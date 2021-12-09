package com.wmapp.fil.service;

import java.util.List;
import com.wmapp.fil.domain.FilInvestConfig;

/**
 * 复投配置Service接口
 * 
 * @author wmapp
 * @date 2021-10-25
 */
public interface IFilInvestConfigService 
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
     * 批量删除复投配置
     * 
     * @param ids 需要删除的复投配置ID
     * @return 结果
     */
    public int deleteFilInvestConfigByIds(Long[] ids);

    /**
     * 删除复投配置信息
     * 
     * @param id 复投配置ID
     * @return 结果
     */
    public int deleteFilInvestConfigById(Long id);

    /***
     * 判断直推人数是否重复存在
     * @param filInvestConfig
     * @return
     */
    Boolean checkInvestNum(FilInvestConfig filInvestConfig);

    /**
     * 根据用户id查询所属可提现复投比例
     * @param userId
     * @return
     */
    FilInvestConfig getConfigByUserId(Long userId);
}
