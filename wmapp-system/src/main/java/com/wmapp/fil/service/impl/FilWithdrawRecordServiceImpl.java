package com.wmapp.fil.service.impl;

import java.util.List;
import com.wmapp.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wmapp.fil.mapper.FilWithdrawRecordMapper;
import com.wmapp.fil.domain.FilWithdrawRecord;
import com.wmapp.fil.service.IFilWithdrawRecordService;

/**
 * 提现记录Service业务层处理
 * 
 * @author wmapp
 * @date 2021-11-10
 */
@Service
public class FilWithdrawRecordServiceImpl implements IFilWithdrawRecordService 
{
    @Autowired
    private FilWithdrawRecordMapper filWithdrawRecordMapper;

    /**
     * 查询提现记录
     * 
     * @param id 提现记录ID
     * @return 提现记录
     */
    @Override
    public FilWithdrawRecord selectFilWithdrawRecordById(Long id)
    {
        return filWithdrawRecordMapper.selectFilWithdrawRecordById(id);
    }

    /**
     * 查询提现记录列表
     * 
     * @param filWithdrawRecord 提现记录
     * @return 提现记录
     */
    @Override
    public List<FilWithdrawRecord> selectFilWithdrawRecordList(FilWithdrawRecord filWithdrawRecord)
    {
        return filWithdrawRecordMapper.selectFilWithdrawRecordList(filWithdrawRecord);
    }

    /**
     * 新增提现记录
     * 
     * @param filWithdrawRecord 提现记录
     * @return 结果
     */
    @Override
    public int insertFilWithdrawRecord(FilWithdrawRecord filWithdrawRecord)
    {
        filWithdrawRecord.setCreateTime(DateUtils.getNowDate());
        return filWithdrawRecordMapper.insertFilWithdrawRecord(filWithdrawRecord);
    }

    /**
     * 修改提现记录
     * 
     * @param filWithdrawRecord 提现记录
     * @return 结果
     */
    @Override
    public int updateFilWithdrawRecord(FilWithdrawRecord filWithdrawRecord)
    {
        filWithdrawRecord.setUpdateTime(DateUtils.getNowDate());
        return filWithdrawRecordMapper.updateFilWithdrawRecord(filWithdrawRecord);
    }

    /**
     * 批量删除提现记录
     * 
     * @param ids 需要删除的提现记录ID
     * @return 结果
     */
    @Override
    public int deleteFilWithdrawRecordByIds(Long[] ids)
    {
        return filWithdrawRecordMapper.deleteFilWithdrawRecordByIds(ids);
    }

    /**
     * 删除提现记录信息
     * 
     * @param id 提现记录ID
     * @return 结果
     */
    @Override
    public int deleteFilWithdrawRecordById(Long id)
    {
        return filWithdrawRecordMapper.deleteFilWithdrawRecordById(id);
    }
}
