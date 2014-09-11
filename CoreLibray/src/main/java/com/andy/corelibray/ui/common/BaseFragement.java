package com.andy.corelibray.ui.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import java.util.ArrayList;

/**
 * Fragement的基类
 * Created by andy_lv on 14-1-22.
 */
public class BaseFragement extends Fragment implements View.OnClickListener{

    public BaseFragement(){
        super();
    }
    /**
     * BaseFragment切换的回调接口
     */
    public static interface BaseFragementSwitchCallBack{
        public void startFragement(BaseFragement fragement);
        public void back();
    }

    /**
     * 回调接口实例
     */
    private BaseFragementSwitchCallBack baseFragementSwitchCallBack;

    /**
     * 设置Fragemnet回调接口
     * @param baseFragementSwitchCallBack  实现此接口的类
     */
    public void setBaseFragementSwitchCallBack(BaseFragementSwitchCallBack  baseFragementSwitchCallBack){
        this.baseFragementSwitchCallBack = baseFragementSwitchCallBack;
    }

    /**
     * 启动下一个页面
     */
    public void startNextFragment(BaseFragement fragement){
        if (baseFragementSwitchCallBack != null){
            baseFragementSwitchCallBack.startFragement(fragement);
        }
    }

    /**
     * 返回上一个页面
     */
    public void onBack(){
        if (baseFragementSwitchCallBack != null){
            baseFragementSwitchCallBack.back();
        }
    }

    /**	间隔时间击时间	*/
    private static long intervalTime =800;
    /**	最后点击时间	*/
    private static long lastClickTime;
    private static ArrayList<View> views = new ArrayList<View>();

    @Override
    public void onClick(View view) {
        if (!isFastDoubleClick(view)){
            onViewClick(view);
        }
    }

    /**
     *   防短时重复点击回调 <br>
     *   子类使用OnClickListener设置监听事件时直接覆写该方法完成点击回调事件
     *   @param view
     */
    protected void onViewClick(View view){
    }

    /**
     * 是否重复点击
     * @view 被点击view，如果前后是同一个view，则进行双击校验
     * @return
     */
    private  boolean isFastDoubleClick(View view) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (views.size()== 0) {
            views.add(view);
        }
        if ( 0 < timeD && timeD < intervalTime && views.get(0).getId() == view.getId()) {
            return true;
        }
        lastClickTime = time;
        views.clear();
        views.add(view);
        return false;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            String name = this.getClass().getSimpleName();
        } catch (Exception e) {
        }
    }

}
