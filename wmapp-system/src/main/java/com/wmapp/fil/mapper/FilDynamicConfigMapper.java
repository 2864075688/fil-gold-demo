package com.wmapp.fil.mapper;

import java.util.List;
import com.wmapp.fil.domain.FilDynamicConfig;

/**
 * 动态比例配置Mapper接口
 * 
 * @author wmapp
 * @date 2021-11-09
 */
public interface FilDynamicConfigMapper 
{
    /**
     * 查询动态比例配置
     * 
     * @param id 动态比例配置ID
     * @return 动态比例配置
     */
    public FilDynamicConfig selectFilDynamicConfigById(Long id);

    /**
     * 查询动态比例配置列表
     * 
     * @param filDynamicConfig 动态比例配置
     * @return 动态比例配置集合
     */
    public List<FilDynamicConfig> selectFilDynamicConfigList(FilDynamicConfig filDynamicConfig);

    /**
     * 新增动态比例配置
     * 
     * @param filDynamicConfig 动态比例配置
     * @return 结果
     */
    public int insertFilDynamicConfig(FilDynamicConfig filDynamicConfig);

    /**
     * 修改动态比例配置
     * 
     * @param filDynamicConfig 动态比例配置
     * @return 结果
     */
    public int updateFilDynamicConfig(FilDynamicConfig filDynamicConfig);

    /**
     * 删除动态比例配置
     * 
     * @param id 动态比例配置ID
     * @return 结果
     */
    public int deleteFilDynamicConfigById(Long id);

    /**
     * 批量删除动态比例配置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFilDynamicConfigByIds(Long[] ids);
}
