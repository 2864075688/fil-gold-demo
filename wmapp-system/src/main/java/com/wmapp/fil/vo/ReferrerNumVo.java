package com.wmapp.fil.vo;

import lombok.Data;


/**
 * @author wmapp
 * @date 2021-11-09
 */
@Data
public class ReferrerNumVo {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 直推的人数
     */
    private Integer referrerNum;

}
