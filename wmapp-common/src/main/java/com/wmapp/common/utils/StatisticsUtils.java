package com.wmapp.common.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class StatisticsUtils {


    public static String[] getLast30Day(Date createTime){
        String[] last12Day = new String[30];
        LocalDateTime now = null ;
        if(createTime == null ){
            now = LocalDateTime.now();
        }else{
            now = LocalDateTime.ofInstant(createTime.toInstant(), ZoneId.systemDefault());
        }
        last12Day[29] =  StringUtils.leftPad(String.valueOf(now.getMonthValue()), 2, '0') +"-"+StringUtils.leftPad(String.valueOf(now.getDayOfMonth()), 2, '0');
        for(int i=0; i<29; i++){
            now = now.minus(1, ChronoUnit.DAYS);
            String monthValue = StringUtils.leftPad(String.valueOf(now.getMonthValue()), 2, '0');
            String dayOfMonth = StringUtils.leftPad(String.valueOf(now.getDayOfMonth()), 2, '0');
            last12Day[28-i] = monthValue +"-"+ dayOfMonth ;
        }
        return last12Day;
    }


    public static String[] getLast12Months(Date createTime){
        String[] last12Months = new String[12];
        LocalDateTime now = null ;
        if(createTime == null ){
            now = LocalDateTime.now();
        }else{
            now = LocalDateTime.ofInstant(createTime.toInstant(), ZoneId.systemDefault());
        }
        last12Months[11] =  now.getYear() +"-"+StringUtils.leftPad(String.valueOf(now.getMonthValue()), 2, '0');
        for(int i=0; i<11; i++){
            now = now.minus(1, ChronoUnit.MONTHS);
            String monthValue = StringUtils.leftPad(String.valueOf(now.getMonthValue()), 2, '0');
            last12Months[10-i] = now.getYear() +"-"+ monthValue ;
        }
        return last12Months;
    }

}
