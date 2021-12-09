package com.wmapp.fil.service.impl;

import java.util.List;
import com.wmapp.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wmapp.fil.mapper.FilUserRevenueMapper;
import com.wmapp.fil.domain.FilUserRevenue;
import com.wmapp.fil.service.IFilUserRevenueService;

/**
 * 用户收益Service业务层处理
 * 
 * @author wmapp
 * @date 2021-11-05
 */
@Service
public class FilUserRevenueServiceImpl implements IFilUserRevenueService 
{
    @Autowired
    private FilUserRevenueMapper filUserRevenueMapper;

    /**
     * 查询用户收益
     * 
     * @param id 用户收益ID
     * @return 用户收益
     */
    @Override
    public FilUserRevenue selectFilUserRevenueById(Long id)
    {
        return filUserRevenueMapper.selectFilUserRevenueById(id);
    }

    /**
     * 查询用户收益列表
     * 
     * @param filUserRevenue 用户收益
     * @return 用户收益
     */
    @Override
    public List<FilUserRevenue> selectFilUserRevenueList(FilUserRevenue filUserRevenue)
    {
        return filUserRevenueMapper.selectFilUserRevenueList(filUserRevenue);
    }

    /**
     * 新增用户收益
     * 
     * @param filUserRevenue 用户收益
     * @return 结果
     */
    public int insertFilUserRevenue(FilUserRevenue filUserRevenue)
    {
        filUserRevenue.setCreateTime(DateUtils.getNowDate());
        return filUserRevenueMapper.insertFilUserRevenue(filUserRevenue);
    }

  }
