package com.andy.corelibray.net;


import android.content.Context;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * 业务请求参数
 * Created by andy_lv on 2014/8/23.
 */
public class BussinessParams extends BaseParams{

    /**
     * 设置具体业务请求url
     */
    private String relative_url = "";

    public BussinessParams(Context context){
       super(context);
    }

    public void setParamList(List<NameValuePair> list) {
        super.setParamList(list);
    }

    public void setFileParamList(List<NameValuePair> fileParamList1){
        super.setFileParamList(fileParamList);
    }

    @Override
    protected String getUrl() {
        return super.getUrl() + relative_url;
    }

    /**
     * 设置请求具体的业务地址
     * @param relative_url
     */
    public void setRelative_url(String relative_url){
        this.relative_url = relative_url;
    }
}
