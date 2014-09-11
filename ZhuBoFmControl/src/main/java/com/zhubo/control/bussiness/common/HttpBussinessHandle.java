package com.zhubo.control.bussiness.common;

import com.andy.commonlibrary.net.HttpClientHandleInterface;
import com.andy.commonlibrary.net.exception.MessageException;
import com.andy.corelibray.ui.common.DialogController;

/**
 * 业务请求回调
 * Created by andy_lv on 2014/9/2.
 */
public abstract class HttpBussinessHandle  implements HttpClientHandleInterface {

    @Override
    public abstract void start();

    public abstract void success(String object);

    public abstract void fail(MessageException exception);

    @Override
    public void finish() {
        DialogController.getInstance().dismiss();
    }

    @Override
    public void cancel() {
        DialogController.getInstance().dismiss();
    }
}
