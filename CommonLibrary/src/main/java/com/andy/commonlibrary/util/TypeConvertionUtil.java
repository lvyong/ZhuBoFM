package com.andy.commonlibrary.util;

/**
 * Created by Vinchaos api on 13-12-12.
 * 基础类型转换工具类
 */
public class TypeConvertionUtil {
    /**
     * String2double
     *
     * @param string
     * @return -1为转换错误
     */
    public static double string2Double(String string) {
        if(!StringUtil.isNotEmpty(string))return -1;
        double temp;
        try {
            temp = Double.parseDouble(string);
        } catch (Exception e) {
            temp = -1;
        }
        return temp;
    }
    /**
     * String2double
     *
     * @param string
     * @param defaultValue
     * @return
     */
    public static double string2Double(String string,double defaultValue){
        if(!StringUtil.isNotEmpty(string))return defaultValue;
        double temp;
        try {
            temp = Double.parseDouble(string);
        } catch (Exception e) {
            temp = defaultValue;
        }
        return temp;
    }

    /**
     * String2int
     *
     * @param string
     * @return -1为转换错误
     */
    public static int string2Int(String string) {
        if(!StringUtil.isNotEmpty(string))return -1;
        int temp;
        try {
            temp = Integer.parseInt(string);
        } catch (Exception e) {
            temp = -1;
        }
        return temp;
    }

    /**
     * String2int
     *
     * @param string
     * @param defaultValue
     * @return
     */
    public static int string2Int(String string,int defaultValue) {
        if(!StringUtil.isNotEmpty(string))return defaultValue;
        int temp;
        try {
            temp = Integer.parseInt(string);
        } catch (Exception e) {
            temp = defaultValue;
        }
        return temp;
    }
}
