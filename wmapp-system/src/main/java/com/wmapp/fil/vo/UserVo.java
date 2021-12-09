package com.wmapp.fil.vo;

import com.wmapp.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 返回前端的数据
 * @author wmapp
 * @date 2021-11-11
 */
@Data
@AllArgsConstructor
public class UserVo {

    /**  ID */
    private Long userId;

    /**  用户钱包地址 */
    private String userCode;

    /**  父级地址 */
    private String parentCode;

    /** 总投资 */
    private String totalInvest;

    /** 总收益 */
    private String totalRevenue;

    /** 可提现金额 */
    private String withdrawMoney;

    /** 已提现金额 */
    private String pastMoney;

    /** 节点收益 */
    private String nodeRevenue;

    /** 静态收益 */
    private String staticRevenue;

    /** 动态收益 */
    private String dynamicRevenue;

    /** 实际可到账 */
    private String actualAmount;

    /** 复投金额 */
    private String investAmount;

    /** 节点类型0-普通 1-大节点 2-小节点 */
    private Integer nodeType;

    /** 状态0-未激活 1-已激活 */
    private Integer status;
    /** 授权状态0-未授权 1-已授权 */
    private Integer authStatus;

    public UserVo(Long userId,String userCode,String parentCode){
        this.userId = userId;
        this.userCode = userCode;
        this.parentCode = parentCode;
    }

    public String getTotalInvest() {
        if(totalInvest==null){
            totalInvest = "0.0";
        }
        return totalInvest;
    }

    public String getTotalRevenue() {
        if(totalRevenue==null){
            totalRevenue = "0.0";
        }
        return totalRevenue;
    }

    public String getWithdrawMoney() {
        if(withdrawMoney==null){
            withdrawMoney = "0.0";
        }
        return withdrawMoney;
    }

    public String getPastMoney() {
        if(pastMoney==null){
            pastMoney = "0.0";
        }
        return pastMoney;
    }

    public String getNodeRevenue() {
        if(nodeRevenue==null){
            nodeRevenue = "0.0";
        }
        return nodeRevenue;
    }

    public String getStaticRevenue() {
        if(staticRevenue==null){
            staticRevenue = "0.0";
        }
        return staticRevenue;
    }

    public String getDynamicRevenue() {
        if(dynamicRevenue==null){
            dynamicRevenue = "0.0";
        }
        return dynamicRevenue;
    }
    public String getActualAmount() {
        if(actualAmount==null){
            actualAmount = "0.0";
        }
        return actualAmount;
    }
    public String getInvestAmount() {
        if(investAmount==null){
            investAmount = "0.0";
        }
        return investAmount;
    }
    public Integer getNodeType() {
        if(nodeType==null){
            nodeType = 0;
        }
        return nodeType;
    }

    public Integer getStatus() {
        if(status==null){
            status = 0;
        }
        return status;
    }
    public Integer getAuthStatus() {
        if(authStatus==null){
            authStatus = 0;
        }
        return authStatus;
    }
}
