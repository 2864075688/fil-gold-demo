package com.wmapp.web.controller.api;

import com.wmapp.common.config.HuoBiConfig;
import com.wmapp.common.core.domain.AjaxResult;
import com.wmapp.common.utils.BigDecimalUtil;
import com.wmapp.common.utils.StringUtils;
import com.wmapp.fil.domain.FilInvestConfig;
import com.wmapp.fil.domain.FilUser;
import com.wmapp.fil.service.HuoBiService;
import com.wmapp.fil.service.IFilInvestConfigService;
import com.wmapp.fil.service.IFilTransactionRecordService;
import com.wmapp.fil.service.IFilUserService;
import com.wmapp.fil.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 用户相关接口
 *
 * @author wmapp
 * @module fil-user
 * @date 2021-10-25
 */
@RestController
@RequestMapping("/api/user")
public class ApiFilUserController {

    @Autowired
    private IFilUserService filUserService;

    @Autowired
    private HuoBiService huoBiService;

    @Autowired
    private HuoBiConfig huoBiConfig;

    @Autowired
    private IFilInvestConfigService filInvestConfigService;

    @Autowired
    private IFilTransactionRecordService transactionRecordService;

    /**
     * 保存用户
     * @param {@see Map<String,Object>#userCode}  用户code
     * @param {@see Map<String,Object>#parentCode}  父类parentCode
     * @return {@see AjaxResult#status}200成功 其余失败 {@see AjaxResult#data} userId
     */
    @PostMapping
    public AjaxResult save(@RequestBody Map<String,Object> map)throws Exception{
        String userCode = map.get("userCode")==null?"":map.get("userCode").toString();
        String parentCode = map.get("parentCode")==null?"":map.get("parentCode").toString();
        if(StringUtils.isEmpty(userCode)){
            return AjaxResult.error("用户code不能为空");
        }
        FilUser user = filUserService.selectFilUserByCode(userCode);
        if(user!=null){
            return AjaxResult.success("用户已存在",handleUser(user));
        }
        FilUser puser = null;
        if(StringUtils.isNotEmpty(parentCode)){
            puser = filUserService.selectFilUserByCode(parentCode);
        }
        Long userId = filUserService.saveUserAndLevel(userCode,puser);
        if(userId>0){
            UserVo vo = new UserVo(userId,userCode,puser==null?null:puser.getUserCode());
            return AjaxResult.success("保存成功",vo);
        }
        return AjaxResult.error("保存失败");
    }

    /**
     * 更新授权状态已授权
     * @param  {@see Map#userId} 用户id
     * @return
     * @throws Exception
     */
    @PutMapping
    public AjaxResult updateAuthStatus(@RequestBody Map<String,Object> map)throws Exception{
        String transactionHash = map.get("transactionHash")==null?"":map.get("transactionHash").toString();
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
        if("0x0".equals(receipt.getStatus())) {//交易失败
            return AjaxResult.error("交易失败");
        }
        if(!huoBiConfig.getContractAddress().equals(receipt.getTo())){
            return AjaxResult.error("到账钱包地址和当前钱包地址不一致");
        }
        String userCode = receipt.getFrom();
        FilUser user = filUserService.selectFilUserByCode(userCode);
        if(user == null){
            return AjaxResult.error("用户不存在");
        }
        filUserService.updateAuthStatus(user,receipt);
        return AjaxResult.success("更新成功");
    }

    /**
     * 根据userCode获取用户信息
     * @param userCode
     * @return {@see AjaxResult#status}200成功 其余失败 {@see AjaxResult#data} {@link UserVo}
     */
    @GetMapping
    public AjaxResult getUserByCode(String userCode){
        FilUser user = filUserService.selectFilUserByCode(userCode);
        if(user!=null){
            UserVo vo = handleUser(user);
            return AjaxResult.success("获取成功",vo);
        }
        return AjaxResult.error("用户不存在");
    }

    /**
     * 封装返回前台的数据
     * @param user
     * @return
     */
    public UserVo handleUser(FilUser user){
        BigDecimal totalRevenue = user.getTotalRevenue();//总收益
        BigDecimal totalPastMoney = user.getPastMoney();//已提现金额
        //包含提现和复投金额
        BigDecimal costAmount = BigDecimalUtil.add(totalPastMoney,user.getRepeatInvest());
        //用户总可提现金额
        BigDecimal totalWithdrawMoney = BigDecimalUtil.sub(totalRevenue,costAmount);
        //实际可提现金额
        BigDecimal actualAmount = BigDecimal.ZERO;
        //需要复投金额
        BigDecimal investAmount = BigDecimal.ZERO;
        if(totalWithdrawMoney.compareTo(BigDecimal.ZERO)>0){
            FilInvestConfig config = filInvestConfigService.getConfigByUserId(user.getId());
            BigDecimal cashRate = config.getCashRate();//提现比例
             actualAmount = BigDecimalUtil.mulRate(totalWithdrawMoney,cashRate);
             investAmount = BigDecimalUtil.sub(totalWithdrawMoney,actualAmount);
        }
        UserVo vo = new UserVo(user.getId(),user.getUserCode(),user.getParentCode(),BigDecimalUtil.toPlainTrailingZeros(user.getTotalInvest()),
                BigDecimalUtil.toPlainTrailingZeros(totalRevenue),BigDecimalUtil.toPlainTrailingZeros(totalWithdrawMoney),
                BigDecimalUtil.toPlainTrailingZeros(totalPastMoney),BigDecimalUtil.toPlainTrailingZeros(user.getNodeRevenue()),
                BigDecimalUtil.toPlainTrailingZeros(user.getStaticRevenue()),BigDecimalUtil.toPlainTrailingZeros(user.getDynamicRevenue()),
                BigDecimalUtil.toPlainTrailingZeros(actualAmount),BigDecimalUtil.toPlainTrailingZeros(investAmount)
                ,user.getNodeType(),user.getStatus(),user.getAuthStatus());
        return vo;
    }

}
