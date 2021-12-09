package com.wmapp.fil.domain;

import java.math.BigDecimal;
import com.wmapp.common.annotation.Excel;
import com.wmapp.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 提现记录对象 fil_withdraw_record
 * 
 * @author wmapp
 * @date 2021-11-10
 */
@NoArgsConstructor
@AllArgsConstructor
public class FilWithdrawRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  ID */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 用户code */
    @Excel(name = "用户code")
    private String userCode;

    /** 需提现金额 */
    @Excel(name = "需提现金额")
    private BigDecimal amount;

    /** 复投金额 */
    @Excel(name = "复投金额")
    private BigDecimal investAmount;

    /** 提现金额 */
    @Excel(name = "提现金额")
    private BigDecimal withdrawalAmount;

    /** 提现hash */
    @Excel(name = "提现hash")
    private String withdrawalHash;

    /** 状态0-提现中，1-提现成功，2-提现失败 */
    @Excel(name = "状态0-提现中，1-提现成功，2-提现失败")
    private Integer status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setUserCode(String userCode) 
    {
        this.userCode = userCode;
    }

    public String getUserCode() 
    {
        return userCode;
    }
    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }
    public void setInvestAmount(BigDecimal investAmount) 
    {
        this.investAmount = investAmount;
    }

    public BigDecimal getInvestAmount() 
    {
        return investAmount;
    }
    public void setWithdrawalAmount(BigDecimal withdrawalAmount) 
    {
        this.withdrawalAmount = withdrawalAmount;
    }

    public BigDecimal getWithdrawalAmount() 
    {
        return withdrawalAmount;
    }

    public void setWithdrawalHash(String withdrawalHash)
    {
        this.withdrawalHash = withdrawalHash;
    }

    public String getWithdrawalHash() 
    {
        return withdrawalHash;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    public FilWithdrawRecord(Long userId,String userCode,BigDecimal amount,BigDecimal investAmount,
                             BigDecimal withdrawalAmount,String withdrawalHash,Integer status){
        this.userCode = userCode;
        this.userId = userId;
        this.amount = amount;
        this.investAmount = investAmount;
        this.withdrawalAmount = withdrawalAmount;
        this.withdrawalHash = withdrawalHash;
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("userCode", getUserCode())
            .append("amount", getAmount())
            .append("investAmount", getInvestAmount())
            .append("withdrawalAmount", getWithdrawalAmount())
            .append("withdrawalHash", getWithdrawalHash())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
