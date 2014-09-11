package com.andy.corelibray.ui.common;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.andy.commonlibrary.util.StringUtil;
import com.andy.corelibray.R;
import com.andy.ui.libray.dialog.AlertDialog;
import com.andy.ui.libray.dialog.BaseDialog;
import com.andy.ui.libray.dialog.DialogUtil;
import com.andy.ui.libray.dialog.ProgressDialog;

import java.util.HashMap;
import java.util.Map;

/**
 * Dialog控制器
 * 管理对话框的显示与清除
 * Created by xyz on 14-1-4.
 */
public class DialogController implements DialogInterface.OnDismissListener,ProgressDialog.OnDialogKeyListener {

    private static DialogController instance;

    //保证只有一个对话框实例存在
    private BaseDialog dialogFragment;

    private DialogConfirmClick mDialogConfirmClick;

    //当前对话框所在的页面
    private Activity currentActivity;

    private Map<Activity,BaseDialog> dialogMap = new HashMap<Activity, BaseDialog>();

    //自定义View布局参数
    private LinearLayout.LayoutParams mLayoutParameters = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);

    private DialogController(){

    }

    public static DialogController getInstance(){
        if (instance == null){
            instance = new DialogController();
        }

        return  instance;
    }


    /**
     * 设置对话框标题
     * @param title
     */
    public void setTitle(String title){
        if (!(dialogFragment instanceof AlertDialog)) return;

        AlertDialog alertDialog = (AlertDialog)dialogFragment;

        alertDialog.setTitle(title);
    }

    /**
     * 设置对话框标题
     * @param titleId
     */
    public void setTitle(int titleId){
        if (!(dialogFragment instanceof AlertDialog)) return;

        AlertDialog alertDialog = (AlertDialog)dialogFragment;

        alertDialog.setTitle(titleId);
    }

    /**
     * 设置对话框消息内容
     * @param message
     */
    public void setMessage(String message){
        if (!(dialogFragment instanceof AlertDialog)) return;

        AlertDialog alertDialog = (AlertDialog)dialogFragment;

        alertDialog.setMessage(message);
    }

    /**
     * 设置对话框中间的view
     * @param middleView
     */
    public void setMiddleView(View middleView){
        if (!(dialogFragment instanceof AlertDialog)) return;

        AlertDialog alertDialog = (AlertDialog)dialogFragment;

        alertDialog.setMiddleView(middleView);
    }

    /**
     * 设置按钮文字
     * @param buttonTypeEnum
     * @param text
     */
    public void setButtonText(AlertDialog.Builder.ButtonTypeEnum buttonTypeEnum,String text){
        if (!(dialogFragment instanceof AlertDialog)) return;

        AlertDialog alertDialog = (AlertDialog)dialogFragment;

        alertDialog.setButtonText(buttonTypeEnum, text);
    }

    /**
     * 设置按钮是否可用
     * @param buttonTypeEnum
     * @param isEnable 按钮是否可用
     */
    public void setButtonEnable(AlertDialog.Builder.ButtonTypeEnum buttonTypeEnum,boolean isEnable){
        if (!(dialogFragment instanceof AlertDialog)) return;

        AlertDialog alertDialog = (AlertDialog)dialogFragment;

        alertDialog.setButtonEnable(buttonTypeEnum, isEnable);
    }

    /**
     * 设置按钮是否可见
     * @param buttonTypeEnum
     * @param visibility 按钮是否可见
     */
    public void setButtonVisable(AlertDialog.Builder.ButtonTypeEnum buttonTypeEnum,int visibility){
        if (!(dialogFragment instanceof AlertDialog)) return;

        AlertDialog alertDialog = (AlertDialog)dialogFragment;

        alertDialog.setButtonVisiblity(buttonTypeEnum, visibility);
    }

    /**
     * 显示一个提示对话框，默认一个确定按钮，点击后取消对话框显示
     * @param activity  对话框依赖的Activity
     * @param message   消息提示内容
     */
    public void showAlertDialog(FragmentActivity activity,String message){

        showAlertDialog(activity,"", message);
    }

    /**
     * 显示一个带title的消息提示框
     * @param activity
     * @param title
     * @param message
     */
    public void showAlertDialog(FragmentActivity activity,String title,String message){

        if (!isCouldShow(activity)) return;

        String middleButtonText = activity.getResources().getString(R.string.com_confirm);

        showAlertDialog(activity, 0, title, message, "", "", middleButtonText, defaultDialogClickListener, false);

    }

    /**
     * 显示一个带title的消息提示框
     * @param activity
     * @param title
     * @param message
     */
    public void showAlertDialog(FragmentActivity activity,String title,String message,AlertDialog.Builder.AlertDialogClickListener clickListener){
        if (!isCouldShow(activity)) return;
        String middleButtonText = activity.getResources().getString(R.string.com_confirm);

        showAlertDialog(activity, 0, title, message, "", "", middleButtonText, clickListener, false);
    }

    /**
     * 显示一个消息对话框,可以定制确认按钮文字和事件
     * @param activity
     * @param title
     * @param message
     * @param middleButtonText  默认为“确定”
     * @param alertDialogClickListener
     */
    public void showAlertDialog(FragmentActivity activity,
                                String title,
                                String message,
                                String middleButtonText,
                                AlertDialog.Builder.AlertDialogClickListener alertDialogClickListener){
        if (!isCouldShow(activity)) return;

        if (StringUtil.isEmpty(middleButtonText)){
            middleButtonText = activity.getResources().getString(R.string.com_confirm);
        }

        showAlertDialog(activity, 0, title, message, "", "", middleButtonText, alertDialogClickListener, false);

    }

    /**
     * 标准对话框  title + message + 两个按钮
     * @param context
     * @param title
     * @param message
     * @param leftButtonText
     * @param rightButtionText
     * @param listener
     */
    public void showAlertDialog(FragmentActivity context,
                                String title,
                                String message,
                                String leftButtonText,
                                String rightButtionText,
                                AlertDialog.Builder.AlertDialogClickListener listener){
        if (!isCouldShow(context)) return;

        showAlertDialog(context, 0, title, message, leftButtonText, rightButtionText, null, listener, false);
    }

    /**
     * 全参构建AlertDialog并显示
     * @param activity
     * @param titleIconId
     * @param title
     * @param message
     * @param leftButtonText
     * @param rightButtonText
     * @param middleButtonText
     * @param clickListener
     */
    public void showAlertDialog(FragmentActivity activity,
                                int titleIconId,
                                String title,
                                String message,
                                String leftButtonText,
                                String rightButtonText,
                                String middleButtonText,
                                AlertDialog.Builder.AlertDialogClickListener clickListener,
                                boolean cancelable){
        if (!isCouldShow(activity)) return;
        dismiss();
        dialogFragment = DialogUtil.createAlertDialog(activity.getSupportFragmentManager(),
                titleIconId,
                title,
                message,
                leftButtonText,
                rightButtonText,
                middleButtonText,
                clickListener);
        dialogFragment.setCancelable(cancelable);
        dialogFragment.setCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (mDialogConfirmClick!=null){
                    mDialogConfirmClick.onDialogCancelClick();
                }
            }
        });
        setButtonEnable(AlertDialog.Builder.ButtonTypeEnum.RIGHT_BUTTON, true);
        if (StringUtil.isNotEmpty(rightButtonText)) setButtonVisable(AlertDialog.Builder.ButtonTypeEnum.RIGHT_BUTTON,View.VISIBLE);
        show(dialogFragment);
    }

    /**
     * 全参构建AlertDialog并显示
     * @param activity
     * @param titleIconId
     * @param title
     * @param middleView
     * @param leftButtonText
     * @param rightButtonText
     * @param middleButtonText
     * @param clickListener
     */
    public void showAlertDialog(FragmentActivity activity,
                                int titleIconId,
                                String title,
                                View middleView,
                                String leftButtonText,
                                final String rightButtonText,
                                String middleButtonText,
                                AlertDialog.Builder.AlertDialogClickListener clickListener,
                                boolean cancelable){

        if (!isCouldShow(activity)) return;

        dismiss();

        AlertDialog alertDialog = DialogUtil.createAlertDialog(activity.getSupportFragmentManager(),
                titleIconId,
                title,
                "",
                leftButtonText,
                rightButtonText,
                middleButtonText,
                clickListener);
        alertDialog.setMiddleView(middleView, mLayoutParameters);
        dialogFragment = alertDialog;
        dialogFragment.setCancelable(cancelable);
        setButtonEnable(AlertDialog.Builder.ButtonTypeEnum.RIGHT_BUTTON, true);
        alertDialog.setCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (mDialogConfirmClick != null) {
                    mDialogConfirmClick.onDialogCancelClick();
                }
            }
        });
        if (StringUtil.isNotEmpty(rightButtonText)) setButtonVisable(AlertDialog.Builder.ButtonTypeEnum.RIGHT_BUTTON,View.VISIBLE);
        show(dialogFragment);
    }

    /**
     * 提供刷卡统一调用
     */
    public void showProgress(FragmentActivity activity){

        showProgressDialog(activity,"正在识别设备...");

    }

    /**
     * 显示一个进度提示对话框,无进度提示文字
     * @param activity
     */
    public void showProgressDialog(FragmentActivity activity){

        showProgressDialog(activity,"");

    }

    /**
     * 显示一个进度对话框，包含进度提示文字
     * @param activity
     * @param loadingMessage 进度提示文本
     */
    public void showProgressDialog(FragmentActivity activity,String loadingMessage){
        if (!isCouldShow(activity)) return;
        dismiss();
        dialogFragment = DialogUtil.createProgressDialog(activity.getSupportFragmentManager(), loadingMessage);
        ProgressDialog progressDialog = (ProgressDialog)dialogFragment;
        progressDialog.setOnDismissListener(this);
        progressDialog.setOnDialogKeyListener(this);
        show(dialogFragment);
    }

    /**
     * 对话框显示之前预处理
     */
    private boolean isCouldShow(Activity activity){
        currentActivity = activity;
        return activity != null && !activity.isFinishing();
    }

    private void show(BaseDialog dialogFragment){
        dialogMap.put(currentActivity,dialogFragment);
        dialogFragment.show();
    }

    /**
     * 取消对话框显示
     */
    public void dismiss(){

        //java.lang.IllegalStateException: Activity has been destroyed
        //Crash 报出上述异常，再次做下述判断，Activity没有消失之前，对对话框进行取消操作
        if (currentActivity == null || currentActivity.isFinishing()) {
            return;
        }

        if (dialogMap == null || dialogMap.size() ==0)  return;

        BaseDialog baseDialog = dialogMap.get(currentActivity);

        if (baseDialog == null){
            return;
        }

        //java.lang.NullPointerException DialogFragment 184行，v4包中报空指针，try catch下，避免crash
        try {
            baseDialog.dismissAllowingStateLoss();
            dialogMap.remove(baseDialog);
            baseDialog = null;
        }catch (Exception e){

        }

    }

    /**
     * 默认对话框点击事件处理
     */
    private AlertDialog.Builder.AlertDialogClickListener defaultDialogClickListener = new AlertDialog.Builder.AlertDialogClickListener() {
        @Override
        public void clickCallBack(AlertDialog.Builder.ButtonTypeEnum typeEnum, AlertDialog alertDialog) {
            switch (typeEnum){
                case LEFT_BUTTON:
                    break;
                case RIGHT_BUTTON:
                    break;
                case MIDDLE_BUTTON:
                    alertDialog.dismiss();
                    break;
            }
        }
    };


        /**
         *  设置对话框按钮点击监听器
         * @param mDialogConfirmClick
         */
    private void setDialogConfirmClick(DialogConfirmClick mDialogConfirmClick) {
        this.mDialogConfirmClick = mDialogConfirmClick;
    }

    public DialogConfirmClick getmDialogConfirmClick() {
        return mDialogConfirmClick;
    }

    /**
     *    对话框按钮点击回调监听器
     **/
    public  interface  DialogConfirmClick {
        /**  点击确认按钮回调*/
        void onDialogConfirmClick(Object data);

        /**  点击取消输入按钮回调*/
        void onDialogCancelClick();
    }


    public void setCancel(boolean isCancel){
        if(null!= dialogFragment){
            dialogFragment.setCancelable(isCancel);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {

    }

    @Override
    public void onDialogKeyevent(int keyCode, KeyEvent keyEvent) {
        if (keyCode == KeyEvent.KEYCODE_BACK){

        }
    }
}
