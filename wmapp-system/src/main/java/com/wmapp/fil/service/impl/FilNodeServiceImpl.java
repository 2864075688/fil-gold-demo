package com.wmapp.fil.service.impl;

import java.util.List;
import com.wmapp.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wmapp.fil.mapper.FilNodeMapper;
import com.wmapp.fil.domain.FilNode;
import com.wmapp.fil.service.IFilNodeService;

/**
 * 节点Service业务层处理
 * 
 * @author wmapp
 * @date 2021-10-25
 */
@Service
public class FilNodeServiceImpl implements IFilNodeService 
{
    @Autowired
    private FilNodeMapper filNodeMapper;

    /**
     * 查询节点
     * 
     * @param id 节点ID
     * @return 节点
     */
    @Override
    public FilNode selectFilNodeById(Long id)
    {
        return filNodeMapper.selectFilNodeById(id);
    }

    /**
     * 查询节点列表
     * 
     * @param filNode 节点
     * @return 节点
     */
    @Override
    public List<FilNode> selectFilNodeList(FilNode filNode)
    {
        return filNodeMapper.selectFilNodeList(filNode);
    }

    /**
     * 新增节点
     * 
     * @param filNode 节点
     * @return 结果
     */
    @Override
    public int insertFilNode(FilNode filNode)
    {
        filNode.setCreateTime(DateUtils.getNowDate());
        return filNodeMapper.insertFilNode(filNode);
    }

    /**
     * 修改节点
     * 
     * @param filNode 节点
     * @return 结果
     */
    @Override
    public int updateFilNode(FilNode filNode)
    {
        filNode.setUpdateTime(DateUtils.getNowDate());
        return filNodeMapper.updateFilNode(filNode);
    }

    /**
     * 批量删除节点
     * 
     * @param ids 需要删除的节点ID
     * @return 结果
     */
    @Override
    public int deleteFilNodeByIds(Long[] ids)
    {
        return filNodeMapper.deleteFilNodeByIds(ids);
    }

    /**
     * 删除节点信息
     * 
     * @param id 节点ID
     * @return 结果
     */
    @Override
    public int deleteFilNodeById(Long id)
    {
        return filNodeMapper.deleteFilNodeById(id);
    }

    @Override
    public FilNode selectFilNodeByIdToUpdate(Long nodeId) {
        return filNodeMapper.selectFilNodeByIdToUpdate(nodeId);
    }
}
