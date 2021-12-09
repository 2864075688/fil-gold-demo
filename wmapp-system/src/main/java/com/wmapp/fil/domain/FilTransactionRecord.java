package com.wmapp.fil.domain;

import java.math.BigDecimal;
import com.wmapp.common.annotation.Excel;
import com.wmapp.common.core.domain.BaseEntity;
import jnr.ffi.annotations.In;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 交易记录对象 fil_transaction_record
 * 
 * @author wmapp
 * @date 2021-11-10
 */
@AllArgsConstructor
@NoArgsConstructor
public class FilTransactionRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    @Excel(name = "ID")
    private Long id;

    /** 来源id */
    private String objIds;

    /** 用户code */
    @Excel(name = "用户code")
    private String userCode;

    /** 付款方 */
    @Excel(name = "付款方")
    private String payerFrom;

    /** 到账方 */
    @Excel(name = "到账方")
    private String payerTo;

    /** 交易hash */
    @Excel(name = "交易hash")
    private String transactionHash;

    /** 交易金额 */
    @Excel(name = "交易金额")
    private BigDecimal amount;

    /** 金额状态  0-入账 1-出帐*/
    @Excel(name = "金额状态")
    private Integer amountType;

    /** 账单来源 0-投入记录；1-提现；2-转账 3-节点申购 4-复投 5-授权*/
    @Excel(name = "账单来源")
    private Integer recordType;

    /** 账单状态 0-待提交，1-确认中 2-成功 3-失败*/
    @Excel(name = "账单状态")
    private Integer status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setObjIds(String objIds)
    {
        this.objIds = objIds;
    }

    public String getObjIds()
    {
        return objIds;
    }
    public void setUserCode(String userCode)
    {
        this.userCode = userCode;
    }

    public String getUserCode()
    {
        return userCode;
    }
    public void setPayerFrom(String payerFrom)
    {
        this.payerFrom = payerFrom;
    }

    public String getPayerFrom() 
    {
        return payerFrom;
    }
    public void setPayerTo(String payerTo) 
    {
        this.payerTo = payerTo;
    }

    public String getPayerTo() 
    {
        return payerTo;
    }
    public void setTransactionHash(String transactionHash) 
    {
        this.transactionHash = transactionHash;
    }

    public String getTransactionHash() 
    {
        return transactionHash;
    }
    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }
    public void setAmountType(Integer amountType) 
    {
        this.amountType = amountType;
    }

    public Integer getAmountType() 
    {
        return amountType;
    }
    public void setRecordType(Integer recordType) 
    {
        this.recordType = recordType;
    }

    public Integer getRecordType() 
    {
        return recordType;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    public FilTransactionRecord (String objIds, String userCode, String payerFrom, String payerTo, String transactionHash,
                                 BigDecimal amount, Integer amountType, Integer recordType, Integer status){
        this.amount = amount;
        this.objIds = objIds;
        this.userCode = userCode;
        this.payerFrom = payerFrom;
        this.payerTo = payerTo;
        this.transactionHash = transactionHash;
        this.amountType = amountType;
        this.recordType = recordType;
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("objId", getObjIds())
            .append("userCode", getUserCode())
            .append("payerFrom", getPayerFrom())
            .append("payerTo", getPayerTo())
            .append("transactionHash", getTransactionHash())
            .append("amount", getAmount())
            .append("amountType", getAmountType())
            .append("recordType", getRecordType())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
