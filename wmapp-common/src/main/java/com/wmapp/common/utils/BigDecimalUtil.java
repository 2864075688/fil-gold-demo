package com.wmapp.common.utils;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * BigDecimal工具类
 * @author wmapp
 * @date 2021-11-05
 */
public class BigDecimalUtil {
    /**
     * 为了保留精度保留18位小数点
     */
    private final static int SCALE_NUM = 18;

    /**
     * 加法
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal add(BigDecimal v1, BigDecimal v2){
        if(v1==null)v1=BigDecimal.ZERO;
        if(v2==null)v2=BigDecimal.ZERO;
        return v1.add(v2).setScale(SCALE_NUM,BigDecimal.ROUND_HALF_DOWN);
    }

    /**
     * 减法
     * @param v1 减数
     * @param v2 被减数
     * @return
     */
    public static BigDecimal sub(BigDecimal v1,BigDecimal v2){
        if(v1==null)v1=BigDecimal.ZERO;
        if(v2==null)v2=BigDecimal.ZERO;
        return v1.subtract(v2).setScale(SCALE_NUM,BigDecimal.ROUND_HALF_DOWN);
    }

    /**
     * 乘法
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal mul(BigDecimal v1,BigDecimal v2){
        if(v1==null || v2==null)return BigDecimal.ZERO;
        return v1.multiply(v2).setScale(SCALE_NUM,BigDecimal.ROUND_HALF_DOWN);
    }

    public static BigDecimal mul(BigDecimal v1,String v2){
        if(v1==null || !isNumber(v2))return BigDecimal.ZERO;
        BigDecimal b2 = new BigDecimal(v2);
        return mul(v1,b2);
    }

    /**
     * 判断是否是数字
     * @param str
     * @return
     */
    public static boolean isNumber(String str){
        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
        return pattern.matcher(str).matches();
    }

    /**
     * 除法
     * @param v1 除数
     * @param v2 被除数
     *           (10/2)=(v1/v2)
     * @return
     */
    public static BigDecimal div(BigDecimal v1,String v2){
        if(v1==null || !isNumber(v2))return BigDecimal.ZERO;
        BigDecimal b2 = new BigDecimal(v2);
        return div(v1,b2);//四舍五入,保留22位小数
    }

    public static BigDecimal div(BigDecimal v1,BigDecimal v2){
        if(v2.compareTo(BigDecimal.ZERO)==0)return BigDecimal.ZERO;
        return v1.divide(v2,SCALE_NUM,BigDecimal.ROUND_HALF_DOWN);//四舍五入,保留22位小数
    }

    /**
     * 乘以比例
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal mulRate(BigDecimal v1, BigDecimal v2) {
        BigDecimal v3 = div( v2,"100");
        return  mul(v1,v3);
    }

    public static BigDecimal mulRateStr(BigDecimal v1, String v2) {
        BigDecimal v3 = div( new BigDecimal(v2),"100");
        return  mul(v1,v3);
    }

    /**
     * 去零并且不显示科学计数法
     * @param value
     * @return
     */
    public static String toPlainTrailingZeros(BigDecimal value){
        if(value==null) return "0";
        String str = value.stripTrailingZeros().toPlainString();
        return str;
    }

    public static BigDecimal toPlainTrailingZerosToBigDecimal(BigDecimal value){
        String s = toPlainTrailingZeros(value);
        return new BigDecimal(s);
    }
}
