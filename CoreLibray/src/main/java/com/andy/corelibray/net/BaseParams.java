package com.andy.corelibray.net;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.andy.commonlibrary.util.AppUtil;
import com.andy.corelibray.CoreApplication;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 设置http请求基本参数
 *
 * Created by andy_lv on 2014/8/23.
 */
public  class BaseParams {
    private final String TAG = "BaseParams";

    //http请求基地址
    private final static String BASE_URL = "http://api.mallfm.bjcathay.com";

    //参数List
    protected List<NameValuePair> paramList;
    //文件参数
    protected List<NameValuePair> fileParamList;

    /**
     * 构造器
     */
    public BaseParams(Context context){
           this.paramList = new  ArrayList<NameValuePair>();
           this.fileParamList = new ArrayList<NameValuePair>();

           CoreApplication application = (CoreApplication) ((Activity)context).getApplication();
           String apiToken = application.getApiToken();
           if(TextUtils.isEmpty(apiToken)){
               apiToken = MD5.generateRegisterToken(application.getUuid());
           }
           this.paramList.add(new BasicNameValuePair("s",
                "1"));
           Log.e(TAG,"------------t==="+apiToken);
           this.paramList.add(new BasicNameValuePair("t", apiToken));

    }

    /**
     * 设置参数列表
     * @param list  参数列表
     */
    protected void setParamList(List<NameValuePair> list){
        if(null != list){
            paramList = removeNameValuePar("t",list,paramList);
            paramList = removeNameValuePar("s",list,paramList);
        }
         this.paramList.addAll(list);
    }

    /**
     * 得到请求参数列表
     * @return
     */
    protected List<NameValuePair> getParamList(){
        return this.paramList;
    }

    /**
     * 得到请求URL
     * @return
     */
    protected  String getUrl(){
        return this.BASE_URL;
    }

    /**
     * 设置文件参数
     * @param list
     */
    protected  void setFileParamList(List<NameValuePair> list){
        if(null != list){
            fileParamList = removeNameValuePar("t",list,fileParamList);
            fileParamList = removeNameValuePar("s",list,fileParamList);
        }
        this.fileParamList.addAll(list);
    }

    /**
     * 清楚相同的key
     * @param key
     * @param list
     * @return
     */
    private List<NameValuePair> removeNameValuePar(String key,List<NameValuePair> list,List<NameValuePair> removedList){
        List<NameValuePair> result = null;
        Iterator<NameValuePair> iterator = list.iterator();
        while(iterator.hasNext()){
            NameValuePair nameValuePair = iterator.next();
            if(nameValuePair.getName().equals(key)){
                Iterator<NameValuePair> iterator1 = removedList.iterator();
                while(iterator1.hasNext()){
                    NameValuePair nameValuePair1 = iterator1.next();
                    if(nameValuePair1.getName().equals(key)){
                        iterator1.remove();
                        break;
                    }
                }
                break;
            }
        }
        return  result = removedList;
    }

    /**
     * 得到文件参数
     * @return
     */
    protected  List<NameValuePair> getFileParamList(){
        return this.fileParamList;
    }
}
