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
 * Created by andy_lv on 2014/9/11.
 */
public class ProductSaleFactory {

    private Context context;
    private HttpManger.HttpAsyncTask httpAsyncTask;

    public ProductSaleFactory(Context context){
        this.context = context;
    }

    /**
     * 产品销量查询
     * @param startDate
     * @param endDate
     * @param page
     * @param handler
     */
    public void getProductSale(String startDate,
                          String endDate,
                          int page,
                          BusinessResponseHandler handler){
        BussinessParams bussinessParams = new BussinessParams(context);
        bussinessParams.setRelative_url("/api/anchor_client/sales");
        //增加基本参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("t", "f05da37f8656c78db7efdb64a1166fb6273caf0d"));
      /*  list.add(new BasicNameValuePair("start",startDate));
        list.add(new BasicNameValuePair("end",endDate));*/
        list.add(new BasicNameValuePair("start","2014-08-01"));
        list.add(new BasicNameValuePair("end","2014-09-01"));
        list.add(new BasicNameValuePair("page",page+""));
        bussinessParams.setParamList(list);

        httpAsyncTask = HttpManger.getInstance().getSend(context, bussinessParams, handler);
    }

    /**
     * 产品明细查询
     * @param productId
     * @param startDate
     * @param endDate
     * @param handler
     */
    public void getProductDetail(String productId,
                                 String startDate,
                                 String endDate,
                                 BusinessResponseHandler handler){
        BussinessParams bussinessParams = new BussinessParams(context);
        bussinessParams.setRelative_url("/api/anchor_client/sales/"+productId);
        //增加基本参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("t", "f05da37f8656c78db7efdb64a1166fb6273caf0d"));
       // list.add(new BasicNameValuePair("start",startDate));
        list.add(new BasicNameValuePair("start","2014-08-01"));
        list.add(new BasicNameValuePair("end","2014-09-01"));
        //list.add(new BasicNameValuePair("end",endDate));
        bussinessParams.setParamList(list);

        httpAsyncTask = HttpManger.getInstance().getSend(context, bussinessParams, handler);
    }






    public void cancel(){
        if(null != httpAsyncTask){
            httpAsyncTask.cancel(true);
        }
    }
}
