package com.andy.commonlibrary.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by andy_lv on 2014/9/20.
 */
public class SharePreferUtil {

    private Context context;

    private static final String preferName = "prefer";

    private static SharedPreferences.Editor localEditor ;
    private static SharePreferUtil sharePreferUtil;
    private SharedPreferences sharedPreferences;

    private SharePreferUtil(Context context){
        sharedPreferences = context.getSharedPreferences(preferName, 0);
        localEditor = sharedPreferences.edit();
    }

    public static SharePreferUtil  getInstance(Context context){
        if(null == sharePreferUtil){
            sharePreferUtil = new SharePreferUtil(context);
        }

        return sharePreferUtil;
    }

    /**
     *  保存字符串
     * @param key
     * @param value
     */
    public void saveString(String key,String value){
        localEditor.putString(key,value);
        localEditor.commit();
    }

    public String getString(String key){
        return sharedPreferences.getString(key,"");
    }

    /**
     * 保存int值
     * @param key
     * @param value
     */
    public void saveInt(String key,int value){
        localEditor.putInt(key,value);
        localEditor.commit();
    }

    public int getInt(String key){
        return sharedPreferences.getInt(key,0);
    }

    /**
     * 保存boolean 值
     * @param key
     * @param value
     */
    public void saveBoolean(String key,boolean value){
        localEditor.putBoolean(key,value);
        localEditor.commit();
    }

    public boolean getBoolean(String key){
        return sharedPreferences.getBoolean(key,false);
    }

    /**
     * 保存float值
     * @param key
     * @param value
     */
    public void saveFloat(String key,float value){
        localEditor.putFloat(key,value);
        localEditor.commit();
    }

    public float getFloat(String key){
        return sharedPreferences.getFloat(key,0.0f);
    }
}
