package com.wmapp.fil.service;

import java.util.List;
import com.wmapp.fil.domain.FilUserRevenue;

/**
 * 用户收益Service接口
 * 
 * @author wmapp
 * @date 2021-11-05
 */
public interface IFilUserRevenueService 
{
    /**
     * 查询用户收益
     * 
     * @param id 用户收益ID
     * @return 用户收益
     */
    public FilUserRevenue selectFilUserRevenueById(Long id);

    /**
     * 查询用户收益列表
     * 
     * @param filUserRevenue 用户收益
     * @return 用户收益集合
     */
    public List<FilUserRevenue> selectFilUserRevenueList(FilUserRevenue filUserRevenue);
}
