package com.andy.corelibray.net;

import android.support.v4.app.FragmentActivity;

import com.andy.commonlibrary.net.HttpClientHandleInterface;
import com.andy.commonlibrary.net.exception.MessageException;
import com.andy.commonlibrary.util.ToastUtil;
import com.andy.corelibray.R;
import com.andy.corelibray.ui.common.DialogController;


/**
 * 业务请求响应对象
 * 对业务请求通用异常进行处理
 */
public class BusinessResponseHandler  implements HttpClientHandleInterface{

    @Override
    public void success(String response) {
    }

    @Override
    public void cancel() {

    }

    /**
     * 请求所在的Activity
     */
    private FragmentActivity activity;

    /**
     * 网络请求开始时，是否弹出进度对话框
     */
    private boolean isShowProgress;

    /**
     * 请求结束，不管成功或失败，是否消除进度对话框
     */
    private boolean isDismissProgress;

    /**
     * 是否在请求过程中，让Dialog消失
     */
    private boolean isCancel = true;

    /**
     * 加载的dialog的message
     */
    private String loadMessage;

    //是否后台运行
    private boolean isBackground = false;

    public BusinessResponseHandler() {
        //调用此构造函数的请求，认为是不需要显示ProgressDialog的
        this(null,false,null,false,true);
        this.setBackground(true);
    }

    public BusinessResponseHandler(FragmentActivity context) {
        this(context,false,null,true,true);
    }

    /**
     * @param activity       请求所依赖的页面
     * @param isShowProgress 是否显示进度提示对话框
     */
    public BusinessResponseHandler(FragmentActivity activity, boolean isShowProgress) {
        this(activity,isShowProgress,null,true,true);
    }

    /**
     * 构造请求响应处理器
     *
     * @param activity       请求所依赖的页面
     * @param isShowProgress 是否显示进度提示对话框
     * @param loadMessage    显示加载文字
     */
    public BusinessResponseHandler(FragmentActivity activity, boolean isShowProgress, String loadMessage) {
        this(activity,isShowProgress,loadMessage,true,true);
    }

    /**
     * 构造请求响应处理器
     *
     * @param activity          请求所依赖的页面
     * @param isShowProgress    是否显示进度提示对话框
     * @param isDismissProgress 请求结束是否清除进度对话框
     */
    public BusinessResponseHandler(FragmentActivity activity, boolean isShowProgress, boolean isDismissProgress) {
        this(activity,isShowProgress,null,true,isDismissProgress);
    }

    /**
     * 构造请求响应处理器
     *
     * @param activity          请求所依赖的页面
     * @param isShowProgress    是否显示进度提示对话框
     * @param isDismissProgress 请求结束是否清除进度对话框
     * @param loadMessage       显示加载文字
     */
    public BusinessResponseHandler(FragmentActivity activity, boolean isShowProgress, boolean isDismissProgress, String loadMessage) {
        this(activity,isShowProgress,loadMessage,true,isDismissProgress);
    }

    /**
     * 构造请求响应处理器
     * @param activity          请求所依赖的页面
     * @param isCancel          请求中是否可以清除对话框
     * @param isShowProgress    是否显示进度提示对话框
     * @param isDismissProgress 请求结束是否清除进度对话框
     * @param loadMessage       显示加载文字
     */
    public BusinessResponseHandler(FragmentActivity activity, boolean isShowProgress, String loadMessage, boolean isCancel, boolean isDismissProgress) {
        this.activity           = activity;
        this.isShowProgress     = isShowProgress;
        this.loadMessage        = loadMessage;
        this.isCancel           = isCancel;
        this.isDismissProgress  = isDismissProgress;
    }

    public boolean isBackground() {
        return isBackground;
    }

    public void setBackground(boolean isBackground) {
        this.isBackground = isBackground;
    }

    public void setShowProgress(boolean isShowProgress) {
        this.isShowProgress = isShowProgress;
    }

    public void setContext(FragmentActivity activity) {
        this.activity = activity;
    }

    public FragmentActivity getContext(){
        return activity;
    }

    public void setDismissProgress(boolean isDismissProgress) {
        this.isDismissProgress = isDismissProgress;
    }

    public void setLoadMessage(String loadMessage) {
        this.loadMessage = loadMessage;
    }

    public void setCancel(boolean isCancel) {
        this.isCancel = isCancel;
    }

    @Override
    public void start() {
        if (isShowProgress) {
            DialogController.getInstance().showProgressDialog(activity, loadMessage);
            DialogController.getInstance().setCancel(isCancel);
        }
    }


    @Override
    public void finish() {
        dismissProgress();
    }


    @Override
    public void fail(MessageException exception) {
        if (activity != null && !activity.isFinishing()) {
            DialogController.getInstance().dismiss();
        }
        handleFailException(exception);
    }


    /**
     * 处理失败时的异常
     *
     * @param exception
     */
    protected void handleFailException(MessageException exception) {

        if (null == activity) {
            return;
        }

        if(exception.getStatusCode().equals(activity.getResources().getString(R.string.io_statuscode))
          || exception.getStatusCode().equals(activity.getResources().getString(R.string.timeout_status_code))
          || exception.getStatusCode().equals(activity.getResources().getString(R.string.general_statuscode))){
            DialogController.getInstance().showAlertDialog(activity, exception.getStatusMessage());
            isDismissProgress = false;
        }else{
            ToastUtil.toast(activity, exception.getStatusMessage());
        }
    }


    private void dismissProgress() {
        if (isDismissProgress && activity != null && !activity.isFinishing()) {
            DialogController.getInstance().dismiss();
        }
    }
}
