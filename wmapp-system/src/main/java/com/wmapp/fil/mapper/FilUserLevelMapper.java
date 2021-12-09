package com.wmapp.fil.mapper;

import java.util.List;
import com.wmapp.fil.domain.FilUserLevel;
import org.apache.ibatis.annotations.Param;

/**
 * 用户层级Mapper接口
 * 
 * @author wmapp
 * @date 2021-10-26
 */
public interface FilUserLevelMapper 
{
    /**
     * 查询用户层级
     * 
     * @param userId 用户层级ID
     * @return 用户层级
     */
    public FilUserLevel selectFilUserLevelById(Long userId);

    /**
     * 查询用户层级列表
     * 
     * @param filUserLevel 用户层级
     * @return 用户层级集合
     */
    public List<FilUserLevel> selectFilUserLevelList(FilUserLevel filUserLevel);

    /**
     * 新增用户层级
     * 
     * @param filUserLevel 用户层级
     * @return 结果
     */
    public int insertFilUserLevel(FilUserLevel filUserLevel);

    /**
     * 批量添加用户深度表
     * @param levelList
     */
    public int insertBatchUserLevel(@Param("list") List<FilUserLevel> levelList);
}
