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
import com.wmapp.fil.domain.FilNodeApply;
import com.wmapp.fil.service.IFilNodeApplyService;
import com.wmapp.common.utils.poi.ExcelUtil;
import com.wmapp.common.core.page.TableDataInfo;

/**
 * 节点申购订单Controller
 * 
 * @author wmapp
 * @date 2021-11-10
 */
@RestController
@RequestMapping("/fil/applyNode")
public class FilNodeApplyController extends BaseController
{
    @Autowired
    private IFilNodeApplyService filNodeApplyService;

    /**
     * 查询节点申购订单列表
     */
    @PreAuthorize("@ss.hasPermi('fil:applyNode:list')")
    @GetMapping("/list")
    public TableDataInfo list(FilNodeApply filNodeApply)
    {
        startPage();
        List<FilNodeApply> list = filNodeApplyService.selectFilNodeApplyList(filNodeApply);
        return getDataTable(list);
    }

    /**
     * 获取节点申购订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('fil:applyNode:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(filNodeApplyService.selectFilNodeApplyById(id));
    }

}
