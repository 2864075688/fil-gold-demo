package com.wmapp.fil.domain;

import java.math.BigDecimal;
import com.wmapp.common.annotation.Excel;
import com.wmapp.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 动态比例配置对象 fil_dynamic_config
 * 
 * @author wmapp
 * @date 2021-11-09
 */
public class FilDynamicConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 配置名称 */
    @Excel(name = "配置名称")
    private String configName;

    /** 人数 */
    @Excel(name = "人数")
    private Integer peopleNum;

    /** 几代 */
    @Excel(name = "几代")
    private Integer eraNum;

    /** 百分比 */
    @Excel(name = "百分比")
    private BigDecimal takeRate;

    /** 状态0-正常，1-禁用 */
    @Excel(name = "状态0-正常，1-禁用")
    private Integer status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setConfigName(String configName) 
    {
        this.configName = configName;
    }

    public String getConfigName() 
    {
        return configName;
    }
    public void setPeopleNum(Integer peopleNum)
    {
        this.peopleNum = peopleNum;
    }

    public Integer getPeopleNum()
    {
        return peopleNum;
    }
    public void setTakeRate(BigDecimal takeRate) 
    {
        this.takeRate = takeRate;
    }

    public BigDecimal getTakeRate() 
    {
        return takeRate;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    public void setEraNum(Integer eraNum)
    {
        this.eraNum = eraNum;
    }

    public Integer getEraNum()
    {
        return eraNum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("configName", getConfigName())
            .append("peopleNum", getPeopleNum())
            .append("eraNum", getEraNum())
            .append("takeRate", getTakeRate())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
