package com.wmapp.fil.mapper;

import java.math.BigDecimal;
import java.util.List;
import com.wmapp.fil.domain.FilPlatformRevenue;

/**
 * 平台收入Mapper接口
 * 
 * @author wmapp
 * @date 2021-11-05
 */
public interface FilPlatformRevenueMapper 
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
     * 新增平台收入
     * 
     * @param filPlatformRevenue 平台收入
     * @return 结果
     */
    public int insertFilPlatformRevenue(FilPlatformRevenue filPlatformRevenue);

    /**
     * 批量保存平台收益表
     * @param platformRevenueList
     * @return
     */
    int insertBatch(List<FilPlatformRevenue> platformRevenueList);

    BigDecimal getTotalPlatFormAmount();
}
