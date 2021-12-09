package com.wmapp.fil.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wmapp.common.annotation.Excel;
import com.wmapp.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 平台收入对象 fil_platform_revenue
 * 
 * @author wmapp
 * @date 2021-11-05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilPlatformRevenue extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 投入id */
    @Excel(name = "投入id")
    private Long investId;

    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal amount;

    /** 钱包地址 */
    @Excel(name = "钱包地址")
    private String address;

    /** 记录类型0-平台 1-节点沉淀资金 2动态沉淀资金 3静态沉淀资金*/
    private Integer type;

    public FilPlatformRevenue(Long userId, Long investId, BigDecimal amount, String address, Integer type) {
        this.userId = userId;
        this.investId = investId;
        this.amount = amount;
        this.address = address;
        this.type = type;
    }

}
