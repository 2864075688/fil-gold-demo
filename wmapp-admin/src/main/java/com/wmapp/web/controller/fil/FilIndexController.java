package com.wmapp.web.controller.fil;

import com.wmapp.common.core.domain.AjaxResult;
import com.wmapp.common.utils.BigDecimalUtil;
import com.wmapp.fil.domain.FilUser;
import com.wmapp.fil.service.HuoBiService;
import com.wmapp.fil.service.IFilPlatformRevenueService;
import com.wmapp.fil.service.IFilUserService;
import com.wmapp.fil.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wmapp
 */
@RestController
@RequestMapping("/fil/index")
public class FilIndexController {
    @Autowired
    private IFilPlatformRevenueService filPlatformRevenueService;

    @Autowired
    private IFilUserService filUserService;

    @Autowired
    private HuoBiService huoBiService;

    @GetMapping
    public AjaxResult getIndexData(){
        Map<String, Object> map = new HashMap<>();
        List<FilUser> list = filUserService.selectFilUserList(new FilUser());
        BigDecimal totalPlatAmount = filPlatformRevenueService.getTotalPlatFormAmount();
        FilUser vo = filUserService.getUserTotalAmount();
        BigDecimal gas = BigDecimal.ZERO;;
        BigDecimal balance = BigDecimal.ZERO;
        try {
            gas = huoBiService.getBalance();
            balance = huoBiService.getERC20Balance();
        }catch (Exception e){
            e.printStackTrace();
        }
        map.put("userNum",list.size());
        map.put("totalPlatAmount", BigDecimalUtil.toPlainTrailingZeros(totalPlatAmount));
        map.put("totalInvest",BigDecimalUtil.toPlainTrailingZeros(vo.getTotalInvest()));
        map.put("pastMoney",BigDecimalUtil.toPlainTrailingZeros(vo.getPastMoney()));
        map.put("gas",BigDecimalUtil.toPlainTrailingZeros(gas));
        map.put("balance",BigDecimalUtil.toPlainTrailingZeros(balance));
        return AjaxResult.success("获取成功",map);
    }
}
