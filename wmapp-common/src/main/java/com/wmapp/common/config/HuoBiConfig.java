package com.wmapp.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 配置类
 *
 * @author wmapp
 * @date 2021/11/1 13:42
 */
@ConfigurationProperties(prefix = "huobi")
@Component
public class HuoBiConfig {

    /**
     * 区块链通道地址 使用HECO 默认测试地址
     * 正式地址 {@code https://http-mainnet-node.huobichain.com}
     */
    private String httpUrl = "https://http-testnet.hecochain.com";

    /**
     * 公户账户地址
     */
    private String address = "0x***";

    /**
     * 合约json
     */
    private String abi;

    /**
     * 合约地址
     */
    private String contractAddress = "0x***";

    /***
     * chainId
     */
    private long chainId = 256;

    /**
     * 钱包私钥
     */
    private String privateKey = "0x***";
    

    public String getHttpUrl() {
        return httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAbi() {
        return abi;
    }

    public void setAbi(String abi) {
        this.abi = abi;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public long getChainId() {
        return chainId;
    }

    public void setChainId(long chainId) {
        this.chainId = chainId;
    }

    @Override
    public String toString() {
        return "HuoBiConfig{" +
                "httpUrl='" + httpUrl + '\'' +
                ", address='" + address + '\'' +
                ", chainId='" + chainId + '\'' +
                ", abi='" + abi + '\'' +
                ", contractAddress='" + contractAddress + '\'' +
                ", privateKey='" + privateKey + '\'' +
                '}';
    }
}
