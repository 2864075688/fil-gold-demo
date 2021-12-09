package com.wmapp.fil.mapper;

import java.util.List;
import com.wmapp.fil.domain.FilInvestConfig;

/**
 * 复投配置Mapper接口
 * 
 * @author wmapp
 * @date 2021-10-25
 */
public interface FilInvestConfigMapper 
{
    /**
     * 查询复投配置
     * 
     * @param id 复投配置ID
     * @return 复投配置
     */
    public FilInvestConfig selectFilInvestConfigById(Long id);

    /**
     * 查询复投配置列表
     * 
     * @param filInvestConfig 复投配置
     * @return 复投配置集合
     */
    public List<FilInvestConfig> selectFilInvestConfigList(FilInvestConfig filInvestConfig);

    /**
     * 新增复投配置
     * 
     * @param filInvestConfig 复投配置
     * @return 结果
     */
    public int insertFilInvestConfig(FilInvestConfig filInvestConfig);

    /**
     * 修改复投配置
     * 
     * @param filInvestConfig 复投配置
     * @return 结果
     */
    public int updateFilInvestConfig(FilInvestConfig filInvestConfig);

    /**
     * 删除复投配置
     * 
     * @param id 复投配置ID
     * @return 结果
     */
    public int deleteFilInvestConfigById(Long id);

    /**
     * 批量删除复投配置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFilInvestConfigByIds(Long[] ids);

    /***
     * 查询有没有重复的直推人数
     * @param filInvestConfig
     * @return
     */
    Integer checkInvestNum(FilInvestConfig filInvestConfig);

    /**
     * 根据用户id查询提现比例
     * @param userId
     * @return
     */
    public FilInvestConfig getConfigByUserId(Long userId);
}
