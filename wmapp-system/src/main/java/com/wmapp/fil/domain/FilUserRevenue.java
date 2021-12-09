package com.wmapp.fil.domain;

import java.math.BigDecimal;
import com.wmapp.common.annotation.Excel;
import com.wmapp.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户收益对象 fil_user_revenue
 * 
 * @author wmapp
 * @date 2021-11-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilUserRevenue extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 用户code */
    @Excel(name = "用户code")
    private String userCode;

    /** 投入ID */
    @Excel(name = "投入ID")
    private Long investId;

    /** 收益金额 */
    @Excel(name = "收益金额")
    private BigDecimal amount;

    /** 类型0-投资收益；1-节点收益；2-静态收益；3-动态收益 */
    @Excel(name = "类型0-投资收益；1-节点收益；2-静态收益；3-动态收益")
    private Integer type;

    public FilUserRevenue(Long userId, String userCode, Long investId, BigDecimal amount, Integer type) {
        this.userId = userId;
        this.userCode = userCode;
        this.investId = investId;
        this.amount = amount;
        this.type = type;
    }
}
