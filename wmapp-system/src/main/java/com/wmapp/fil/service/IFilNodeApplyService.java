package com.wmapp.fil.service;

import java.util.List;

import com.wmapp.fil.domain.FilNode;
import com.wmapp.fil.domain.FilNodeApply;
import com.wmapp.fil.domain.FilUser;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/**
 * 节点申购订单Service接口
 * 
 * @author wmapp
 * @date 2021-11-10
 */
public interface IFilNodeApplyService 
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
     * 保存申购记录
     * @param receipt 交易信息
     * @param node 节点信息
     * @return
     */
    Integer save(FilUser user,TransactionReceipt receipt, FilNode node);
}
