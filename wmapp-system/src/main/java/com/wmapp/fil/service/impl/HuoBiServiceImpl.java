package com.wmapp.fil.service.impl;

import com.wmapp.common.config.HuoBiConfig;
import com.wmapp.common.exception.TransactionException;
import com.wmapp.fil.service.HuoBiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

/**
 * @author wmapp
 * @date 2021/11/1 14:17
 */
@Service
public class HuoBiServiceImpl implements HuoBiService {

    private static Logger log = LoggerFactory.getLogger(HuoBiServiceImpl.class);

    @Resource
    private HuoBiConfig huoBiConfig;
    private static Web3j web3;
    private static Credentials credentials;

    @PostConstruct
    private void init() {
        web3 = Web3j.build(new HttpService(huoBiConfig.getHttpUrl()));
        credentials = Credentials.create(huoBiConfig.getPrivateKey());
    }

    @Override
    public String transfer(String to, BigDecimal amount) throws ExecutionException, InterruptedException {
        EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(
                huoBiConfig.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();

        BigInteger nonce = ethGetTransactionCount.getTransactionCount();

        Function function = new Function(
                "transfer",
                Arrays.asList(new Address(to), new Uint256(Convert.toWei(String.valueOf(amount), Convert.Unit.ETHER).toBigInteger())),
                Collections.singletonList(new TypeReference<Type>() {
                }));
        String encodedFunction = FunctionEncoder.encode(function);

        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, Convert.toWei("18", Convert.Unit.GWEI).toBigInteger(),
                Convert.toWei("100000", Convert.Unit.WEI).toBigInteger(), huoBiConfig.getContractAddress(), encodedFunction);

        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, huoBiConfig.getChainId(), credentials);
        String hexValue = Numeric.toHexString(signedMessage);

        log.debug("transfer hexValue:" + hexValue);

        EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).sendAsync().get();
        if (ethSendTransaction.hasError()) {
            log.info("transfer error:{}", ethSendTransaction.getError().getMessage());
            throw new TransactionException(ethSendTransaction.getError().getMessage());
        } else {
            String transactionHash = ethSendTransaction.getTransactionHash();
            log.info("Transfer transactionHash:{}", transactionHash);
            return transactionHash;
        }
    }

    @Override
    public TransactionReceipt queryTransactionHash(String hash) throws ExecutionException, InterruptedException {
        EthGetTransactionReceipt receipt = web3.ethGetTransactionReceipt(hash).sendAsync().get();
        try {
            TransactionReceipt transactionReceipt = receipt.getTransactionReceipt().get();
            log.info("交易信息: {}", receipt.getTransactionReceipt());
            log.info("status: {}", receipt.getTransactionReceipt().get().getStatus());
            return transactionReceipt;
        } catch (Exception e) {
            log.warn("交易区块暂未确认或hash地址不存在");
        }
        return null;
    }

    @Override
    public BigDecimal getBalance() throws IOException {
        BigInteger balance = web3.ethGetBalance(huoBiConfig.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance();
        return Convert.fromWei(balance.toString(), Convert.Unit.ETHER);
    }

    @Override
    public BigDecimal getERC20Balance() throws IOException {
        final String DATA_PREFIX = "0x70a08231000000000000000000000000";
        String value = web3.ethCall(org.web3j.protocol.core.methods.request.Transaction.createEthCallTransaction(huoBiConfig.getAddress(),
                        huoBiConfig.getContractAddress(), DATA_PREFIX + huoBiConfig.getAddress().substring(2)),
                DefaultBlockParameterName.PENDING).send().getValue();
        BigInteger bigInteger = new BigInteger(value.substring(2), 16);
        return Convert.fromWei(bigInteger.toString(), Convert.Unit.ETHER);
    }

    @Override
    public TransactionReceipt querySuccessTransactionHash(String transactionHash,long startTime)throws ExecutionException, InterruptedException{
        TransactionReceipt receipt = this.queryTransactionHash(transactionHash);
        long endTime = System.currentTimeMillis();
        long time = endTime-startTime;
        if (receipt==null){
            if(time>60000){//大于1分钟还查询不到结果就返回null
                return null;
            }else{
               receipt = this.querySuccessTransactionHash(transactionHash,startTime);
               return receipt;
            }
        }else if(receipt!=null && "0x1".equals(receipt.getStatus())) {//交易成功
            return receipt;
        }else if(receipt!=null && "0x0".equals(receipt.getStatus())){//交易失败
            return receipt;
        }
        return null;
    }

    /**
     * 获取金额
     * @param transaction
     * @return
     */
    @Override
    public BigDecimal getAmountOfMoney(TransactionReceipt transaction){
        Log log = transaction.getLogs().get(0);
        BigInteger bigInteger = new BigInteger(log.getData().substring(2), 16);
        return Convert.fromWei(bigInteger.toString(), Convert.Unit.ETHER);
    }
}
