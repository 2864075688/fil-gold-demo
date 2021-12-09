package com.wmapp.fil.mapper;

import java.util.List;
import com.wmapp.fil.domain.FilNodeApply;

/**
 * 节点申购订单Mapper接口
 * 
 * @author wmapp
 * @date 2021-11-10
 */
public interface FilNodeApplyMapper 
{
    /**
     * 查询节点申购订单
     * 
     * @param id 节点申购订单ID
     * @return 节点申购订单
     */
    public FilNodeApply selectFilNodeApplyById(Long id);

    /**
     * 查询节点申购订单列表
     * 
     * @param filNodeApply 节点申购订单
     * @return 节点申购订单集合
     */
    public List<FilNodeApply> selectFilNodeApplyList(FilNodeApply filNodeApply);

    /**
     * 新增节点申购订单
     * 
     * @param filNodeApply 节点申购订单
     * @return 结果
     */
    public int insertFilNodeApply(FilNodeApply filNodeApply);

    /**
     * 修改节点申购订单
     * 
     * @param filNodeApply 节点申购订单
     * @return 结果
     */
    public int updateFilNodeApply(FilNodeApply filNodeApply);

    /**
     * 删除节点申购订单
     * 
     * @param id 节点申购订单ID
     * @return 结果
     */
    public int deleteFilNodeApplyById(Long id);

    /**
     * 批量删除节点申购订单
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFilNodeApplyByIds(Long[] ids);
}
