package com.wmapp.common.config;

import com.wmapp.common.annotation.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @author wmapp
 * @date 2021-11-06
 */
@Component
@ConfigurationProperties(prefix = "fil")
public class FilRateConfig {

    /**
     * 备用钱包地址-沉冗资金转账地址
     */
    private String spareAddress;
    /**
     * 激活账户的fil个数
     */
    private String activateAmount;
    /**
     * 手续费
     */
    private String brokerageRate;
    /**
     * 动态静态分配额
     */
    private String allocationRate;
    /**
     * 节点收益额度
     */
    private String nodeRate;
    /**
     * 大节点收益额度
     */
    private String bigNodeRate;
    /**
     * 小节点收益额度
     */
    private String smallNodeRate;

    public String getSpareAddress() {
        return spareAddress;
    }

    public void setSpareAddress(String spareAddress) {
        this.spareAddress = spareAddress;
    }

    public String getActivateAmount() {
        return activateAmount;
    }

    public void setActivateAmount(String activateAmount) {
        this.activateAmount = activateAmount;
    }

    public String getBrokerageRate() {
        return brokerageRate;
    }

    public void setBrokerageRate(String brokerageRate) {
        this.brokerageRate = brokerageRate;
    }

    public String getAllocationRate() {
        return allocationRate;
    }

    public void setAllocationRate(String allocationRate) {
        this.allocationRate = allocationRate;
    }

    public String getNodeRate() {
        return nodeRate;
    }

    public void setNodeRate(String nodeRate) {
        this.nodeRate = nodeRate;
    }

    public String getBigNodeRate() {
        return bigNodeRate;
    }

    public void setBigNodeRate(String bigNodeRate) {
        this.bigNodeRate = bigNodeRate;
    }

    public String getSmallNodeRate() {
        return smallNodeRate;
    }

    public void setSmallNodeRate(String smallNodeRate) {
        this.smallNodeRate = smallNodeRate;
    }
}
