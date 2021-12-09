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
import com.wmapp.fil.domain.FilDynamicConfig;
import com.wmapp.fil.service.IFilDynamicConfigService;
import com.wmapp.common.utils.poi.ExcelUtil;
import com.wmapp.common.core.page.TableDataInfo;

/**
 * 动态比例配置Controller
 * 
 * @author wmapp
 * @date 2021-11-09
 */
@RestController
@RequestMapping("/fil/dynamicConfig")
public class FilDynamicConfigController extends BaseController
{
    @Autowired
    private IFilDynamicConfigService filDynamicConfigService;

    /**
     * 查询动态比例配置列表
     */
    @PreAuthorize("@ss.hasPermi('fil:dynamicConfig:list')")
    @GetMapping("/list")
    public TableDataInfo list(FilDynamicConfig filDynamicConfig)
    {
        startPage();
        List<FilDynamicConfig> list = filDynamicConfigService.selectFilDynamicConfigList(filDynamicConfig);
        return getDataTable(list);
    }

    /**
     * 获取动态比例配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('fil:dynamicConfig:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(filDynamicConfigService.selectFilDynamicConfigById(id));
    }

    /**
     * 新增动态比例配置
     */
    @PreAuthorize("@ss.hasPermi('fil:dynamicConfig:add')")
    @Log(title = "动态比例配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FilDynamicConfig filDynamicConfig)
    {
        return toAjax(filDynamicConfigService.insertFilDynamicConfig(filDynamicConfig));
    }

    /**
     * 修改动态比例配置
     */
    @PreAuthorize("@ss.hasPermi('fil:dynamicConfig:edit')")
    @Log(title = "动态比例配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FilDynamicConfig filDynamicConfig)
    {
        return toAjax(filDynamicConfigService.updateFilDynamicConfig(filDynamicConfig));
    }

    /**
     * 删除动态比例配置
     */
    @PreAuthorize("@ss.hasPermi('fil:dynamicConfig:remove')")
    @Log(title = "动态比例配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(filDynamicConfigService.deleteFilDynamicConfigByIds(ids));
    }

}
