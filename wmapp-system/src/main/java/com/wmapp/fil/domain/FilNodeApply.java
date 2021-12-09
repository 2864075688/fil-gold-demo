package com.wmapp.fil.domain;

import java.math.BigDecimal;
import com.wmapp.common.annotation.Excel;
import com.wmapp.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 节点申购订单对象 fil_node_apply
 * 
 * @author wmapp
 * @date 2021-11-10
 */
public class FilNodeApply extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 节点申购记录 */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 节点ID */
    @Excel(name = "节点ID")
    private Long nodeId;

    /** 用户code */
    @Excel(name = "用户code")
    private String userCode;

    /** 交易hash */
    @Excel(name = "交易hash")
    private String transactionHash;

    /** 节点类型0-大节点 1小节点 */
    @Excel(name = "节点类型0-大节点 1小节点")
    private Integer nodeType;

    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal amount;

    /** 状态 */
    @Excel(name = "状态")
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
    public void setNodeId(Long nodeId) 
    {
        this.nodeId = nodeId;
    }

    public Long getNodeId() 
    {
        return nodeId;
    }
    public void setUserCode(String userCode)
    {
        this.userCode = userCode;
    }

    public String getUserCode()
    {
        return userCode;
    }
    public void setTransactionHash(String transactionHash) 
    {
        this.transactionHash = transactionHash;
    }

    public String getTransactionHash() 
    {
        return transactionHash;
    }
    public void setNodeType(Integer nodeType) 
    {
        this.nodeType = nodeType;
    }

    public Integer getNodeType() 
    {
        return nodeType;
    }
    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("nodeId", getNodeId())
            .append("userCode", getUserCode())
            .append("transactionHash", getTransactionHash())
            .append("nodeType", getNodeType())
            .append("amount", getAmount())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .toString();
    }
}
