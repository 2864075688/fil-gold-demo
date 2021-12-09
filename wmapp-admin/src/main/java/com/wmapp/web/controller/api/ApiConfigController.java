package com.wmapp.web.controller.api;

import com.wmapp.common.core.domain.AjaxResult;
import com.wmapp.fil.domain.FilNode;
import com.wmapp.fil.service.IFilNodeService;
import com.wmapp.system.domain.SysNotice;
import com.wmapp.system.service.ISysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wmapp
 * @module fil-user
 * @date 2021-10-25
 */
@RestController
@RequestMapping("/api/config")
public class ApiConfigController {

    @Autowired
    private IFilNodeService nodeService;

    @Autowired
    private ISysNoticeService sysNoticeService;

    /**
     * 获取节点list
     * @return
     */
    @GetMapping("node")
    public AjaxResult getNode(){
        FilNode node = new FilNode();
        node.setStatus(0);
        List<FilNode> nodeList = nodeService.selectFilNodeList(node);
        return AjaxResult.success("获取成功",nodeList);
    }

    /**
     * 获取公告
     * @return
     */
    @GetMapping("notice")
    public AjaxResult getNotice(){
        SysNotice notice = sysNoticeService.selectNoticeById(3l);
        return AjaxResult.success("获取成功",notice);
    }
}
