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
     * @param  loginName
     * @param  password
     * @param handleInterface
     */
    public void login(String loginName,String password ,BusinessResponseHandler handleInterface){
        BussinessParams bussinessParams = new BussinessParams(context);
        bussinessParams.setRelative_url("/api/user/login");

        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("t", "f05da37f8656c78db7efdb64a1166fb6273caf0d"));

        list.add(new BasicNameValuePair("type","ANCHOR"));
        list.add(new BasicNameValuePair("name",loginName));
        list.add(new BasicNameValuePair("password",password));

        bussinessParams.setParamList(list);
        httpAsyncTask = HttpManger.getInstance().postSend(context,bussinessParams,handleInterface);
    }

    /**
     * 修改密码
     * @param oldPass
     * @param newOld
     * @param handleInterface
     */
    public void changePassword(String oldPass,String newOld,
                               BusinessResponseHandler handleInterface){
        BussinessParams bussinessParams = new BussinessParams(context);
        bussinessParams.setRelative_url("/api/user/change_password");

        List<NameValuePair> list = new ArrayList<NameValuePair>();
      //  list.add(new BasicNameValuePair("t", "f05da37f8656c78db7efdb64a1166fb6273caf0d"));

        list.add(new BasicNameValuePair("oldPassword",oldPass));
        list.add(new BasicNameValuePair("password",newOld));

        bussinessParams.setParamList(list);
        httpAsyncTask = HttpManger.getInstance().postSend(context,bussinessParams,handleInterface);
    }


    /**
     * 修改用户签名
     * @param signature
     * @param handler
     */
    public void changeSignature(String signature,BusinessResponseHandler handler){
        BussinessParams bussinessParams = new BussinessParams(context);
        bussinessParams.setRelative_url("/api/user");

        List<NameValuePair> list = new ArrayList<NameValuePair>();
      //  list.add(new BasicNameValuePair("t", "f05da37f8656c78db7efdb64a1166fb6273caf0d"));
        list.add(new BasicNameValuePair("message",signature));

        bussinessParams.setParamList(list);
        httpAsyncTask = HttpManger.getInstance().putSend(context,bussinessParams,handler);
    }

    public void cancle(){
        if(null != httpAsyncTask){
            httpAsyncTask.cancel(true);
        }
    }

}
