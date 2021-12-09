package com.wmapp.fil.service.impl;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wmapp.fil.mapper.FilPlatformRevenueMapper;
import com.wmapp.fil.domain.FilPlatformRevenue;
import com.wmapp.fil.service.IFilPlatformRevenueService;

/**
 * 平台收入Service业务层处理
 * 
 * @author wmapp
 * @date 2021-11-05
 */
@Service
public class FilPlatformRevenueServiceImpl implements IFilPlatformRevenueService 
{
    @Autowired
    private FilPlatformRevenueMapper filPlatformRevenueMapper;

    /**
     * 查询平台收入
     * 
     * @param id 平台收入ID
     * @return 平台收入
     */
    @Override
    public FilPlatformRevenue selectFilPlatformRevenueById(Long id)
    {
        return filPlatformRevenueMapper.selectFilPlatformRevenueById(id);
    }

    /**
     * 查询平台收入列表
     * 
     * @param filPlatformRevenue 平台收入
     * @return 平台收入
     */
    @Override
    public List<FilPlatformRevenue> selectFilPlatformRevenueList(FilPlatformRevenue filPlatformRevenue)
    {
        return filPlatformRevenueMapper.selectFilPlatformRevenueList(filPlatformRevenue);
    }

    @Override
    public BigDecimal getTotalPlatFormAmount() {
        return filPlatformRevenueMapper.getTotalPlatFormAmount();
    }

    /**
     * 新增平台收入
     *
     * @param filPlatformRevenue 平台收入
     * @return 结果
     */
    public int insertFilPlatformRevenue(FilPlatformRevenue filPlatformRevenue)
    {
        return filPlatformRevenueMapper.insertFilPlatformRevenue(filPlatformRevenue);
    }

}
