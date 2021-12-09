package com.wmapp.fil.mapper;

import java.util.List;
import com.wmapp.fil.domain.FilUserRevenue;

/**
 * 用户收益Mapper接口
 * 
 * @author wmapp
 * @date 2021-11-05
 */
public interface FilUserRevenueMapper 
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

    /**
     * 新增用户收益
     * 
     * @param filUserRevenue 用户收益
     * @return 结果
     */
    public int insertFilUserRevenue(FilUserRevenue filUserRevenue);

    /**
     * 批量保存记录表
     * @param list
     * @return
     */
    public int insertBatchUserRevenue(List<FilUserRevenue> list);

}
