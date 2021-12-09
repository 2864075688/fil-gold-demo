package com.wmapp.fil.domain;

import java.math.BigDecimal;
import com.wmapp.common.annotation.Excel;
import com.wmapp.common.core.domain.BaseEntity;
import com.wmapp.common.utils.BigDecimalUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户对象 fil_user
 * 
 * @author wmapp
 * @date 2021-10-26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  ID */
    private Long id;

    /** 父id */
    @Excel(name = "父id")
    private Long pid;

    /** 根ids*/
    private String rootIds;

    private String parentCode;

    /** 用户唯一code */
    @Excel(name = "用户唯一code")
    private String userCode;

    /** 节点ID */
    @Excel(name = "节点ID")
    private Long nodeId;

    /** 总投资 */
    @Excel(name = "总投资")
    private BigDecimal totalInvest;

    /** 总复投金额  */
    private BigDecimal repeatInvest;

    /** 总收益 */
    private BigDecimal totalRevenue;

    /** 可提现金额 */
    @Excel(name = "可提现金额")
    private BigDecimal withdrawMoney;

    /** 已提现金额 */
    @Excel(name = "已提现金额")
    private BigDecimal pastMoney;

    /** 节点收益 */
    @Excel(name = "节点收益")
    private BigDecimal nodeRevenue;

    /** 静态收益 */
    @Excel(name = "静态收益")
    private BigDecimal staticRevenue;

    /** 动态收益 */
    @Excel(name = "动态收益")
    private BigDecimal dynamicRevenue;

    /** 节点类型0-普通 1-大节点 2-小节点 */
    @Excel(name = "节点类型0-普通 1-大节点 2-小节点")
    private Integer nodeType;

    /** 状态0-正常 1-禁用 */
    @Excel(name = "状态0-未激活 1-已激活")
    private Integer status;

    /** 授权状态0-未授权 1-已授权 */
    private Integer authStatus;

    /**  获取层级 */
    private Integer level;

    public BigDecimal getTotalRevenue() {
        if(staticRevenue!=null){
            totalRevenue = BigDecimalUtil.add(totalRevenue,staticRevenue);
        }
        if(dynamicRevenue!=null){
            totalRevenue = BigDecimalUtil.add(totalRevenue,dynamicRevenue);
        }
        if(nodeRevenue!=null){
            totalRevenue = BigDecimalUtil.add(totalRevenue,nodeRevenue);
        }
        return totalRevenue;
    }
    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
