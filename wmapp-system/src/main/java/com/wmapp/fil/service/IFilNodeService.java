package com.wmapp.fil.service;

import java.util.List;
import com.wmapp.fil.domain.FilNode;

/**
 * 节点Service接口
 * 
 * @author wmapp
 * @date 2021-10-25
 */
public interface IFilNodeService 
{
    /**
     * 查询节点
     * 
     * @param id 节点ID
     * @return 节点
     */
    public FilNode selectFilNodeById(Long id);

    /**
     * 查询节点列表
     * 
     * @param filNode 节点
     * @return 节点集合
     */
    public List<FilNode> selectFilNodeList(FilNode filNode);

    /**
     * 新增节点
     * 
     * @param filNode 节点
     * @return 结果
     */
    public int insertFilNode(FilNode filNode);

    /**
     * 修改节点
     * 
     * @param filNode 节点
     * @return 结果
     */
    public int updateFilNode(FilNode filNode);

    /**
     * 批量删除节点
     * 
     * @param ids 需要删除的节点ID
     * @return 结果
     */
    public int deleteFilNodeByIds(Long[] ids);

    /**
     * 删除节点信息
     * 
     * @param id 节点ID
     * @return 结果
     */
    public int deleteFilNodeById(Long id);

    /**
     * 节点加锁
     * @param nodeId
     * @return
     */
    FilNode selectFilNodeByIdToUpdate(Long nodeId);
}
