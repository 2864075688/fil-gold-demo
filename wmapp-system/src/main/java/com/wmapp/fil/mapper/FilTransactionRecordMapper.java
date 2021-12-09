package com.wmapp.fil.mapper;

import java.util.List;
import com.wmapp.fil.domain.FilTransactionRecord;
import org.apache.ibatis.annotations.Param;

/**
 * 交易记录Mapper接口
 * 
 * @author wmapp
 * @date 2021-11-10
 */
public interface FilTransactionRecordMapper 
{
    /**
     * 查询交易记录
     * 
     * @param id 交易记录ID
     * @return 交易记录
     */
    public FilTransactionRecord selectFilTransactionRecordById(Long id);

    /**
     * 查询交易记录列表
     * 
     * @param filTransactionRecord 交易记录
     * @return 交易记录集合
     */
    public List<FilTransactionRecord> selectFilTransactionRecordList(FilTransactionRecord filTransactionRecord);

    /**
     * 新增交易记录
     * 
     * @param filTransactionRecord 交易记录
     * @return 结果
     */
    public int insertFilTransactionRecord(FilTransactionRecord filTransactionRecord);

    /**
     * 修改交易记录
     * 
     * @param filTransactionRecord 交易记录
     * @return 结果
     */
    public int updateFilTransactionRecord(FilTransactionRecord filTransactionRecord);

    /**
     * 删除交易记录
     * 
     * @param id 交易记录ID
     * @return 结果
     */
    public int deleteFilTransactionRecordById(Long id);

    /**
     * 批量删除交易记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFilTransactionRecordByIds(Long[] ids);

    /**
     * 根据hash判断有没有被使用
     * @param transactionHash
     * @return
     */
    int getExistHashCount(@Param("transactionHash") String transactionHash);

    public List<FilTransactionRecord> selectFilTransactionRecordListCustom();


}
