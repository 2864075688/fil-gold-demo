package com.wmapp.web.controller.fil;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wmapp.common.annotation.Log;
import com.wmapp.common.core.controller.BaseController;
import com.wmapp.common.core.domain.AjaxResult;
import com.wmapp.common.enums.BusinessType;
import com.wmapp.fil.domain.FilInvestLog;
import com.wmapp.fil.service.IFilInvestLogService;
import com.wmapp.common.utils.poi.ExcelUtil;
import com.wmapp.common.core.page.TableDataInfo;

/**
 * 投入记录Controller
 * 
 * @author wmapp
 * @date 2021-10-26
 */
@RestController
@RequestMapping("/fil/investLog")
public class FilInvestLogController extends BaseController
{
    @Autowired
    private IFilInvestLogService filInvestLogService;

    /**
     * 查询投入记录列表
     */
    @PreAuthorize("@ss.hasPermi('fil:investLog:list')")
    @GetMapping("/list")
    public TableDataInfo list(FilInvestLog filInvestLog)
    {
        startPage();
        List<FilInvestLog> list = filInvestLogService.selectFilInvestLogList(filInvestLog);
        return getDataTable(list);
    }

    /**
     * 导出投入记录列表
     */
    @PreAuthorize("@ss.hasPermi('fil:investLog:export')")
    @Log(title = "投入记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FilInvestLog filInvestLog)
    {
        List<FilInvestLog> list = filInvestLogService.selectFilInvestLogList(filInvestLog);
        ExcelUtil<FilInvestLog> util = new ExcelUtil<FilInvestLog>(FilInvestLog.class);
        return util.exportExcel(list, "投入记录数据");
    }

    /**
     * 获取投入记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('fil:investLog:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(filInvestLogService.selectFilInvestLogById(id));
    }

}
