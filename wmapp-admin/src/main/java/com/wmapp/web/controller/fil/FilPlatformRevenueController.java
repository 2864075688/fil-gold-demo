package com.wmapp.web.controller.fil;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wmapp.common.core.controller.BaseController;
import com.wmapp.common.core.domain.AjaxResult;
import com.wmapp.fil.domain.FilPlatformRevenue;
import com.wmapp.fil.service.IFilPlatformRevenueService;
import com.wmapp.common.core.page.TableDataInfo;

/**
 * 平台收入Controller
 * 
 * @author wmapp
 * @date 2021-11-05
 */
@RestController
@RequestMapping("/fil/platformRevenue")
public class FilPlatformRevenueController extends BaseController
{
    @Autowired
    private IFilPlatformRevenueService filPlatformRevenueService;

    /**
     * 查询平台收入列表
     */
    @PreAuthorize("@ss.hasPermi('fil:platformRevenue:list')")
    @GetMapping("/list")
    public TableDataInfo list(FilPlatformRevenue filPlatformRevenue)
    {
        startPage();
        filPlatformRevenue.setType(0);
        List<FilPlatformRevenue> list = filPlatformRevenueService.selectFilPlatformRevenueList(filPlatformRevenue);
        return getDataTable(list);
    }

    /**
     * 获取平台收入详细信息
     */
    @PreAuthorize("@ss.hasPermi('fil:platformRevenue:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(filPlatformRevenueService.selectFilPlatformRevenueById(id));
    }
}
