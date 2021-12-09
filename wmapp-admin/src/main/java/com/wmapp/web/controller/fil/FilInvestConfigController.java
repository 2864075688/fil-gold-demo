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
import com.wmapp.fil.domain.FilInvestConfig;
import com.wmapp.fil.service.IFilInvestConfigService;
import com.wmapp.common.utils.poi.ExcelUtil;
import com.wmapp.common.core.page.TableDataInfo;

/**
 * 复投配置Controller
 * 
 * @author wmapp
 * @date 2021-10-25
 */
@RestController
@RequestMapping("/fil/investConfig")
public class FilInvestConfigController extends BaseController
{
    @Autowired
    private IFilInvestConfigService filInvestConfigService;

    /**
     * 查询复投配置列表
     */
    @PreAuthorize("@ss.hasPermi('fil:investConfig:list')")
    @GetMapping("/list")
    public TableDataInfo list(FilInvestConfig filInvestConfig)
    {
        startPage();
        List<FilInvestConfig> list = filInvestConfigService.selectFilInvestConfigList(filInvestConfig);
        return getDataTable(list);
    }

    /**
     * 获取复投配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('fil:investConfig:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(filInvestConfigService.selectFilInvestConfigById(id));
    }

    /**
     * 新增复投配置
     */
    @PreAuthorize("@ss.hasPermi('fil:investConfig:add')")
    @Log(title = "复投配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FilInvestConfig filInvestConfig)
    {
        return toAjax(filInvestConfigService.insertFilInvestConfig(filInvestConfig));
    }

    @PreAuthorize("@ss.hasPermi('fil:investConfig:edit')")
    @PutMapping("check")
    public AjaxResult checkInvestNum(@RequestBody FilInvestConfig filInvestConfig){
        Boolean flag =  filInvestConfigService.checkInvestNum(filInvestConfig);
        return AjaxResult.success("操作成功",flag);
    }

    /**
     * 修改复投配置
     */
    @PreAuthorize("@ss.hasPermi('fil:investConfig:edit')")
    @Log(title = "复投配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FilInvestConfig filInvestConfig)
    {
        return toAjax(filInvestConfigService.updateFilInvestConfig(filInvestConfig));
    }

    /**
     * 删除复投配置
     */
    @PreAuthorize("@ss.hasPermi('fil:investConfig:remove')")
    @Log(title = "复投配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(filInvestConfigService.deleteFilInvestConfigByIds(ids));
    }
}
