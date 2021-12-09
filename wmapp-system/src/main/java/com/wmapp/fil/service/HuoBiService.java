package com.wmapp.fil.service;

import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

/**
 * @author wmapp
 * @date 2021/11/1 11:23
 */
public interface HuoBiService {

    /**
     * 转账
     *
     * @param to     目标用户
     * @param amount 转账金额
     * @return 交易区块Hash
     */
    String transfer(String to, BigDecimal amount) throws ExecutionException, InterruptedException;

    /**
     * 根据块hash查询结果
     *
     * @param hash 交易hash
     * @return 结果 {@see TransactionReceipt#status} 0x0 失败  0x1 成功
     */
    TransactionReceipt queryTransactionHash(String hash) throws ExecutionException, InterruptedException;

    /**
     * 根据hash重复查询结果直至成功
     *
     * @param hash 交易hash
     * @return 成功返回 TransactionReceipt 不存在返回null
     */
    TransactionReceipt querySuccessTransactionHash(String hash,long startTime) throws ExecutionException, InterruptedException;

    /**
     * 获取公户 eth 余额
     *
     * @return 余额
     */
    BigDecimal getBalance() throws IOException;


    /**
     * 获取 HECO HFIL 代币余额
     *
     * @return 余额
     * @throws IOException
     */
    BigDecimal getERC20Balance() throws IOException;

    /**
     * 根据receipt获取支付金额
     * @param receipt
     * @return
     */
    BigDecimal getAmountOfMoney(TransactionReceipt receipt);


}
