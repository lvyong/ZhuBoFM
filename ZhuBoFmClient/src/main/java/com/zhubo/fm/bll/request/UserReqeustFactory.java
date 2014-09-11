package com.zhubo.fm.bll.request;

import android.app.Activity;
import android.content.Context;

import com.andy.commonlibrary.util.AppUtil;
import com.andy.corelibray.CoreApplication;
import com.andy.corelibray.net.BusinessResponseHandler;
import com.andy.corelibray.net.BussinessParams;
import com.andy.corelibray.net.HttpManger;
import com.zhubo.control.bussiness.bean.UserBean;
import com.zhubo.control.bussiness.common.HttpBussinessHandle;
import com.zhubo.control.bussiness.bean.AuthToken;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息请求
 * Created by andy_lv on 2014/9/2.
 */
public class UserReqeustFactory {

    private Context context;

    private HttpManger.HttpAsyncTask httpAsyncTask;


    public UserReqeustFactory(Context context){
        this.context = context;
    }


    /**
     * 获取Token
     * @param handleInterface
     */
    public void getToken(BusinessResponseHandler handleInterface){
        BussinessParams bussinessParams = new BussinessParams(context);
        bussinessParams.setRelative_url("/api/user");

        CoreApplication application = (CoreApplication) ((Activity)context).getApplication();

        //增加基本参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("uuid", application.getUuid()));
        list.add(new BasicNameValuePair("imei", AppUtil.getInstance(context).getIMEI()));

        bussinessParams.setParamList(list);
        httpAsyncTask = HttpManger.getInstance().postSend(context,bussinessParams,handleInterface);
    }

    /**
     * 登录
     * @param handleInterface
     */
    public void login(BusinessResponseHandler handleInterface){
        BussinessParams bussinessParams = new BussinessParams(context);
        bussinessParams.setRelative_url("/api/user/login");

        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("t", "f05da37f8656c78db7efdb64a1166fb6273caf0d"));

        list.add(new BasicNameValuePair("type","ANCHOR"));
        list.add(new BasicNameValuePair("name","anchor"));
        list.add(new BasicNameValuePair("password","admin"));

        bussinessParams.setParamList(list);
        httpAsyncTask = HttpManger.getInstance().postSend(context,bussinessParams,handleInterface);
    }


    public void cancle(){
        if(null != httpAsyncTask){
            httpAsyncTask.cancel(true);
        }
    }

}
