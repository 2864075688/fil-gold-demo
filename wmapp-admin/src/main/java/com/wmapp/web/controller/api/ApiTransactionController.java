package com.wmapp.web.controller.api;

import com.wmapp.common.config.HuoBiConfig;
import com.wmapp.common.core.controller.BaseController;
import com.wmapp.common.core.domain.AjaxResult;
import com.wmapp.common.utils.BigDecimalUtil;
import com.wmapp.common.utils.StringUtils;
import com.wmapp.fil.domain.*;
import com.wmapp.fil.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 投入记录
 *
 * @author wmapp
 * @module fil-user
 * @date 2021-11-04
 */
@RestController
@RequestMapping("api")
public class ApiTransactionController extends BaseController {

    @Autowired
    private IFilInvestLogService filInvestLogService;
    @Autowired
    private IFilNodeApplyService filNodeApplyService;
    @Autowired
    private IFilNodeService filNodeService;
    @Autowired
    private IFilUserService filUserService;
    @Autowired
    private HuoBiService huoBiService;
    @Autowired
    private IFilInvestConfigService filInvestConfigService;
    @Autowired
    private HuoBiConfig huoBiConfig;
    @Autowired
    private IFilTransactionRecordService transactionRecordService;
    /**
     * 根据交易hash查询交易记录保存投入记录
     * @param {@see Map#transactionHash} 申购的交易hash
     * @param {@see Map#userCode} 用户code
     * @return
     */
    @PostMapping("invest")
    public AjaxResult saveInvest(@RequestBody Map<String,Object> map) throws Exception{
        String transactionHash =  map.get("transactionHash")==null?"":map.get("transactionHash").toString();
        String userCode = map.get("userCode")==null?"":map.get("userCode").toString();
        if(StringUtils.isEmpty(userCode)){
            return AjaxResult.error("userCode不能为空");
        }
        if(StringUtils.isEmpty(transactionHash)){
            return AjaxResult.error("交易Hash不能为空");
        }
        int hashCount = transactionRecordService.getExistHashCount(transactionHash);
        if(hashCount>0){
            return AjaxResult.error("交易Hash不可重复使用");
        }
        //查询成功操作hash块数据
        long startTime = System.currentTimeMillis();
        TransactionReceipt receipt = huoBiService.querySuccessTransactionHash(transactionHash,startTime);
        if (receipt==null){
            return AjaxResult.error("交易不存在");
        }
        if("0x0".equals(receipt.getStatus())) {//交易失败
            return AjaxResult.error("交易失败");
        }
        if(!(userCode.toLowerCase()).equals(receipt.getFrom())){
            return AjaxResult.error("交易块钱包地址和当前用户钱包地址不一致");
        }
        if(!huoBiConfig.getContractAddress().equals(receipt.getTo())){
            return AjaxResult.error("交易块钱包地址和系统公户钱包地址不一致");
        }
        boolean result = filInvestLogService.saveInvest(receipt);
        if(result){
            return AjaxResult.success("投入成功");
        }
        return AjaxResult.error("操作失败");
    }

