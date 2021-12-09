package com.wmapp.fil.service.impl;

import java.util.List;
import com.wmapp.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wmapp.fil.mapper.FilTransactionRecordMapper;
import com.wmapp.fil.domain.FilTransactionRecord;
import com.wmapp.fil.service.IFilTransactionRecordService;

/**
 * 交易记录Service业务层处理
 * 
 * @author wmapp
 * @date 2021-11-10
 */
@Service
public class FilTransactionRecordServiceImpl implements IFilTransactionRecordService 
{
    @Autowired
    private FilTransactionRecordMapper filTransactionRecordMapper;

    /**
     * 查询交易记录
     * 
     * @param id 交易记录ID
     * @return 交易记录
     */
    @Override
    public FilTransactionRecord selectFilTransactionRecordById(Long id)
    {
        return filTransactionRecordMapper.selectFilTransactionRecordById(id);
    }

    /**
     * 查询交易记录列表
     * 
     * @param filTransactionRecord 交易记录
     * @return 交易记录
     */
    @Override
    public List<FilTransactionRecord> selectFilTransactionRecordList(FilTransactionRecord filTransactionRecord)
    {
        return filTransactionRecordMapper.selectFilTransactionRecordList(filTransactionRecord);
    }

    @Override
    public List<FilTransactionRecord> selectFilTransactionRecordListCustom() {
        return filTransactionRecordMapper.selectFilTransactionRecordListCustom();
    }

    /**
     * 新增交易记录
     * 
     * @param filTransactionRecord 交易记录
     * @return 结果
     */
    @Override
    public int insertFilTransactionRecord(FilTransactionRecord filTransactionRecord)
    {
        filTransactionRecord.setCreateTime(DateUtils.getNowDate());
        return filTransactionRecordMapper.insertFilTransactionRecord(filTransactionRecord);
    }

    /**
     * 修改交易记录
     * 
     * @param filTransactionRecord 交易记录
     * @return 结果
     */
    @Override
    public int updateFilTransactionRecord(FilTransactionRecord filTransactionRecord)
    {
        filTransactionRecord.setUpdateTime(DateUtils.getNowDate());
        return filTransactionRecordMapper.updateFilTransactionRecord(filTransactionRecord);
    }

    /**
     * 批量删除交易记录
     * 
     * @param ids 需要删除的交易记录ID
     * @return 结果
     */
    @Override
    public int deleteFilTransactionRecordByIds(Long[] ids)
    {
        return filTransactionRecordMapper.deleteFilTransactionRecordByIds(ids);
    }

    /**
     * 删除交易记录信息
     * 
     * @param id 交易记录ID
     * @return 结果
     */
    @Override
    public int deleteFilTransactionRecordById(Long id)
    {
        return filTransactionRecordMapper.deleteFilTransactionRecordById(id);
    }

    @Override
    public int getExistHashCount(String transactionHash) {
        return filTransactionRecordMapper.getExistHashCount(transactionHash);
    }
}
