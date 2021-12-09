package com.wmapp.fil.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.wmapp.common.utils.DateUtils;
import com.wmapp.common.utils.StringUtils;
import com.wmapp.fil.domain.FilTransactionRecord;
import com.wmapp.fil.domain.FilUserLevel;
import com.wmapp.fil.mapper.FilTransactionRecordMapper;
import com.wmapp.fil.mapper.FilUserLevelMapper;
import com.wmapp.fil.service.HuoBiService;
import com.wmapp.fil.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wmapp.fil.mapper.FilUserMapper;
import com.wmapp.fil.domain.FilUser;
import com.wmapp.fil.service.IFilUserService;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import javax.annotation.Resource;

/**
 * 用户Service业务层处理
 * 
 * @author wmapp
 * @date 2021-10-26
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class FilUserServiceImpl implements IFilUserService 
{
    private static Logger logger = LoggerFactory.getLogger(FilUserServiceImpl.class);

    @Autowired
    private FilUserMapper filUserMapper;

    @Autowired
    private FilUserLevelMapper filUserLevelMapper;

    @Autowired
    private HuoBiService huoBiService;

    @Resource
    private FilTransactionRecordMapper filTransactionRecordMapper;

    /**
     * 查询用户
     * 
     * @param userCode 用户code
     * @return 用户
     */
    @Override
    public FilUser selectFilUserByCodeForUpdate(String userCode)
    {
        return filUserMapper.selectFilUserByCodeForUpdate(userCode);
    }

    @Override
    public int updateUser(FilUser user) {
        return filUserMapper.updateFilUser(user);
    }

    @Override
    public FilUser selectFilUserByCode(String userCode) {
        return filUserMapper.selectFilUserByCode(userCode);
    }

    @Override
    public int updateAuthStatus(FilUser user, TransactionReceipt receipt) {
        BigDecimal price = BigDecimal.ZERO;
        user.setAuthStatus(1);
        filUserMapper.updateFilUser(user);
        //保存交易记录表
        filTransactionRecordMapper.insertFilTransactionRecord(new FilTransactionRecord(user.getId()+"",
                user.getUserCode(), receipt.getFrom(), receipt.getTo(),
                receipt.getTransactionHash(),price, 0, 5, 2));
        return filUserMapper.updateFilUser(user);
    }

    @Override
    public FilUser getUserTotalAmount() {
        return filUserMapper.getUserTotalAmount();
    }

    /**
     * 查询用户列表
     * 
     * @param filUser 用户
     * @return 用户
     */
    @Override
    public List<FilUser> selectFilUserList(FilUser filUser)
    {
        return filUserMapper.selectFilUserList(filUser);
    }

    /**
     * 保存深度表
     * @param userCode 用户code
     * @param puser 邀请的父类
     * @return
     */
    @Override
    public Long saveUserAndLevel(String userCode,FilUser puser)throws Exception {
            FilUser user = new FilUser();
            user.setUserCode(userCode);
            Long userId = 0L;
            if(puser!=null){//有父code需要保存深度表
                userId = saveUserAndLevel(user,puser);
            }else{//如果没有父类的话直接插入数据库
                user.setPid(0l);
                insertFilUser(user);
                userId = user.getId();
            }
        return userId;
    }

    /**
     * 保存用户和深度表
     * @param puser
     * @param user
     */
    private Long saveUserAndLevel(FilUser user,FilUser puser) throws Exception{
            Long userId = 0l;
            String rootIds = "";
            user.setParentCode(puser.getUserCode());
            user.setPid(puser.getId());
            rootIds = puser.getRootIds();
            if(StringUtils.isNotEmpty(rootIds)){//如果父类有层级id,保存深度表
                rootIds = puser.getRootIds()+","+puser.getId();
                user.setRootIds(rootIds);
                insertFilUser(user);
                String[] ids= rootIds.split(",");
                List<FilUserLevel> levelList = new ArrayList<>();
                int level = ids.length;
                for(int i=0;i<ids.length;i++){
                    Long superId = Long.parseLong(ids[i]);
                    levelList.add(new FilUserLevel(user.getId(),superId,level));
                    level--;
                }
                filUserLevelMapper.insertBatchUserLevel(levelList);
                userId = user.getId();
            }else{//只有父类一级得情况
                user.setRootIds(puser.getId().toString());
                insertFilUser(user);
                FilUserLevel level = new FilUserLevel(user.getId(),puser.getId(),1);
                filUserLevelMapper.insertFilUserLevel(level);
                userId = user.getId();
            }
        return userId;
    }


    /**
     * 新增用户
     * 
     * @param filUser 用户
     * @return 结果
     */
    public int insertFilUser(FilUser filUser)throws Exception
    {
        filUser.setCreateTime(DateUtils.getNowDate());
        return filUserMapper.insertFilUser(filUser);
    }

    /**
     * 修改用户
     * 
     * @param filUser 用户
     * @return 结果
     */
    public int updateFilUser(FilUser filUser)throws Exception
    {
        filUser.setUpdateTime(DateUtils.getNowDate());
        return filUserMapper.updateFilUser(filUser);
    }

}