    /**
     * 用户提现
     * @param {@see Map#userCode} 用户code
     * @param {@see Map#amount}  提现金额
     * @return
     */
    @PostMapping("withdrawal")
    public AjaxResult withdrawal(@RequestBody Map<String,Object> map)throws Exception{
        String userCode = map.get("userCode")==null?"":map.get("userCode").toString();
        BigDecimal amount = map.get("amount")==null?BigDecimal.ZERO:new BigDecimal(map.get("amount").toString());
        if(StringUtils.isEmpty(userCode)){
            return AjaxResult.error("userCode不能为空");
        }
        if(amount.compareTo(BigDecimal.ZERO)<=0){
            return AjaxResult.error("请输入正确的提现金额");
        }
        FilUser user = filUserService.selectFilUserByCodeForUpdate(userCode);
        BigDecimal totalRevenue = user.getTotalRevenue();//总收益
        BigDecimal totalPastMoney = user.getPastMoney();//已提现金额
        //包含提现和复投金额
        BigDecimal costAmount = BigDecimalUtil.add(totalPastMoney,user.getRepeatInvest());
        //用户总可提现金额
        BigDecimal totalWithdrawMoney = BigDecimalUtil.sub(totalRevenue,costAmount);
        if(totalWithdrawMoney.compareTo(amount)>=0){//提现金额
            FilInvestConfig config = filInvestConfigService.getConfigByUserId(user.getId());
            BigDecimal cashRate = config.getCashRate();//提现比例
            //可提现金额
            BigDecimal withdrawAmount = BigDecimalUtil.mulRate(amount,cashRate);
            //复投金额
            BigDecimal investAmount = BigDecimalUtil.sub(amount,withdrawAmount);
            //查询公户金额
            BigDecimal balance = huoBiService.getERC20Balance();
            //判断提现金额是否大于转账金额
            if(balance.compareTo(withdrawAmount)<0){
                return AjaxResult.error("余额不足，请联系客服");
            }
            //查询燃料费
            BigDecimal gas = huoBiService.getBalance();
            if(gas.compareTo(new BigDecimal("0.001"))<0){
                return AjaxResult.error("燃料费不足，请联系客服");
            }
            //此处更新已提现的金额
            user.setWithdrawMoney(BigDecimalUtil.add(user.getWithdrawMoney(), withdrawAmount));
            //提现得自动复投
            boolean result = filInvestLogService.saveWithdrawInvest(user,investAmount,withdrawAmount);
            if(result) {
                return AjaxResult.success("提现成功");
            }
            return AjaxResult.error("提现失败");
        }else{
            return AjaxResult.error("当前可提现金额不足");
        }

    }

    /**
     * 申购节点
     * @param {@see Map#transactionHash} 申购的交易hash
     * @param {@see Map#nodeId} 节点id
     * @param {@see Map#userCode} 用户code
     * @return
     * @throws Exception
     */
    @PostMapping("/apply/node")
    public AjaxResult applyNode(@RequestBody Map<String,Object> map)throws Exception{
        String transactionHash = map.get("transactionHash")==null?"":map.get("transactionHash").toString();
        String userCode = map.get("userCode")==null?"":map.get("userCode").toString();
        Long nodeId = map.get("nodeId")==null?0l:Long.parseLong(map.get("nodeId").toString());
        if(StringUtils.isEmpty(userCode)){
            return AjaxResult.error("用户code不能为空");
        };
        FilUser user = filUserService.selectFilUserByCode(userCode);
        if(user!=null && user.getNodeType()>0){
            return AjaxResult.error("当前已经是节点身份");
        }
        int hashCount = transactionRecordService.getExistHashCount(transactionHash);
        if(hashCount>0){
            return AjaxResult.error("交易Hash不可重复使用");
        }
        //查询控制在1分钟响应
        long startTime = System.currentTimeMillis();
        TransactionReceipt receipt = huoBiService.querySuccessTransactionHash(transactionHash,startTime);
        if (receipt==null){
            return AjaxResult.error("交易不存在");
        }
        if(!huoBiConfig.getContractAddress().equals(receipt.getTo())){
            return AjaxResult.error("交易块钱包地址和系统公户钱包地址不一致");
        }
        if(!(userCode.toLowerCase()).equals(receipt.getFrom())){
            return AjaxResult.error("交易块钱包地址和当前用户钱包地址不一致");
        }
        FilNode node = filNodeService.selectFilNodeByIdToUpdate(nodeId);
        if(node == null){
            return AjaxResult.error("节点不存在");
        }
        //总名额
        Integer quota = node.getQuota();
        Integer occupyQuota =  node.getOccupyQuota();
        if(quota==occupyQuota){
            return AjaxResult.error("此节点申购名额已用完");
        }
        BigDecimal price = huoBiService.getAmountOfMoney(receipt);
        if(node.getAmount().compareTo(price) != 0){
            return AjaxResult.error("申购节点金额不一致");
        }
        return toAjax(filNodeApplyService.save(user,receipt,node));
    }

}
