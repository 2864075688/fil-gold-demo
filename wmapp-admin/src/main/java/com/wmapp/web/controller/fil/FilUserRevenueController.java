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
import com.wmapp.fil.domain.FilUserRevenue;
import com.wmapp.fil.service.IFilUserRevenueService;
import com.wmapp.common.core.page.TableDataInfo;

/**
 * 用户收益Controller
 * 
 * @author wmapp
 * @date 2021-11-05
 */
@RestController
@RequestMapping("/fil/userRevenue")
public class FilUserRevenueController extends BaseController
{
    @Autowired
    private IFilUserRevenueService filUserRevenueService;

    /**
     * 查询用户收益列表
     */
    @PreAuthorize("@ss.hasPermi('fil:userRevenue:list')")
    @GetMapping("/list")
    public TableDataInfo list(FilUserRevenue filUserRevenue)
    {
        startPage();
        List<FilUserRevenue> list = filUserRevenueService.selectFilUserRevenueList(filUserRevenue);
        return getDataTable(list);
    }

    /**
     * 获取用户收益详细信息
     */
    @PreAuthorize("@ss.hasPermi('fil:userRevenue:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(filUserRevenueService.selectFilUserRevenueById(id));
    }

}
