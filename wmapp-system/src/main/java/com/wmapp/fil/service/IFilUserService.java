package com.wmapp.fil.service;

import java.util.List;
import com.wmapp.fil.domain.FilUser;
import com.wmapp.fil.vo.UserVo;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/**
 * 用户Service接口
 * 
 * @author wmapp
 * @date 2021-10-26
 */
public interface IFilUserService 
{
    /**
     * 查询用户列表
     * 
     * @param filUser 用户
     * @return 用户集合
     */
    public List<FilUser> selectFilUserList(FilUser filUser);

    /**
     * 保存用户
     * @param userCode
     * @param puser 父类实体类
     * @return
     */
    Long saveUserAndLevel(String  userCode,FilUser puser)throws Exception;

    /**
     * 根据用户userCode查询用户
     * @param userCode
     * @return
     */
    FilUser selectFilUserByCodeForUpdate(String userCode);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    int updateUser(FilUser user);

    /**
     * 根据usercode查询用户
     * @param userCode
     * @return
     */
    FilUser selectFilUserByCode(String userCode);

    /**
     * 更新授权
     * @param user
     * @param receipt
     */
    int updateAuthStatus(FilUser user, TransactionReceipt receipt);

    /**
     * 查询用户所有统计
     * @return
     */
    FilUser getUserTotalAmount();
}
