package com.wmapp.fil.domain;

import java.math.BigDecimal;

import com.wmapp.common.annotation.DataSource;
import com.wmapp.common.annotation.Excel;
import com.wmapp.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 节点对象 fil_node
 * 
 * @author wmapp
 * @date 2021-10-25
 */
@Data
public class FilNode extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  ID节点类型 */
    private Long id;

    /** 节点名称 */
    @Excel(name = "节点名称")
    private String nodeName;

    /** 节点类型1-节点 2-小节点 */
    @Excel(name = "节点类型 ")
    private Integer nodeType;

    /** 节点金额 */
    @Excel(name = "节点金额")
    private BigDecimal amount;

    /** 名额 */
    @Excel(name = "名额")
    private Integer quota;

    /** 已占用配额 */
    @Excel(name = "已占用配额")
    private Integer occupyQuota;

    /** 状态0-正常 1-禁用 */
    @Excel(name = "状态0-正常 1-禁用")
    private Integer status;


}
