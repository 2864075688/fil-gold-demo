package com.wmapp.fil.mapper;

import java.util.List;
import com.wmapp.fil.domain.FilInvestLog;

/**
 * 投入记录Mapper接口
 * 
 * @author wmapp
 * @date 2021-10-26
 */
public interface FilInvestLogMapper 
{
    /**
     * 查询投入记录
     * 
     * @param id 投入记录ID
     * @return 投入记录
     */
    public FilInvestLog selectFilInvestLogById(Long id);

    /**
     * 查询投入记录列表
     * 
     * @param filInvestLog 投入记录
     * @return 投入记录集合
     */
    public List<FilInvestLog> selectFilInvestLogList(FilInvestLog filInvestLog);

    /**
     * 新增投入记录
     * 
     * @param filInvestLog 投入记录
     * @return 结果
     */
    public int insertFilInvestLog(FilInvestLog filInvestLog);

    /**
     * 修改投入记录
     * 
     * @param filInvestLog 投入记录
     * @return 结果
     */
    public int updateFilInvestLog(FilInvestLog filInvestLog);

    /**
     * 删除投入记录
     * 
     * @param id 投入记录ID
     * @return 结果
     */
    public int deleteFilInvestLogById(Long id);

    /**
     * 批量删除投入记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFilInvestLogByIds(Long[] ids);
}
