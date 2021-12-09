package com.wmapp.fil.mapper;

import java.util.List;
import com.wmapp.fil.domain.FilWithdrawRecord;

/**
 * 提现记录Mapper接口
 * 
 * @author wmapp
 * @date 2021-11-10
 */
public interface FilWithdrawRecordMapper 
{
    /**
     * 查询提现记录
     * 
     * @param id 提现记录ID
     * @return 提现记录
     */
    public FilWithdrawRecord selectFilWithdrawRecordById(Long id);

    /**
     * 查询提现记录列表
     * 
     * @param filWithdrawRecord 提现记录
     * @return 提现记录集合
     */
    public List<FilWithdrawRecord> selectFilWithdrawRecordList(FilWithdrawRecord filWithdrawRecord);

    /**
     * 新增提现记录
     * 
     * @param filWithdrawRecord 提现记录
     * @return 结果
     */
    public int insertFilWithdrawRecord(FilWithdrawRecord filWithdrawRecord);

    /**
     * 修改提现记录
     * 
     * @param filWithdrawRecord 提现记录
     * @return 结果
     */
    public int updateFilWithdrawRecord(FilWithdrawRecord filWithdrawRecord);

    /**
     * 删除提现记录
     * 
     * @param id 提现记录ID
     * @return 结果
     */
    public int deleteFilWithdrawRecordById(Long id);

    /**
     * 批量删除提现记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFilWithdrawRecordByIds(Long[] ids);
}
