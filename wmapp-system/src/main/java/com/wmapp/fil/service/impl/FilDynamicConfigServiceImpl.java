package com.wmapp.fil.service.impl;

import java.util.List;
import com.wmapp.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wmapp.fil.mapper.FilDynamicConfigMapper;
import com.wmapp.fil.domain.FilDynamicConfig;
import com.wmapp.fil.service.IFilDynamicConfigService;

/**
 * 动态比例配置Service业务层处理
 * 
 * @author wmapp
 * @date 2021-11-09
 */
@Service
public class FilDynamicConfigServiceImpl implements IFilDynamicConfigService 
{
    @Autowired
    private FilDynamicConfigMapper filDynamicConfigMapper;

    /**
     * 查询动态比例配置
     * 
     * @param id 动态比例配置ID
     * @return 动态比例配置
     */
    @Override
    public FilDynamicConfig selectFilDynamicConfigById(Long id)
    {
        return filDynamicConfigMapper.selectFilDynamicConfigById(id);
    }

    /**
     * 查询动态比例配置列表
     * 
     * @param filDynamicConfig 动态比例配置
     * @return 动态比例配置
     */
    @Override
    public List<FilDynamicConfig> selectFilDynamicConfigList(FilDynamicConfig filDynamicConfig)
    {
        return filDynamicConfigMapper.selectFilDynamicConfigList(filDynamicConfig);
    }

    /**
     * 新增动态比例配置
     * 
     * @param filDynamicConfig 动态比例配置
     * @return 结果
     */
    @Override
    public int insertFilDynamicConfig(FilDynamicConfig filDynamicConfig)
    {
        filDynamicConfig.setCreateTime(DateUtils.getNowDate());
        return filDynamicConfigMapper.insertFilDynamicConfig(filDynamicConfig);
    }

    /**
     * 修改动态比例配置
     * 
     * @param filDynamicConfig 动态比例配置
     * @return 结果
     */
    @Override
    public int updateFilDynamicConfig(FilDynamicConfig filDynamicConfig)
    {
        filDynamicConfig.setUpdateTime(DateUtils.getNowDate());
        return filDynamicConfigMapper.updateFilDynamicConfig(filDynamicConfig);
    }

    /**
     * 批量删除动态比例配置
     * 
     * @param ids 需要删除的动态比例配置ID
     * @return 结果
     */
    @Override
    public int deleteFilDynamicConfigByIds(Long[] ids)
    {
        return filDynamicConfigMapper.deleteFilDynamicConfigByIds(ids);
    }

    /**
     * 删除动态比例配置信息
     * 
     * @param id 动态比例配置ID
     * @return 结果
     */
    @Override
    public int deleteFilDynamicConfigById(Long id)
    {
        return filDynamicConfigMapper.deleteFilDynamicConfigById(id);
    }
}
