package com.wmapp.fil.service.impl;

import java.math.BigDecimal;
import java.util.List;
import com.wmapp.common.utils.DateUtils;
import com.wmapp.fil.domain.FilNode;
import com.wmapp.fil.domain.FilTransactionRecord;
import com.wmapp.fil.domain.FilUser;
import com.wmapp.fil.mapper.FilNodeMapper;
import com.wmapp.fil.mapper.FilTransactionRecordMapper;
import com.wmapp.fil.mapper.FilUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wmapp.fil.mapper.FilNodeApplyMapper;
import com.wmapp.fil.domain.FilNodeApply;
import com.wmapp.fil.service.IFilNodeApplyService;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import javax.annotation.Resource;

/**
 * 节点申购订单Service业务层处理
 * 
 * @author wmapp
 * @date 2021-11-10
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class FilNodeApplyServiceImpl implements IFilNodeApplyService 
{
    @Autowired
    private FilNodeMapper filNodeMapper;

    @Autowired
    private FilNodeApplyMapper filNodeApplyMapper;

    @Autowired
    private FilUserMapper filUserMapper;

    @Resource
    private FilTransactionRecordMapper filTransactionRecordMapper;

    /**
     * 查询节点申购订单
     * 
     * @param id 节点申购订单ID
     * @return 节点申购订单
     */
    @Override
    public FilNodeApply selectFilNodeApplyById(Long id)
    {
        return filNodeApplyMapper.selectFilNodeApplyById(id);
    }

    /**
     * 查询节点申购订单列表
     * 
     * @param filNodeApply 节点申购订单
     * @return 节点申购订单
     */
    @Override
    public List<FilNodeApply> selectFilNodeApplyList(FilNodeApply filNodeApply)
    {
        return filNodeApplyMapper.selectFilNodeApplyList(filNodeApply);
    }

    @Override
    public Integer save(FilUser user,TransactionReceipt receipt, FilNode node) {
        String userCode  = receipt.getFrom();
        BigDecimal price = node.getAmount();
        if(user==null){
            user = new FilUser();
            //保存用户
            user.setPid(0l);
            user.setUserCode(userCode);
            user.setNodeId(node.getId());
            user.setNodeType(node.getNodeType());
            filUserMapper.insertFilUser(user);
        }else{//更新用户节点
            user.setNodeId(node.getId());
            user.setNodeType(node.getNodeType());
            filUserMapper.updateFilUser(user);
        }
        //占用配额
        node.setOccupyQuota(node.getOccupyQuota()+1);
        filNodeMapper.updateFilNode(node);
        FilNodeApply apply = new FilNodeApply();
        apply.setUserId(user.getId());
        apply.setAmount(price);
        apply.setNodeId(node.getId());
        apply.setNodeType(node.getNodeType());
        apply.setTransactionHash(receipt.getTransactionHash());
        apply.setUserCode(user.getUserCode());
        insertFilNodeApply(apply);
        //保存交易记录表
        Integer result = filTransactionRecordMapper.insertFilTransactionRecord(new FilTransactionRecord(apply.getId()+"",
                user.getUserCode(), receipt.getFrom(), receipt.getTo(),
                receipt.getTransactionHash(),price, 0, 3, 2));
        return result;
    }

    /**
     * 新增节点申购订单
     * 
     * @param filNodeApply 节点申购订单
     * @return 结果
     */
    public int insertFilNodeApply(FilNodeApply filNodeApply)
    {
        filNodeApply.setCreateTime(DateUtils.getNowDate());
        return filNodeApplyMapper.insertFilNodeApply(filNodeApply);
    }

}
