package com.andy.commonlibrary.net;

import com.andy.commonlibrary.net.exception.MessageException;

/**
 * http 请求 handle 接口
 * Created by andy_lv on 2014/8/23.
 */
public interface HttpClientHandleInterface{

    /**
     * 请求开始
     */
    public void start();

    /**
     * 请求成功，返回报文，最后解析成具体对象
     * @param object
     * @return
     */
    public void success(String object);

    /**
     * 请求失败，返回错误消息
     * @param exception
     */
    public void  fail(MessageException exception);


    /**
     * 请求结束
     */
    public void  finish();

    /**
     * 请求取消
     */
    public void cancel();





}
