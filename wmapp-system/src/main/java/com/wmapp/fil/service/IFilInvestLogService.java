package com.wmapp.fil.service;

import java.math.BigDecimal;
import java.util.List;

import com.wmapp.common.config.FilRateConfig;
import com.wmapp.fil.domain.FilInvestLog;
import com.wmapp.fil.domain.FilTransactionRecord;
import com.wmapp.fil.domain.FilUser;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/**
 * 投入记录Service接口
 * 
 * @author wmapp
 * @date 2021-10-26
 */
public interface IFilInvestLogService 
{
    /**
     * 查询投入记录
     * 
     * @param id 投入记录ID
     * @return 投入记录
     */
    public FilInvestLog selectFilInvestLogById(Long id);

    /**
     * 查询投入记录列表
     * 
     * @param filInvestLog 投入记录
     * @return 投入记录集合
     */
    public List<FilInvestLog> selectFilInvestLogList(FilInvestLog filInvestLog);

    /**
     * 保存投入记录
     * @param receipt {@link TransactionReceipt}
     */
    boolean saveInvest(TransactionReceipt receipt)throws Exception;

    /**
     * 提现自动复投
     * @param user
     * @param investAmount
     * @return
     */
    boolean saveWithdrawInvest(FilUser user, BigDecimal investAmount,BigDecimal withdrawAmount)throws Exception;

}
