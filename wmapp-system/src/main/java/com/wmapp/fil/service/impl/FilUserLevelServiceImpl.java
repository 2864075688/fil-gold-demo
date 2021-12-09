package com.wmapp.fil.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wmapp.fil.mapper.FilUserLevelMapper;
import com.wmapp.fil.domain.FilUserLevel;
import com.wmapp.fil.service.IFilUserLevelService;

/**
 * 用户层级Service业务层处理
 * 
 * @author wmapp
 * @date 2021-10-26
 */
@Service
public class FilUserLevelServiceImpl implements IFilUserLevelService 
{
    @Autowired
    private FilUserLevelMapper filUserLevelMapper;

    /**
     * 查询用户层级
     * 
     * @param userId 用户层级ID
     * @return 用户层级
     */
    @Override
    public FilUserLevel selectFilUserLevelById(Long userId)
    {
        return filUserLevelMapper.selectFilUserLevelById(userId);
    }

    /**
     * 查询用户层级列表
     * 
     * @param filUserLevel 用户层级
     * @return 用户层级
     */
    @Override
    public List<FilUserLevel> selectFilUserLevelList(FilUserLevel filUserLevel)
    {
        return filUserLevelMapper.selectFilUserLevelList(filUserLevel);
    }

    /**
     * 新增用户层级
     * 
     * @param filUserLevel 用户层级
     * @return 结果
     */
    @Override
    public int insertFilUserLevel(FilUserLevel filUserLevel)
    {
        return filUserLevelMapper.insertFilUserLevel(filUserLevel);
    }

}
