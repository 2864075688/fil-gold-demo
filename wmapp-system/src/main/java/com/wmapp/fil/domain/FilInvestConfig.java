package com.wmapp.fil.domain;

import java.math.BigDecimal;
import com.wmapp.common.annotation.Excel;
import com.wmapp.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 复投配置对象 fil_invest_config
 * 
 * @author wmapp
 * @date 2021-10-25
 */
@Data
public class FilInvestConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  ID */
    private Long id;

    /** 配置名称 */
    @Excel(name = "配置名称")
    private String configName;

    /** 直推最小值 */
    @Excel(name = "直推最小值")
    private Integer investMin;

    /** 直推最大值 */
    @Excel(name = "直推最大值")
    private Integer investMax;

    /** 提现比例 */
    @Excel(name = "提现比例")
    private BigDecimal cashRate;

    /** 复投比例 */
    @Excel(name = "复投比例")
    private BigDecimal investRate;

    /** 状态0-正常 1-禁用 */
    @Excel(name = "状态0-正常 1-禁用")
    private Integer status;
    /***
     * 非数据库字段
     */
    private Integer investNum;

}
