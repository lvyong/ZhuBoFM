package com.andy.commonlibrary.util;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xyz on 13-12-9.
 * 应用程序级通用的处理,如 格式化等长,加前缀等.
 */
public class StringUtil {

    /**
     * isNotEmpty
     *
     * @param string
     * @return
     */
    public static boolean isNotEmpty(String string) {
        return string != null && !"".equals(string.trim()) && !"null".equals(string.trim());
    }

    /**
     * 判断字符串是否为空
     * @param string
     * @return 空返回true,非空返回false
     */
    public static boolean isEmpty(String string){
        return  !isNotEmpty(string);
    }

    /**
     * trim
     *
     * @param string
     * @return
     */
    public static String trim(String string) {
        if (string == null || string.equals("null"))
            return "";
        else
            return string.trim();
    }

    /**
     *
     * @param o
     * @return
     */
    public static String toString(Object o){
        return o == null ? "" : o.toString();
    }

    /**
     * formatString
     * 将string中的所有replace去掉
     *
     * @param string
     * @param replace
     * @return
     */
    public static String formatString(String string, String replace) {
        if (string == null) return "";
        String newString = string.replaceAll(replace, "");
        return newString;
    }

    /**
     * 字符串模糊处理，*号代替
     * @param string 原字符
     * @param start  模糊开始下标
     * @param end    模糊结束下标
     * @return 138****5678
     */
    public static String formatStringVague(String string,int start,int end){
        if(StringUtil.isEmpty(string)){
            return string;
        }

        if(start < 0 || end > (string.length() - 1)){
            return string;
        }

        StringBuilder sb = new StringBuilder();
        char[] c = string.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if(i >= start && i <= end){
                sb.append("*");
            }else{
                sb.append(c[i]);
            }
        }
        return sb.toString();
    }

    /**
     * formatString
     * 去除字符中间的 "空格/-/," 等间隔符
     *
     * @param string 要格式化的字符
     * @return 格式化后的字符
     */
    public static String formatString(String string) {
        if (string == null) return "";
        String newString = string.replaceAll(" ", "")
                .replaceAll("-", "")
                .replaceAll(",", "");
        return newString;
    }

    /**
     * 格式话文件内容，去空格换行符
     */
    public static String formatFileContent(String string){
        if (string == null) return "";
        String newString = string.replaceAll("\n", "")
                .replaceAll("\t", "")
                .replaceAll("\r", "");
        return newString;
    }

    /**
     * suffixSpaceToString
     * 字符串后端加全角空格，对齐成指定数量个汉字
     *
     * @param string
     * @param len
     * @return
     */
    public static String suffixSpaceToString(String string, int len) {
        StringBuilder stringBuilder = new StringBuilder();
        int length = string.length();
        int appendCount = length < len ? len - length : 0;

        for (int i = 0; i < appendCount; i++) {
            stringBuilder.append("　");
        }
        return string + stringBuilder.toString();
    }

    /**
     * addSpaceToStringFront
     * 字符串前端加全角空格，对齐成指定数量个汉字
     *
     * @param string
     * @param len    指定对齐位数
     * @return
     */
    public static String addSpaceToStringFront(String string, int len) {
        StringBuilder stringBuilder = new StringBuilder();
        int length = string.length();
        int appendCount = length < len ? len - length : 0;

        for (int i = 0; i < appendCount; i++) {
            stringBuilder.append("　");
        }
        return stringBuilder.toString() + string;
    }

    /**
     * formatAmount
     * 金额格式化
     *
     * @param s 金额
     * @return 格式后的金额(###,###.##)
     */
    public static String formatAmount(String s){
        return formatAmount(s, false);
    }

    /**
     * formatAmount
     * 金额格式化
     *
     * @param s          金额
     * @param isInitNull 是否初始化为空字符
     * @return 格式后的金额(###,###.##)
     */
    public static String formatAmount(String s, boolean isInitNull) {
        String result = "";
        if (!isNotEmpty(s)) return "";
        String temp = s;
        s = formatString(s);// 去除string可能的分隔符
        double num = 0.0;
        try {
            num = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            //TODO throw something..
        }
        if (num == 0) {
            if (isInitNull) {
                return "";
            } else {
                return "0.00";
            }
        }
        if (num < 1) {//小于1情况特殊处理
            if (s.length() == 4) {//0.05
                return temp;
            } else if (s.length() == 3) {//0.5
                return temp + "0";
            }
        }
        NumberFormat forMater = new DecimalFormat("#,###.00");
        result = forMater.format(num);

        if (result.startsWith(".")) {
            result = "0" + result;
        }
        return result;
    }

    /**
     * 判断某字符串是否是网址
     * @param urlString
     * @return
     */
    public  static boolean isURL(String urlString){
        String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]" ;
        Pattern patt = Pattern. compile(regex);
        Matcher matcher = patt.matcher(urlString);
        boolean isMatch = matcher.matches();
        return isMatch;
    }

    /**
     * 将一个字符串转换成整型值，如果 number 不能转换成整型则返回 defaultValue
     *
     * @param number  一个数字字符串
     * @return  转换后的整型值。
     */
    public static int StringToInt(String number,int defaultValue){
        if (StringUtil.isEmpty(number)){
            return defaultValue;
        }
        else {
            try{
                return Integer.valueOf(number);
            }catch (Exception e){
                return defaultValue;
            }
        }
    }

   /**
     * 将一个字符串转换成浮点型值，如果 number 不能转换成整型则返回 defaultValue
     *
     * @param number  一个数字字符串
     * @return  转换后的整型值。
     */
    public static Float StringTofloat(String number,float defaultValue){
        if (StringUtil.isEmpty(number)){
            return defaultValue;
        }
        else {
            try{
                return Float.valueOf(number);
            }catch (Exception e){
                return defaultValue;
            }
        }
    }

    /**
     * 将字符串中的所有的数字格式化成指定颜色。
     * @param text  字符串
     * @param color 颜色
     * @return 返回格式化后的字符串对象 SpannableStringBuilder。
     */
    public static SpannableStringBuilder formatNumberColor(String text,int color){
        return formatTextColor(text,"[0-9]+\\.*[0-9]*",color);
    }

    /**
     * 将字符串中与正则表达式匹配的文字设置成指定颜色。
     * @param text  字符串
     * @param patternString 用于筛选的正则表达式。
     * @param color 颜色
     * @return 返回格式化后的字符串对象 SpannableStringBuilder。
     */
    public static SpannableStringBuilder formatTextColor(String text,String patternString,int color){
        if (text ==  null)
            return new SpannableStringBuilder("");

        SpannableStringBuilder style=new SpannableStringBuilder(text);

        if (patternString == null)
            return style;

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);

        while(matcher.find()){
            style.setSpan(new ForegroundColorSpan(color),matcher.start(),matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return style;
    }


    /**
     * 。是否是纯数字字符串
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }


    /**
     * 判定输入汉字
     * @param c
     * @return
     */
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 检测String是否全是中文
     * @param name
     * @return
     */
    public static  boolean checkNameChese(String name)
    {
        boolean res=true;
        char [] cTemp = name.toCharArray();
        for(int i=0;i<name.length();i++)
        {
            if(!isChinese(cTemp[i]))
            {
                res=false;
                break;
            }
        }
        return res;
    }

    /**
     * 返回长度为【strLength】的随机数，在前面补0
     */
    public static String getRandom(int strLength) {

        Random rm = new Random();
        // 获得随机数
        double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);
        // 将获得的获得随机数转化为字符串
        String fixLenthString = String.valueOf(pross);
        // 返回固定的长度的随机数
        return fixLenthString.substring(1, strLength + 1);
    }
}
