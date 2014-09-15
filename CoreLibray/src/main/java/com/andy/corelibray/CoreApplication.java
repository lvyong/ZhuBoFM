package com.andy.corelibray;

import android.app.Application;

import com.andy.commonlibrary.DBHelper;

/**
 * 主要在Applicaiton 中处理Core层逻辑，
 * 包括数据库的加载。
 * Created by andy_lv on 2014/8/23.
 */
public class CoreApplication extends Application{

    //ApiToken
    private String apiToken = "" ;
    //t
    private String t = "" ;

    private String uuid = "111111111111";

    private static CoreApplication instance;

    public  static CoreApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
