package com.andy.corelibray.net;


import android.content.Context;

import com.andy.commonlibrary.net.BaseRequest;


import org.apache.http.HttpException;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by andy_lv on 2014/8/23.
 */
public class HttpClientUtil {

    /**
     * http 请求类型
     */
    public enum RequestType {
        GET, POST,PUT
    }

    private static HttpClientUtil instance;
    private static BaseRequest baseRequest;

    private HttpClientUtil() {
        baseRequest = new BaseRequest();
    }

    /**
     * @return
     */
    public static HttpClientUtil getInstance() {
        if (null == instance) {
            instance = new HttpClientUtil();
        }
        return instance;
    }

    /**
     * 发送请求
     *
     * @param requestType     请求类型
     * @param bussinessParams 请求参数
     * @return
     */
    public String send(RequestType requestType, BussinessParams bussinessParams) throws TimeoutException, IOException, HttpException {
        String resposne = "";
        if (requestType.equals(RequestType.GET)) {
                resposne =  baseRequest.getRequest(bussinessParams.getParamList(),bussinessParams.getUrl());
        } else if (requestType.equals(RequestType.POST)) {
                resposne = baseRequest.postRequest(bussinessParams.getParamList(), bussinessParams.getUrl());
        }else if(requestType.equals(RequestType.PUT)){
               resposne = baseRequest.putRequest(bussinessParams.getParamList(), bussinessParams.getUrl());
        }
        return resposne;
    }

}
