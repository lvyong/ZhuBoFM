package com.zhubo.fm.bll.request;

import android.content.Context;
import android.util.Log;

import com.andy.corelibray.net.BusinessResponseHandler;
import com.andy.corelibray.net.BussinessParams;
import com.andy.corelibray.net.HttpManger;
import com.zhubo.fm.ZhuBoApplication;
import com.zhubo.fm.activity.setting.SetUserPhoto;
import com.zhubo.fm.bll.util.UploadPhoto;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by andy_lv on 2014/9/20.
 */
public class PrivateMessageRequest {

    private Context context;
    private HttpManger.HttpAsyncTask httpAsyncTask;
    /**
     * 构造器
     * @param context
     */
    public PrivateMessageRequest(Context context){
        this.context  = context;
    }

    /**
     * 得到私信列表
     * @param handler
     */
    public void getPrivateMessageList(BusinessResponseHandler handler){
        BussinessParams bussinessParams = new BussinessParams(context);
        bussinessParams.setRelative_url("/api/messages/group");
        //增加基本参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
       // list.add(new BasicNameValuePair("t", "f05da37f8656c78db7efdb64a1166fb6273caf0d"));

        bussinessParams.setParamList(list);
        httpAsyncTask = HttpManger.getInstance().getSend(context,bussinessParams,handler);
    }

    /**
     * 得到栏目列表
     * @param handler
     */
    public void getColumns(BusinessResponseHandler handler){
        BussinessParams bussinessParams = new BussinessParams(context);
        bussinessParams.setRelative_url("/api/anchor_client/columns");
        //增加基本参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
       // list.add(new BasicNameValuePair("t", "f05da37f8656c78db7efdb64a1166fb6273caf0d"));

        bussinessParams.setParamList(list);
        httpAsyncTask = HttpManger.getInstance().getSend(context,bussinessParams,handler);
    }

    /**
     * 得到节目评论列表
     * @param page
     * @param id
     * @param handler
     */
    public void getComlunCommentsList(int page,String id,BusinessResponseHandler handler){
        BussinessParams bussinessParams = new BussinessParams(context);
        bussinessParams.setRelative_url("/api/anchor_client/columns/"+id+
                "/comments");
        //增加基本参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
     //   list.add(new BasicNameValuePair("t", "f05da37f8656c78db7efdb64a1166fb6273caf0d"));
        list.add(new BasicNameValuePair("page",page+""));

        bussinessParams.setParamList(list);
        httpAsyncTask = HttpManger.getInstance().getSend(context,bussinessParams,handler);
    }

    /**
     * 删除私信
     * @param idStr
     * @return
     */
    public String deleteMessage(String idStr){
        String string = "";
        BussinessParams bussinessParams = new BussinessParams(context);
        //增加基本参数
        HashMap<String,String> map = new HashMap<String,String>();
       String apiToken = ZhuBoApplication.getInstance().getApiToken();
      //  map.put("t", "f05da37f8656c78db7efdb64a1166fb6273caf0d");
        map.put("t",apiToken);
        map.put("from",idStr);
        map.put("s","1");
        string = UploadPhoto.delete("http://api.mallfm.bjcathay.com/api/messages",map);
        Log.e("deleteMessage","-----删除返回的数据=="+string);
        return string;
    }


    /**
     * 私信详情
     * @param page
     * @param from
     * @param handler
     */
    public  void getMessageDetail(int page,String from,BusinessResponseHandler handler){
        BussinessParams bussinessParams = new BussinessParams(context);
        bussinessParams.setRelative_url("/api/messages");
        //增加基本参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
       // list.add(new BasicNameValuePair("t", "f05da37f8656c78db7efdb64a1166fb6273caf0d"));
        list.add(new BasicNameValuePair("page",page+""));
        list.add(new BasicNameValuePair("from",from));

        bussinessParams.setParamList(list);
        httpAsyncTask = HttpManger.getInstance().getSend(context,bussinessParams,handler);
    }

    /**
     * 发布私信
     * @param to
     * @param content
     * @param handler
     */
    public void sendMessage(String to,String content,BusinessResponseHandler handler){
        BussinessParams bussinessParams = new BussinessParams(context);
        bussinessParams.setRelative_url("/api/messages");
        //增加基本参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
       // list.add(new BasicNameValuePair("t", "f05da37f8656c78db7efdb64a1166fb6273caf0d"));
        list.add(new BasicNameValuePair("to",to));
        list.add(new BasicNameValuePair("content",content));

        bussinessParams.setParamList(list);
        httpAsyncTask = HttpManger.getInstance().postSend(context,bussinessParams,handler);
    }

    public void cancel(){
        if(null != httpAsyncTask){
            httpAsyncTask.cancel(true);
        }
    }
}
