package com.zhubo.fm.bll.request;

import android.content.Context;

import com.andy.corelibray.net.BusinessResponseHandler;
import com.andy.corelibray.net.BussinessParams;
import com.andy.corelibray.net.HttpManger;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * 文稿请求
 * Created by andy_lv on 2014/9/10.
 */
public class NoteRequestFactory{

    private Context context;
    private HttpManger.HttpAsyncTask httpAsyncTask;
    /**
     * 构造器
     * @param context
     */
    public NoteRequestFactory(Context context){
        this.context  = context;
    }

    /**
     * 保存文稿
     * @param programId
     * @param text
     * @param handler
     */
    public void saveNote(String programId,String text,BusinessResponseHandler handler){
        BussinessParams bussinessParams = new BussinessParams(context);
        bussinessParams.setRelative_url("/api/anchor_client/contents/" +programId);
        //增加基本参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("t", "f05da37f8656c78db7efdb64a1166fb6273caf0d"));
        list.add(new BasicNameValuePair("presentation", text));

        bussinessParams.setParamList(list);
        httpAsyncTask = HttpManger.getInstance().putSend(context,bussinessParams,handler);
    }


    public void cancel(){
        if(null != httpAsyncTask){
            httpAsyncTask.cancel(true);
        }
    }

}
