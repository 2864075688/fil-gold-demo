package com.wmapp.web.controller.fil;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wmapp.common.annotation.Log;
import com.wmapp.common.core.controller.BaseController;
import com.wmapp.common.core.domain.AjaxResult;
import com.wmapp.common.enums.BusinessType;
import com.wmapp.fil.domain.FilTransactionRecord;
import com.wmapp.fil.service.IFilTransactionRecordService;
import com.wmapp.common.utils.poi.ExcelUtil;
import com.wmapp.common.core.page.TableDataInfo;

/**
 * 交易记录Controller
 * 
 * @author wmapp
 * @date 2021-11-13
 */
@RestController
@RequestMapping("/fil/transactionRecord")
public class FilTransactionRecordController extends BaseController
{
    @Autowired
    private IFilTransactionRecordService filTransactionRecordService;

    /**
     * 查询交易记录列表
     */
    @PreAuthorize("@ss.hasPermi('fil:transactionRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(FilTransactionRecord filTransactionRecord)
    {
        startPage();
        List<FilTransactionRecord> list = filTransactionRecordService.selectFilTransactionRecordList(filTransactionRecord);
        return getDataTable(list);
    }

    /**
     * 导出交易记录列表
     */
    @PreAuthorize("@ss.hasPermi('fil:transactionRecord:export')")
    @Log(title = "交易记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FilTransactionRecord filTransactionRecord)
    {
        List<FilTransactionRecord> list = filTransactionRecordService.selectFilTransactionRecordList(filTransactionRecord);
        ExcelUtil<FilTransactionRecord> util = new ExcelUtil<FilTransactionRecord>(FilTransactionRecord.class);
        return util.exportExcel(list, "交易记录数据");
    }

    /**
     * 获取交易记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('fil:transactionRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(filTransactionRecordService.selectFilTransactionRecordById(id));
    }

    /**
     * 新增交易记录
     */
    @PreAuthorize("@ss.hasPermi('fil:transactionRecord:add')")
    @Log(title = "交易记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FilTransactionRecord filTransactionRecord)
    {
        return toAjax(filTransactionRecordService.insertFilTransactionRecord(filTransactionRecord));
    }

    /**
     * 修改交易记录
     */
    @PreAuthorize("@ss.hasPermi('fil:transactionRecord:edit')")
    @Log(title = "交易记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FilTransactionRecord filTransactionRecord)
    {
        return toAjax(filTransactionRecordService.updateFilTransactionRecord(filTransactionRecord));
    }

    /**
     * 删除交易记录
     */
    @PreAuthorize("@ss.hasPermi('fil:transactionRecord:remove')")
    @Log(title = "交易记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(filTransactionRecordService.deleteFilTransactionRecordByIds(ids));
    }
}
