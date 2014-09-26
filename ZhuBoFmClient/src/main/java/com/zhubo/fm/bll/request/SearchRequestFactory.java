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
 * 搜索产品
 * Created by andy_lv on 2014/9/8.
 */
public class SearchRequestFactory {
    private Context context;

    private HttpManger.HttpAsyncTask httpAsyncTask;

    public SearchRequestFactory(Context context){
        this.context = context;
    }

    /**
     * 搜索产品
     * @param page
     * @param handleInterface
     */
    public void searchProduct(int page,
                              String type,
                              String categoryId,
                              String q,
                              String orderBy,
                              BusinessResponseHandler handleInterface){
        BussinessParams bussinessParams = new BussinessParams(context);
        bussinessParams.setRelative_url("/api/anchor_client/search_products");
        //增加基本参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
       // list.add(new BasicNameValuePair("t", "f05da37f8656c78db7efdb64a1166fb6273caf0d"));
        list.add(new BasicNameValuePair("page", page+""));
        list.add(new BasicNameValuePair("type", type));
        list.add(new BasicNameValuePair("categoryId", categoryId));
        list.add(new BasicNameValuePair("q", q));
        list.add(new BasicNameValuePair("orderBy", orderBy));

        bussinessParams.setParamList(list);
        httpAsyncTask = HttpManger.getInstance().getSend(context,bussinessParams,handleInterface);
    }

    /**
     * 得到类目名称
     * @param handleInterface
     */
    public void getCategorys( BusinessResponseHandler handleInterface){
        BussinessParams bussinessParams = new BussinessParams(context);
        bussinessParams.setRelative_url("/api/anchor_client/categories");
        //增加基本参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
       // list.add(new BasicNameValuePair("t", "f05da37f8656c78db7efdb64a1166fb6273caf0d"));

        bussinessParams.setParamList(list);
        httpAsyncTask = HttpManger.getInstance().getSend(context,bussinessParams,handleInterface);
    }

    /**
     * 选择或者取消传中产品
     * @param programId
     * @param productIds
     * @param cancel
     * @param handleInterface
     */
    public void chooseProduct(int programId,String productIds,boolean cancel,
                              BusinessResponseHandler handleInterface){
        BussinessParams bussinessParams = new BussinessParams(context);
        bussinessParams.setRelative_url("/api/anchor_client/contents/" +programId+
                "/choose_product");
        //增加基本参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
     //   list.add(new BasicNameValuePair("t", "f05da37f8656c78db7efdb64a1166fb6273caf0d"));
        list.add(new BasicNameValuePair("productIds",productIds));
        list.add(new BasicNameValuePair("cancel", cancel+""));
        bussinessParams.setParamList(list);
        httpAsyncTask = HttpManger.getInstance().postSend(context,bussinessParams,handleInterface);
    }

    /**
     * 获取某一个节目下的产品列表
     */
    public void getAllProductsOfProgram(String programId, BusinessResponseHandler handleInterface){
        BussinessParams bussinessParams = new BussinessParams(context);
        bussinessParams.setRelative_url("/api/contents/" +programId+
                "/products");
        //增加基本参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
     //   list.add(new BasicNameValuePair("t", "f05da37f8656c78db7efdb64a1166fb6273caf0d"));
        bussinessParams.setParamList(list);
        httpAsyncTask = HttpManger.getInstance().getSend(context,bussinessParams,handleInterface);
    }


    /**
     *
     */
    public void cancle(){
        if(null == httpAsyncTask){
            httpAsyncTask.cancel(true);
        }
    }
}
