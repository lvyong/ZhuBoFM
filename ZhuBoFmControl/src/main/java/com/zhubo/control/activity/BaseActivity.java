package com.zhubo.control.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import com.andy.ui.libray.component.NavigationBar;
import com.zhubo.control.R;

import java.util.ArrayList;

/**
 * 基类Activity
 * Created by andy_lv on 2014/8/24.
 */
public class BaseActivity extends FragmentActivity implements NavigationBar.OnNavBarClickListener,View.OnClickListener{

    /**
     * 间隔时间击时间
     */
    private static long intervalTime = 800;

    //导航栏
    protected NavigationBar navigationBar;

    //view容器，所有子类的根布局都会添加到此容器中
    private FrameLayout baseContainer;

    private static long lastClickTime;
    private static ArrayList<View> views = new ArrayList<View>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_base_layout);

        //初始化导航栏
        navigationBar = (NavigationBar) findViewById(R.id.navigation_bar);
        navigationBar.setOnNavBarClickListener(this);

        //初始化根布局容器
        baseContainer = (FrameLayout) findViewById(R.id.base_container);
        ViewGroup.inflate(this, layoutResID, baseContainer);
    }


    @Override
    public void onNavItemClick(NavigationBar.NavigationBarItem navBarItem) {
        if (navBarItem == NavigationBar.NavigationBarItem.back) finish();
    }

    @Override
    public void onClick(View view) {
        if (!isFastDoubleClick(view)) {
            onViewClick(view);
        }
    }

    /**
     * 防短时重复点击回调 <br>
     * 子类使用OnClickListener设置监听事件时直接覆写该方法完成点击回调事件
     *
     * @param view
     */
    protected void onViewClick(View view) {

    }

    /**
     * 是否重复点击
     *
     * @return
     * @view 被点击view，如果前后是同一个view，则进行双击校验
     */
    private boolean isFastDoubleClick(View view) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (views.size() == 0) {
            views.add(view);
        }
        if (0 < timeD && timeD < intervalTime && views.get(0).getId() == view.getId()) {
            return true;
        }
        lastClickTime = time;
        views.clear();
        views.add(view);
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
