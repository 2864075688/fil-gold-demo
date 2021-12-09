package com.wmapp.fil.mapper;

import java.math.BigDecimal;
import java.util.List;
import com.wmapp.fil.domain.FilUser;
import com.wmapp.fil.vo.ReferrerNumVo;
import com.wmapp.fil.vo.UserVo;
import org.apache.ibatis.annotations.Param;

/**
 * 用户Mapper接口
 * 
 * @author wmapp
 * @date 2021-10-26
 */
public interface FilUserMapper 
{
    /**
     * 查询用户
     * 
     * @param id 用户ID
     * @return 用户
     */
    public FilUser selectFilUserById(Long id);

    /**
     * 查询用户列表
     * 
     * @param filUser 用户
     * @return 用户集合
     */
    public List<FilUser> selectFilUserList(FilUser filUser);

    /**
     * 新增用户
     * 
     * @param filUser 用户
     * @return 结果
     */
    public int insertFilUser(FilUser filUser);

    /**
     * 修改用户
     * 
     * @param filUser 用户
     * @return 结果
     */
    public int updateFilUser(FilUser filUser);

    /**
     * 根据code查询数据
     * @param userCode
     * @return
     */
    FilUser selectFilUserByCode(String userCode);

    /**
     * 根据code查询数据 加锁
     * @param userCode
     * @return
     */
    FilUser selectFilUserByCodeForUpdate(String userCode);

    /**
     * 获取大小节点的用户
     * @return
     */
    List<FilUser> getFileUserToNode();

    /**
     * 根据userId获取当前推荐用户
     * @param ids
     * @return
     */
    List<ReferrerNumVo> getReferrerNumByIds(@Param("list") List<Long> ids);

    /**
     * 获取上级多少层的用户
     * @param userId 用户id
     * @param levels 拿几代
     * @return
     */
    public List<FilUser> getHigherLevelUserListForUpdate(@Param("userId") Long userId, @Param("levels")Integer levels);

    /**
     * 获取下级多少层用户
     * @param userId
     * @param levels
     * @return
     */
    public List<FilUser> getLowerLevelUserListForUpdate(@Param("userId")Long userId, @Param("levels")Integer levels);

    /**
     * 获取总投资量
     * @param idList
     * @return
     */
    BigDecimal getTotalInvest(List<Long> idList);

    /**
     * 批量更新用户
     * @param list
     * @return
     */
    int updateBatchFilUserRevenue(@Param("list")List<FilUser> list);

    /**
     * 查询同层用户
     * @param pid
     * @return
     */
    List<FilUser> getSameLevelListForUpdate(@Param("userId")Long userId,@Param("pid") Long pid);

    /**
     * 查询用户总收益
     * @return
     */
    FilUser getUserTotalAmount();
}
