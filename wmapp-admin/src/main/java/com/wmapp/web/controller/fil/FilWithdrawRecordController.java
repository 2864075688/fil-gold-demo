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
import com.wmapp.fil.domain.FilWithdrawRecord;
import com.wmapp.fil.service.IFilWithdrawRecordService;
import com.wmapp.common.utils.poi.ExcelUtil;
import com.wmapp.common.core.page.TableDataInfo;

/**
 * 提现记录Controller
 * 
 * @author wmapp
 * @date 2021-11-10
 */
@RestController
@RequestMapping("/fil/withdrawRecord")
public class FilWithdrawRecordController extends BaseController
{
    @Autowired
    private IFilWithdrawRecordService filWithdrawRecordService;

    /**
     * 查询提现记录列表
     */
    @PreAuthorize("@ss.hasPermi('fil:withdrawRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(FilWithdrawRecord filWithdrawRecord)
    {
        startPage();
        List<FilWithdrawRecord> list = filWithdrawRecordService.selectFilWithdrawRecordList(filWithdrawRecord);
        return getDataTable(list);
    }

    /**
     * 导出提现记录列表
     */
    @PreAuthorize("@ss.hasPermi('fil:withdrawRecord:export')")
    @Log(title = "提现记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FilWithdrawRecord filWithdrawRecord)
    {
        List<FilWithdrawRecord> list = filWithdrawRecordService.selectFilWithdrawRecordList(filWithdrawRecord);
        ExcelUtil<FilWithdrawRecord> util = new ExcelUtil<FilWithdrawRecord>(FilWithdrawRecord.class);
        return util.exportExcel(list, "提现记录数据");
    }

    /**
     * 获取提现记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('fil:withdrawRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(filWithdrawRecordService.selectFilWithdrawRecordById(id));
    }

    /**
     * 新增提现记录
     */
    @PreAuthorize("@ss.hasPermi('fil:withdrawRecord:add')")
    @Log(title = "提现记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FilWithdrawRecord filWithdrawRecord)
    {
        return toAjax(filWithdrawRecordService.insertFilWithdrawRecord(filWithdrawRecord));
    }

    /**
     * 修改提现记录
     */
    @PreAuthorize("@ss.hasPermi('fil:withdrawRecord:edit')")
    @Log(title = "提现记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FilWithdrawRecord filWithdrawRecord)
    {
        return toAjax(filWithdrawRecordService.updateFilWithdrawRecord(filWithdrawRecord));
    }

    /**
     * 删除提现记录
     */
    @PreAuthorize("@ss.hasPermi('fil:withdrawRecord:remove')")
    @Log(title = "提现记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(filWithdrawRecordService.deleteFilWithdrawRecordByIds(ids));
    }
}
