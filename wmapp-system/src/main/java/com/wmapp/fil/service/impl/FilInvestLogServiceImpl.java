package com.wmapp.fil.service.impl;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import com.wmapp.common.config.FilRateConfig;
import com.wmapp.common.config.HuoBiConfig;
import com.wmapp.common.core.redis.RedisCache;
import com.wmapp.common.exception.CustomException;
import com.wmapp.common.utils.BigDecimalUtil;
import com.wmapp.common.utils.StringUtils;
import com.wmapp.fil.domain.*;
import com.wmapp.fil.mapper.*;
import com.wmapp.fil.service.HuoBiService;
import com.wmapp.fil.vo.ReferrerNumVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wmapp.fil.service.IFilInvestLogService;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import javax.annotation.Resource;

/**
 * 投入记录Service业务层处理
 * 
 * @author wmapp
 * @date 2021-10-26
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class FilInvestLogServiceImpl implements IFilInvestLogService
{
    private static Logger logger = LoggerFactory.getLogger(FilInvestLogServiceImpl.class);

    @Resource
    private FilInvestLogMapper filInvestLogMapper;
    @Resource
    private FilUserMapper filUserMapper;
    @Resource
    private FilPlatformRevenueMapper filPlatformRevenueMapper;
    @Resource
    private FilUserRevenueMapper filUserRevenueMapper;
    @Resource
    private FilTransactionRecordMapper filTransactionRecordMapper;
    @Resource
    private HuoBiService huoBiService;
    @Autowired
    private FilDynamicConfigMapper filDynamicConfigMapper;
    @Autowired
    private FilWithdrawRecordMapper filWithdrawRecordMapper;
    @Resource
    private HuoBiConfig huoBiConfig;
    /**
     * 分配比例配置
     */
    @Resource
    private FilRateConfig filRateConfig;
    /**
     * 动态拿10代
     */
    private final Integer DYNAMIC_LEVEL = 10;
    /**
     * 静态上40层
     */
    private final Integer STATIC_UP_LEVEL = 40;
    /**
     * 静态下60层
     */
    private final Integer STATIC_DOWN_LEVEL = 60;

    /**
     * 查询投入记录
     * 
     * @param id 投入记录ID
     * @return 投入记录
     */
    @Override
    public FilInvestLog selectFilInvestLogById(Long id)
    {
        return filInvestLogMapper.selectFilInvestLogById(id);
    }

    /**
     * 查询投入记录列表
     * 
     * @param filInvestLog 投入记录
     * @return 投入记录
     */
    @Override
    public List<FilInvestLog> selectFilInvestLogList(FilInvestLog filInvestLog)
    {
        return filInvestLogMapper.selectFilInvestLogList(filInvestLog);
    }

    /**
     * 保存投入记录
     * @param receipt {@link TransactionReceipt}
     */
    @Override
    public boolean saveInvest(TransactionReceipt receipt)throws Exception {
        //查询用户
        String userCode = receipt.getFrom();
        FilUser user = filUserMapper.selectFilUserByCodeForUpdate(userCode);
        BigDecimal price = huoBiService.getAmountOfMoney(receipt);
        List<FilPlatformRevenue> platformRevenueList = new ArrayList<>();
        // 业务逻辑
        FilInvestLog log = saveInvest(user, price, receipt.getTransactionHash(),0,platformRevenueList);
        if(log!=null && log.getId()!=null) {//复投成功实行转账操作
            transferSpareAmount(log,platformRevenueList);
            return true;
        }
        throw new CustomException("投入失败", 500);
    }

    /**
     * 提现复投
     * @param user 用户信息
     * @param investAmount 复投金额
     * @return true成功
     * @throws Exception
     */
    @Override
    public boolean saveWithdrawInvest(FilUser user, BigDecimal investAmount,BigDecimal withdrawAmount)throws Exception {
        List<FilPlatformRevenue> platformRevenueList = new ArrayList<>();
        logger.info("###提现进入复投start,userCode: {},复投金额: {}，提现金额: {}",user.getUserCode(),investAmount,withdrawAmount);
        FilInvestLog log = saveInvest(user, investAmount, "",4,platformRevenueList);
        if(log!=null && log.getId()!=null){//复投成功实行转账操作
            logger.info("###提现进入复投成功，进入提现转账");
            String transactionHash = huoBiService.transfer(user.getUserCode(), withdrawAmount);
            //防止交易区块暂未确认或者hash不存在 睡眠一秒
            Thread.currentThread().sleep(1000);
            //重复查询结果
            long startTime = System.currentTimeMillis();
            TransactionReceipt receipt = huoBiService.querySuccessTransactionHash(transactionHash,startTime);
            if (receipt == null) {
                throw new CustomException("交易区块暂未确认或hash地址不存在", 500);
            }else if("0x0".equals(receipt.getStatus())) {//交易失败
                throw new CustomException("转账失败", 500);
            }else{
                user.setRepeatInvest(BigDecimalUtil.add(investAmount,user.getRepeatInvest()));
                user.setPastMoney(BigDecimalUtil.add(withdrawAmount,user.getPastMoney()));
                filUserMapper.updateFilUser(user);
                FilWithdrawRecord withdrawRecord = new FilWithdrawRecord(user.getId(),user.getUserCode(),
                        BigDecimalUtil.add(investAmount,withdrawAmount),investAmount,
                        withdrawAmount,transactionHash,1);
                filWithdrawRecordMapper.insertFilWithdrawRecord(withdrawRecord);
                FilTransactionRecord record = new FilTransactionRecord (withdrawRecord.getId()+"",
                        user.getUserCode(),huoBiConfig.getAddress(),user.getUserCode() ,
                        transactionHash,withdrawAmount, 1, 1, 2);
                filTransactionRecordMapper.insertFilTransactionRecord(record);
                //提现成功转账到备用钱包操作
                transferSpareAmount(log,platformRevenueList);
                logger.info("###提现成功，userCode:{},提现金额：{}",user.getUserCode(),withdrawAmount);
                return true;
            }
        }
        throw new CustomException("自动复投失败", 500);
    }


    /**
     * 投入计算分润
     * @param user 用户
     * @param price 投入金额
     * @param transactionHash 投入成功的交易hash
     * @param recordType  账单来源 0-投入记录；1-提现；2-转账; 3-节点申购 ;4-复投
     * @return true成功
     * @throws Exception
     */
    public FilInvestLog saveInvest(FilUser user,BigDecimal price,String transactionHash,Integer recordType,
                                   List<FilPlatformRevenue> platformRevenueList) throws Exception {
        String userCode = user.getUserCode();
        String address = huoBiConfig.getAddress();//钱包地址
        //如果没有此用户需要添加此用户
        boolean isNew = false;
        boolean activeStatus = false;
        BigDecimal activateAmount = BigDecimal.ZERO;
        if(user==null){
            isNew = true;
            //新用户需要多存0.1个fil激活账户，并且不计入收益
            user.setPid(0l);
            user.setUserCode(userCode);
            user.setTotalInvest(price);//总投资
            user.setStatus(1);//激活状态
            filUserMapper.insertFilUser(user);
        }else{
            if(user.getStatus() == 0){//未激活状态需要多存0.1个fil激活账户，并且不计入收益
                activateAmount = new BigDecimal(filRateConfig.getActivateAmount());
                user.setStatus(1);//激活状态
                price = BigDecimalUtil.sub(price,activateAmount);
                if(price.compareTo(BigDecimal.ZERO)<=0){
                    throw new CustomException("账户未激活，投入金额需大于"+filRateConfig.getActivateAmount()+"FIL", 500);
                }
                activeStatus = true;
            }
        }
        //投入的金额 5%手续费（1%给大小节点-小节点1%、大节点2% 4%给平台）  95%（50%动态、50%静态）
        BigDecimal chargeAmout = BigDecimalUtil.mulRateStr(price,filRateConfig.getBrokerageRate());
        //节点收益（投入金额1%给大小节点-[（42）小节点1%、（26）大节点2%])
        BigDecimal nodeRevenue = BigDecimalUtil.mulRateStr(price,filRateConfig.getNodeRate());
        //平台收益 (手续费-节点收益)
        BigDecimal platformRevenue = BigDecimalUtil.sub(chargeAmout,nodeRevenue);
        if(activeStatus){//激活状态有0.1 Fil 计算到平台收入里面
            platformRevenue = BigDecimalUtil.add(platformRevenue,activateAmount);
        }
        //需要分配的额度（投入金额-手续费）
        BigDecimal residueAmount = BigDecimalUtil.sub(price,chargeAmout);
        //静态分配金额(投入金额的95%，50%静态分配，50%动态分配)
        BigDecimal staticRevenue = BigDecimalUtil.mulRateStr(residueAmount,filRateConfig.getAllocationRate());
        //动态分配金额(分配的额度-静态分配金额)
        BigDecimal dynamicRevenue = BigDecimalUtil.sub(residueAmount,staticRevenue);
        //投入记录 投入记录手续费和投入金额都加上激活金额
        FilInvestLog log = new FilInvestLog(user.getId(),userCode,price,chargeAmout,transactionHash);
        //保存投入记录
        insertFilInvestLog(log);
        //用户收益log表
        List<FilUserRevenue> list = new ArrayList<>();
        //所有收益的用户表
        Map<Long,FilUser> userMap = new HashMap<>();
        userMap.put(user.getId(),user);
        //平台收益（投入金额4%给大小节点）
        platformRevenueList.add(new FilPlatformRevenue(log.getUserId(),log.getId(),platformRevenue,address,0));
        //节点收益
        calNodeRevenue(nodeRevenue,log,address,list,platformRevenueList,userMap);
        //动态分配
        calDynamicRevenue(dynamicRevenue,log,address,list,platformRevenueList,userMap);
        //静态分配
        calStaticRevenue(price,staticRevenue,address,log,user,list,platformRevenueList,userMap);
        if(!isNew){//不是新用户需要更新投资数量
            user.setTotalInvest(BigDecimalUtil.add(user.getTotalInvest(),price));
        }
        userMap.remove(user.getId());
        if(userMap.size()>0){
            //更新除了当前用户以外的数据
            List<FilUser> updateList = new ArrayList<>(userMap.values());
            filUserMapper.updateBatchFilUserRevenue(updateList);
        }
        //更新用户
        filUserMapper.updateFilUser(user);
        //保存用户收益表
        filUserRevenueMapper.insertBatchUserRevenue(list);
        //保存平台收益表
        filPlatformRevenueMapper.insertBatch(platformRevenueList);
        //保存交易记录表
        filTransactionRecordMapper.insertFilTransactionRecord(new FilTransactionRecord (log.getId()+"",
                log.getUserCode(), userCode, address,
                transactionHash,BigDecimalUtil.add(price,activateAmount), 0, recordType, 2));
        logger.info("###资金已分配完毕：总投入金额：{}，平台收益：{}，节点收益：{}，动态收益：{}，静态收益：{}，激活账户余额：{}",price,platformRevenue,
                nodeRevenue,dynamicRevenue,staticRevenue,activateAmount  );
        return log;
    }

    /**
     * 转账到备用钱包
     * @param platformRevenueList
     */
    private void transferSpareAmount(FilInvestLog log,List<FilPlatformRevenue> platformRevenueList) throws Exception{
        String address = huoBiConfig.getAddress();//公户钱包地址
        String spareAddress = filRateConfig.getSpareAddress();//备用钱包地址
        BigDecimal transferAmount = BigDecimal.ZERO;
        StringBuilder ids = new StringBuilder();
        for(int i=0;i<platformRevenueList.size();i++){
            FilPlatformRevenue vo =  platformRevenueList.get(i);
            if(!address.equals(vo.getAddress())){
                transferAmount = transferAmount.add(vo.getAmount());
                ids.append(vo.getId()+",");
            }
        }
        if(transferAmount.compareTo(BigDecimal.ZERO)>0){
            FilTransactionRecord record = new FilTransactionRecord (log.getId()+"",
                    log.getUserCode(),address,spareAddress ,
                    "",transferAmount, 1, 2, 0);
          //查询公户余额
          BigDecimal balance = huoBiService.getERC20Balance();
          BigDecimal gas = huoBiService.getBalance();//燃料费
          if(balance.compareTo(transferAmount)>=0 && gas.compareTo(new BigDecimal("0.001"))>=0){//公户余额大于转账金额
              String transferHash =  huoBiService.transfer(filRateConfig.getSpareAddress(),transferAmount);
              //防止交易区块暂未确认或者hash不存在 睡眠一秒
              Thread.currentThread().sleep(1000);
              if(StringUtils.isNotEmpty(transferHash)){
                  //查询成功操作hash块数据
                  long startTime = System.currentTimeMillis();
                  TransactionReceipt transactionReceipt = huoBiService.querySuccessTransactionHash(transferHash,startTime);
                  if(transactionReceipt==null || "0x0".equals(transactionReceipt.getStatus())){
                      record.setStatus(3);
                      logger.error("###备用钱包转账失败TransactionReceipt为null，投入记录ID: {},transactionHash: {}",
                              log.getId(),transferHash);
                  }else{
                      record.setStatus(2);
                  }
                  record.setTransactionHash(transferHash);
              }else{
              }
          }else{
              logger.error("###备用钱包转账失败，投入记录ID: {},公户余额余额不足: {},燃料费：{}",log.getId(),balance,gas);
          }
          filTransactionRecordMapper.insertFilTransactionRecord(record);
        }
    }


    /**
     * 静态分配收益（收益=自己的投资量 *（50%静态分配额/100层总投资量））
     * @param staticRevenue
     * @param log
     */
    public void calStaticRevenue(BigDecimal totalInvest, BigDecimal staticRevenue,String address, FilInvestLog log,
                                  FilUser user,List<FilUserRevenue> list,List<FilPlatformRevenue> platformList,Map<Long,FilUser> userMap) {
        logger.info("###静态收益start更新成功，投入记录ID: {}",log.getId());
        //上40层用户
        List<FilUser> userList = filUserMapper.getHigherLevelUserListForUpdate(log.getUserId(),STATIC_UP_LEVEL);
        //上60层用户
        List<FilUser> downUserList = filUserMapper.getLowerLevelUserListForUpdate(log.getUserId(),STATIC_DOWN_LEVEL);
        //同层用户
        List<FilUser> sameList = new ArrayList<>();
        if(user.getPid()>0){
            sameList = filUserMapper.getSameLevelListForUpdate(user.getId(),user.getPid());
            userList.addAll(sameList);
        }
        //合并上下100层用户数据
        userList.addAll(downUserList);
        List<Long> idList = userList.stream().map(FilUser::getId).collect(Collectors.toList());
        BigDecimal allInvest = BigDecimalUtil.add(totalInvest,user.getTotalInvest());
        if(idList.size()>0){
            //获取上下100层总投资量
            BigDecimal  invest = filUserMapper.getTotalInvest(idList);
            allInvest = BigDecimalUtil.add(invest,allInvest);
        }
        //计算当前用户的静态收益
        BigDecimal amount = calStaticRevenue(totalInvest,staticRevenue,allInvest);
        FilUserRevenue revenues = new FilUserRevenue(log.getUserId(),log.getUserCode(),log.getId(),amount,2);
        list.add(revenues);
        //更新到用户收益表统一修改
        user.setStaticRevenue(BigDecimalUtil.add(amount,user.getStaticRevenue()));
        BigDecimal totalAmount = amount;
        //计算上下100层数据
        int updateNum = 0;
        for(int i=0;i<userList.size();i++){
            FilUser vo = userList.get(i);
            BigDecimal investAmount = vo.getTotalInvest();
            BigDecimal revenue = calStaticRevenue(investAmount,staticRevenue,allInvest);
            if(revenue.compareTo(BigDecimal.ZERO)>0){
                vo.setStaticRevenue(BigDecimalUtil.add(revenue,vo.getStaticRevenue()));
                dealUserMap(userMap,vo,1);
                list.add(new FilUserRevenue(vo.getId(),vo.getUserCode(),log.getId(),revenue,2));
                updateNum++;
                totalAmount = BigDecimalUtil.add(totalAmount,revenue);
            }
        }
        BigDecimal residueRevenue = BigDecimalUtil.sub(staticRevenue,totalAmount);
        if(residueRevenue.compareTo(BigDecimal.ZERO)>0){
            transferSpareRevenue(address,residueRevenue,3,log,platformList);
        }
        logger.info("###待分配资金: {},冗余资金: {},已分配资金：{},静态收益用户数量：{},当前用户所得静态收益：{}",
                staticRevenue, residueRevenue,
                totalAmount,updateNum,amount);
        logger.info("###静态收益end计算成功，投入记录ID: {}",log.getId());
    }

    /**
     * 动态收益（拿10代）
     * @param dynamicRevenue
     * @param log
     */
    public void calDynamicRevenue(BigDecimal dynamicRevenue, FilInvestLog log,String address,List<FilUserRevenue> list,
                                   List<FilPlatformRevenue> platformRevenueList,Map<Long,FilUser> userMap) {
        //查询动态拿10代
        List<FilUser> userList = filUserMapper.getHigherLevelUserListForUpdate(log.getUserId(),DYNAMIC_LEVEL);
        if(userList.size()>0){
            List<Long> idList = userList.stream().map(FilUser::getId).collect(Collectors.toList());
            //查询直推人数
            List<ReferrerNumVo> referrerList = filUserMapper.getReferrerNumByIds(idList);
            Map<Long,Integer> referMap = referrerList.stream().collect(
                    Collectors.toMap(ReferrerNumVo::getUserId, ReferrerNumVo::getReferrerNum));
            FilDynamicConfig sconfig = new FilDynamicConfig();
            sconfig.setStatus(0);
            //TODO 动态配置放到缓存
            List<FilDynamicConfig> configList = filDynamicConfigMapper.selectFilDynamicConfigList(sconfig);
            int updateNum = 0;
            BigDecimal totalAmount = BigDecimal.ZERO;
            for(int j=0;j<userList.size();j++){
                FilUser vo = userList.get(j);
                Long userId = vo.getId();
                Integer level = vo.getLevel();//和当前投入用户的深度关系
                Integer referNum = referMap.get(userId)==null?0:referMap.get(userId);
                if(referNum==0) continue;//直推人数为0 跳出循环
                for(int i=0;i<configList.size();i++){
                    FilDynamicConfig config = configList.get(i);
                    Integer eraNum = config.getEraNum();//几代
                    Integer peopleNum = config.getPeopleNum();//直推人数
                    BigDecimal takeRate = config.getTakeRate();//拿提层的百分比
                    //当前直推人数大于等于用户直推人数 并且深度表小于等于几代
                    if(referNum>=peopleNum && level == eraNum && takeRate.compareTo(BigDecimal.ZERO)>0){
                        BigDecimal rate = BigDecimalUtil.div(BigDecimalUtil.mul(takeRate,"2"),"100");
                        BigDecimal amount = BigDecimalUtil.mul(dynamicRevenue,rate);
                        vo.setDynamicRevenue(BigDecimalUtil.add(amount,vo.getDynamicRevenue()));
                        dealUserMap(userMap,vo,2);
                        list.add(new FilUserRevenue(vo.getId(),vo.getUserCode(),log.getId(),amount,3));
                        totalAmount = BigDecimalUtil.add(totalAmount,amount);
                        updateNum++;
                        break;
                    }
                }
            }
            BigDecimal residueRevenue = BigDecimalUtil.sub(dynamicRevenue,totalAmount);
            if(residueRevenue.compareTo(BigDecimal.ZERO)>0){
                transferSpareRevenue(address,residueRevenue,2,log,platformRevenueList);
            }
        }
    }

    /**
     * 计算大小节点的收益
     * @param nodeRevenue
     * @param log {@link FilInvestLog}投入记录
     */
    public void calNodeRevenue(BigDecimal nodeRevenue,FilInvestLog log,String address,List<FilUserRevenue> list,
                               List<FilPlatformRevenue> platformRevenueList,Map<Long,FilUser> userMap){
        //从redis中获取大小节点的用户
        List<FilUser> userList = filUserMapper.getFileUserToNode();
        BigDecimal totalNode = BigDecimal.ZERO;
        if(userList.size()>0){
            //大节点收益
            BigDecimal bigNode = BigDecimalUtil.mulRateStr(nodeRevenue,filRateConfig.getBigNodeRate());
            //小节点收益
            BigDecimal smallNode = BigDecimalUtil.mulRateStr(nodeRevenue,filRateConfig.getSmallNodeRate());
            for(int i = 0;i<userList.size();i++){
                FilUser user = userList.get(i);
                BigDecimal amount = user.getNodeType()==1?bigNode:smallNode;
                //收益大于0
                if(amount.compareTo(BigDecimal.ZERO)>0){
                    //更新节点收益
                    user.setNodeRevenue(BigDecimalUtil.add(user.getNodeRevenue(),amount));
                    dealUserMap(userMap,user,0);
                    //更新总收益
                    list.add(new FilUserRevenue(user.getId(),user.getUserCode(),log.getId(),amount,1));
                    totalNode = BigDecimalUtil.add(totalNode,amount);//累加收益
                }
            }
        }
        //大小节点未分配完成的资金
        BigDecimal residueRevenue = BigDecimalUtil.sub(nodeRevenue,totalNode);
        if(residueRevenue.compareTo(BigDecimal.ZERO)>0){
            transferSpareRevenue(address,residueRevenue,1,log,platformRevenueList);
        }
    }

    /**
     * 执行转账操作
     * @param transferAmount
     * @return
     * @throws Exception
     */
    public String transfer(BigDecimal transferAmount,String spareAddress )throws Exception{
        BigDecimal balance = huoBiService.getERC20Balance();//查询公户费用
        BigDecimal gas = huoBiService.getBalance();//查询燃料费
        String transactionHash = "";
        if(balance.compareTo(transferAmount)>=0 && gas.compareTo(new BigDecimal("0.001"))>=0){//公户余额大于转账金额
            transactionHash =  huoBiService.transfer(spareAddress,transferAmount);
            //防止交易区块暂未确认或者hash不存在 睡眠一秒
            Thread.currentThread().sleep(1000);
            //重复查询结果
            long startTime = System.currentTimeMillis();
            TransactionReceipt receipt = huoBiService.querySuccessTransactionHash(transactionHash,startTime);
            if (receipt != null && "0x1".equals(receipt.getStatus())) {//交易成功
                return transactionHash;
            }
        }
        return transactionHash;
    }


    /**
     * 判断是否转到备用钱包
     * @param address
     * @param residueRevenue
     * @return 转账成功 返回交易hash,否则返回空
     */
    public void transferSpareRevenue(String address,BigDecimal residueRevenue,Integer type,FilInvestLog log,
                                       List<FilPlatformRevenue> platformRevenueList){
        String spareAddress = filRateConfig.getSpareAddress();//备用钱包地址
        if(!spareAddress.equals(address)){//备用钱包地址不等于公户钱包地址执行实时转账操作
            platformRevenueList.add(new FilPlatformRevenue(log.getUserId(),log.getId(),residueRevenue,spareAddress,type));
        }else {
            platformRevenueList.add(new FilPlatformRevenue(log.getUserId(),log.getId(),residueRevenue,address,type));
        }
    }

    /**
     * 处理用户收益 保证一个用户只被更新一次
     * @param userMap
     * @param user
     * @param type
     */
    public void dealUserMap(Map<Long,FilUser> userMap,FilUser user,Integer type){
        Long userId = user.getId();
        if(userMap.containsKey(userId)){
            FilUser filUser = userMap.get(userId);
            if(type == 0){//节点收益
                filUser.setNodeRevenue(user.getNodeRevenue());
            }else if(type == 1){//静态收益
                filUser.setStaticRevenue(user.getStaticRevenue());
            }else if(type == 2){//动态收益
                filUser.setDynamicRevenue(user.getDynamicRevenue());
            }
            userMap.put(userId,filUser);
        }else{
            userMap.put(userId,user);
        }
    }


    /**
     * 计算静态收益 （收益=自己的投资量 *（50%静态分配额/100层总投资量））
     * @param totalInvest 自己的投资量
     * @param staticRevenue 50%静态分配额
     * @param allInvest 100层总投资量
     * @return
     */
    private BigDecimal calStaticRevenue(BigDecimal totalInvest, BigDecimal staticRevenue, BigDecimal allInvest) {
        BigDecimal amount = BigDecimalUtil.div(staticRevenue,allInvest);
        return BigDecimalUtil.mul(totalInvest,amount);
    }

    /**
     * 新增投入记录
     * 
     * @param filInvestLog 投入记录
     * @return 结果
     */
    public int insertFilInvestLog(FilInvestLog filInvestLog)
    {
        filInvestLog.setCreateTime(new Date());
        return filInvestLogMapper.insertFilInvestLog(filInvestLog);
    }


}
