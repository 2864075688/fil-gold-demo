package com.wmapp.web.controller.fil;

import java.math.BigDecimal;
import java.util.List;

import com.wmapp.common.utils.BigDecimalUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wmapp.common.core.controller.BaseController;
import com.wmapp.fil.domain.FilUser;
import com.wmapp.fil.service.IFilUserService;
import com.wmapp.common.core.page.TableDataInfo;

/**
 * 用户Controller
 * 
 * @author wmapp
 * @date 2021-10-26
 */
@RestController
@RequestMapping("/fil/filUser")
public class FilUserController extends BaseController
{
    @Autowired
    private IFilUserService filUserService;

    /**
     * 查询用户列表
     */
    @PreAuthorize("@ss.hasPermi('fil:filUser:list')")
    @GetMapping("/list")
    public TableDataInfo list(FilUser filUser)
    {
        startPage();
        List<FilUser> list = filUserService.selectFilUserList(filUser);
        for(FilUser user:list){
            BigDecimal totalRevenue = user.getTotalRevenue();//总收益
            BigDecimal totalPastMoney = user.getPastMoney();//已提现金额
            //包含提现和复投金额
            BigDecimal costAmount = BigDecimalUtil.add(totalPastMoney,user.getRepeatInvest());
            //用户总可提现金额
            BigDecimal totalWithdrawMoney = BigDecimalUtil.toPlainTrailingZerosToBigDecimal
                    (BigDecimalUtil.sub(totalRevenue,costAmount));
            user.setWithdrawMoney(totalWithdrawMoney);
            user.setStaticRevenue(BigDecimalUtil.toPlainTrailingZerosToBigDecimal(user.getStaticRevenue()));
            user.setDynamicRevenue(BigDecimalUtil.toPlainTrailingZerosToBigDecimal(user.getDynamicRevenue()));
            user.setTotalInvest(BigDecimalUtil.toPlainTrailingZerosToBigDecimal(user.getTotalInvest()));
            user.setTotalRevenue(BigDecimalUtil.toPlainTrailingZerosToBigDecimal(totalRevenue));
            user.setPastMoney(BigDecimalUtil.toPlainTrailingZerosToBigDecimal(totalPastMoney));
            user.setNodeRevenue(BigDecimalUtil.toPlainTrailingZerosToBigDecimal(user.getNodeRevenue()));
        }
        return getDataTable(list);
    }

}
