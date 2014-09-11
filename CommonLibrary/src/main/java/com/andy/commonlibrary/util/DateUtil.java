package com.andy.commonlibrary.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Vinchaos api on 13-12-12.
 * 日期工具类
 * 对日期基本的格式化,基本运算处理
 */
public class DateUtil {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat();

    /**
     * formatSystemDate
     * 将系统时间转换成指定格式的字符串
     *
     * @param pattern 转化格式字符串
     * @return
     */
    public static String formatSystemDate(final String pattern) {
        String date = formatDate(null, pattern);
        return date;
    }

    /**
     * formatDate
     * 转换Date类型的时间为指定格式的字符串
     *
     * @param date    Date类型的时间，如果为 null，则转换系统时间
     * @param pattern 转换格式
     * @return
     */
    public static String formatDate(Date date, final String pattern) {
        dateFormat.applyPattern(pattern);
        String result;
        if (date == null) {
            result = dateFormat.format(new Date());
        } else {
            result = dateFormat.format(date);
        }
        return result;
    }

    /**
     * formatDateStr
     * 将时间字符串转换成指定格式的时间字符串
     *
     * @param date    需要转换的时间字符串"long"
     * @param pattern 转换的格式
     * @return
     */
    public static String formatDateStr(final String date, final String pattern) {
        long timeString = Long.parseLong(date);
        dateFormat.applyPattern(pattern);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeString);
        String result = dateFormat.format(c.getTime());
        return result;
    }

    /**
     * formatDateStrToPattern
     * 时间字符串从一种格式转换成另一种格式
     *
     * @param date          20130516
     * @param pattern       example :"yyyyMMdd"
     * @param targetPattern example:"yyyy-MM-dd "
     * @return
     */
    public static String formatDateStrToPattern(String date, String pattern, String targetPattern) throws ParseException {
        dateFormat.applyPattern(pattern);
        Date d = dateFormat.parse(date);
        date = formatDate(d, targetPattern);
        return date;
    }

    /**
     * getWeekOfDate
     * 获取当前星期几
     *
     * @return
     */
    public static String getWeekOfDate() {
        String weekDay = "";
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(System.currentTimeMillis()));
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case 1:
                weekDay = "星期日";
                break;
            case 2:
                weekDay = "星期一";
                break;
            case 3:
                weekDay = "星期二";
                break;
            case 4:
                weekDay = "星期三";
                break;
            case 5:
                weekDay = "星期四";
                break;
            case 6:
                weekDay = "星期五";
                break;
            case 7:
                weekDay = "星期六";
                break;

        }
        return weekDay;
    }


    /**
     * 检查是否早于某个日期
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     * @return
     */
    public static boolean checkDatePre(int year, int monthOfYear,int dayOfMonth,Date date) {
        int temp_year=0;
        int temp_month=0;
        int temp_day=0;
        temp_year=date.getYear();
        temp_month=date.getMonth();
        temp_day=date.getDay();
        if (year > temp_year) {//大于当前年份
            return true;
        }else if(year==temp_year && monthOfYear > temp_month){//大于当前月份
            return true;
        }else if (year==temp_year && monthOfYear == temp_month && dayOfMonth >temp_day) {//大于等于当前年份
            return true;
        }else {
            return false;
        }
    }

    /**
     * 检查是否晚于某个日期
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     * @return
     */
    public static boolean checkDateLater(int year, int monthOfYear,int dayOfMonth,Date date) {
        int temp_year=0;
        int temp_month=0;
        int temp_day=0;
        temp_year=date.getYear();
        temp_month=date.getMonth();
        temp_day=date.getDay();
        if (year < temp_year) {//小于当前年份
            return true;
        }else if(year==temp_year && monthOfYear < temp_month){//小于当前月份
            return true;
        }else if (year==temp_year && monthOfYear == temp_month && dayOfMonth <temp_day) {//小于等于当前年份
            return true;
        }else {
            return false;
        }
    }

}