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
import com.wmapp.fil.domain.FilNode;
import com.wmapp.fil.service.IFilNodeService;
import com.wmapp.common.utils.poi.ExcelUtil;
import com.wmapp.common.core.page.TableDataInfo;

/**
 * 节点Controller
 * 
 * @author wmapp
 * @date 2021-10-25
 */
@RestController
@RequestMapping("/fil/node")
public class FilNodeController extends BaseController
{
    @Autowired
    private IFilNodeService filNodeService;

    /**
     * 查询节点列表
     */
    @PreAuthorize("@ss.hasPermi('fil:node:list')")
    @GetMapping("/list")
    public TableDataInfo list(FilNode filNode)
    {
        startPage();
        List<FilNode> list = filNodeService.selectFilNodeList(filNode);
        return getDataTable(list);
    }

    /**
     * 导出节点列表
     */
    @PreAuthorize("@ss.hasPermi('fil:node:export')")
    @Log(title = "节点", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FilNode filNode)
    {
        List<FilNode> list = filNodeService.selectFilNodeList(filNode);
        ExcelUtil<FilNode> util = new ExcelUtil<FilNode>(FilNode.class);
        return util.exportExcel(list, "节点数据");
    }

    /**
     * 获取节点详细信息
     */
    @PreAuthorize("@ss.hasPermi('fil:node:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(filNodeService.selectFilNodeById(id));
    }

    /**
     * 新增节点
     */
    @PreAuthorize("@ss.hasPermi('fil:node:add')")
    @Log(title = "节点", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FilNode filNode)
    {
        return toAjax(filNodeService.insertFilNode(filNode));
    }

    /**
     * 修改节点
     */
    @PreAuthorize("@ss.hasPermi('fil:node:edit')")
    @Log(title = "节点", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FilNode filNode)
    {
        return toAjax(filNodeService.updateFilNode(filNode));
    }

    /**
     * 删除节点
     */
    @PreAuthorize("@ss.hasPermi('fil:node:remove')")
    @Log(title = "节点", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(filNodeService.deleteFilNodeByIds(ids));
    }
}
