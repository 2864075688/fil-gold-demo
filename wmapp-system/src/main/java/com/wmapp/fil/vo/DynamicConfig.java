package com.wmapp.fil.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author wmapp
 * @date 2021-11-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DynamicConfig {

    /**
     * 人数
     */
    private Integer peopleNum;
    /**
     * 几代
     */
    private Integer eraNum;
    /**
     * 百分比
     */
    private BigDecimal takeRate;

}
