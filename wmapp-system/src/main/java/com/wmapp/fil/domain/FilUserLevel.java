package com.wmapp.fil.domain;

import com.wmapp.common.annotation.Excel;
import com.wmapp.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户层级对象 fil_user_level
 * 
 * @author wmapp
 * @date 2021-10-26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilUserLevel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户 ID */
    private Long userId;

    /** 上级用户ID */
    private Long superId;

    /** 层级 */
    private Integer level;

}
