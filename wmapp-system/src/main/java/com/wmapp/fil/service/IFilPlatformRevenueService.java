package com.wmapp.fil.service;

import java.math.BigDecimal;
import java.util.List;
import com.wmapp.fil.domain.FilPlatformRevenue;

/**
 * 平台收入Service接口
 * 
 * @author wmapp
 * @date 2021-11-05
 */
public interface IFilPlatformRevenueService 
{
    /**
     * 查询平台收入
     * 
     * @param id 平台收入ID
     * @return 平台收入
     */
    public FilPlatformRevenue selectFilPlatformRevenueById(Long id);

    /**
     * 查询平台收入列表
     * 
     * @param filPlatformRevenue 平台收入
     * @return 平台收入集合
     */
    public List<FilPlatformRevenue> selectFilPlatformRevenueList(FilPlatformRevenue filPlatformRevenue);

    /**
     * 获取平台收入
     * @return
     */
    BigDecimal getTotalPlatFormAmount();
}
