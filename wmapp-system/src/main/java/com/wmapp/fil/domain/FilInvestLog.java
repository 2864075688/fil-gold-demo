package com.wmapp.fil.domain;

import java.math.BigDecimal;
import com.wmapp.common.annotation.Excel;
import com.wmapp.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 投入记录对象 fil_invest_log
 * 
 * @author wmapp
 * @date 2021-10-26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilInvestLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 投入记录ID */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 用户id */
    @Excel(name = "用户id")
    private String userCode;

    /** 投入金额 */
    @Excel(name = "投入金额")
    private BigDecimal investAmount;

    /** 手续费 */
    @Excel(name = "手续费")
    private BigDecimal chargeAmout;

    /** 交易hash块 */
    @Excel(name = "交易hash块")
    private String transactionHash;

    public FilInvestLog(Long userId,String userCode,BigDecimal investAmount,BigDecimal chargeAmout,String transactionHash){
        this.userCode = userCode;
        this.userId = userId;
        this.investAmount = investAmount;
        this.chargeAmout = chargeAmout;
        this.transactionHash = transactionHash;
    }
}
