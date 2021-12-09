package com.wmapp.fil.service.impl;

import java.util.List;
import com.wmapp.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wmapp.fil.mapper.FilInvestConfigMapper;
import com.wmapp.fil.domain.FilInvestConfig;
import com.wmapp.fil.service.IFilInvestConfigService;

/**
 * 复投配置Service业务层处理
 * 
 * @author wmapp
 * @date 2021-10-25
 */
@Service
public class FilInvestConfigServiceImpl implements IFilInvestConfigService 
{
    @Autowired
    private FilInvestConfigMapper filInvestConfigMapper;

    /**
     * 查询复投配置
     * 
     * @param id 复投配置ID
     * @return 复投配置
     */
    @Override
    public FilInvestConfig selectFilInvestConfigById(Long id)
    {
        return filInvestConfigMapper.selectFilInvestConfigById(id);
    }

    /**
     * 查询复投配置列表
     * 
     * @param filInvestConfig 复投配置
     * @return 复投配置
     */
    @Override
    public List<FilInvestConfig> selectFilInvestConfigList(FilInvestConfig filInvestConfig)
    {
        return filInvestConfigMapper.selectFilInvestConfigList(filInvestConfig);
    }

    /**
     * 新增复投配置
     * 
     * @param filInvestConfig 复投配置
     * @return 结果
     */
    @Override
    public int insertFilInvestConfig(FilInvestConfig filInvestConfig)
    {
        filInvestConfig.setCreateTime(DateUtils.getNowDate());
        return filInvestConfigMapper.insertFilInvestConfig(filInvestConfig);
    }

    /**
     * 修改复投配置
     * 
     * @param filInvestConfig 复投配置
     * @return 结果
     */
    @Override
    public int updateFilInvestConfig(FilInvestConfig filInvestConfig)
    {
        filInvestConfig.setUpdateTime(DateUtils.getNowDate());
        return filInvestConfigMapper.updateFilInvestConfig(filInvestConfig);
    }

    /**
     * 批量删除复投配置
     * 
     * @param ids 需要删除的复投配置ID
     * @return 结果
     */
    @Override
    public int deleteFilInvestConfigByIds(Long[] ids)
    {
        return filInvestConfigMapper.deleteFilInvestConfigByIds(ids);
    }

    /**
     * 删除复投配置信息
     * 
     * @param id 复投配置ID
     * @return 结果
     */
    @Override
    public int deleteFilInvestConfigById(Long id)
    {
        return filInvestConfigMapper.deleteFilInvestConfigById(id);
    }

    @Override
    public Boolean checkInvestNum(FilInvestConfig filInvestConfig) {
        Integer count = filInvestConfigMapper.checkInvestNum(filInvestConfig);
        if(count>0){
            return true;
        }
        return false;
    }

    @Override
    public FilInvestConfig getConfigByUserId(Long userId) {
        return filInvestConfigMapper.getConfigByUserId(userId);
    }
}
