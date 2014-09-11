package com.zhubo.fm.bll.request;

import android.app.Activity;
import android.content.Context;

import com.andy.commonlibrary.util.AppUtil;
import com.andy.corelibray.CoreApplication;
import com.andy.corelibray.net.BusinessResponseHandler;
import com.andy.corelibray.net.BussinessParams;
import com.andy.corelibray.net.HttpManger;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * 节目管理模块接口请求
 * Created by andy_lv on 2014/9/6.
 */
public class ProgramManagerRequestFactory {

    private Context context;

    private HttpManger.HttpAsyncTask httpAsyncTask;

    public ProgramManagerRequestFactory(Context context){
        this.context = context;
    }

    /**
     * 请求主播的节目
     * @param page
     * @param handleInterface
     */
    public void getMainFmProgram(int page,BusinessResponseHandler handleInterface){
        BussinessParams bussinessParams = new BussinessParams(context);
        bussinessParams.setRelative_url("/api/anchor_client/contents");
        //增加基本参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("t", "f05da37f8656c78db7efdb64a1166fb6273caf0d"));
        list.add(new BasicNameValuePair("page", page+""));

        bussinessParams.setParamList(list);
        httpAsyncTask = HttpManger.getInstance().getSend(context,bussinessParams,handleInterface);
    }

    public void cancle(){
        if(null == httpAsyncTask){
            httpAsyncTask.cancel(true);
        }
    }

}
