package com.andy.commonlibrary.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Vinchaos api on 13-12-12.
 * Email工具类
 */
public class EmailUtil {
    /**E-mail格式化*/
    /***/

    /**
     * checkEmailAddress
     * E-mail验证
     *
     * @param emailString
     * @return
     */
    public static boolean checkEmailAddress(String emailString) {
        String regEx = "\\w+([-+._]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Matcher matcherObj = Pattern.compile(regEx).matcher(emailString);
        return matcherObj.matches();
    }
}
