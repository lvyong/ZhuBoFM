package com.zhubo.fm.activity.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.andy.commonlibrary.util.ToastUtil;
import com.andy.ui.libray.component.NavigationBar;
import com.zhubo.control.activity.BaseActivity;
import com.zhubo.control.activity.common.BottomTabBar;
import com.zhubo.control.activity.fragement.BaseFragment;
import com.zhubo.control.activity.fragement.FragmentController;
import com.zhubo.fm.R;
import com.zhubo.fm.ZhuBoApplication;
import com.zhubo.fm.activity.main.fragement.MessageCommentFragement;
import com.zhubo.fm.activity.main.fragement.ProductSaleFragement;
import com.zhubo.fm.activity.main.fragement.ProgramManageFragement;
import com.zhubo.fm.bll.common.FmConstant;

/**
 * 应用程序入口页面
 */
public class MainActivity extends BaseActivity implements BottomTabBar.BottomTabBarClickCallBack,BaseFragment.NavigationCallBack{

    private BottomTabBar bottomTabBar;
    private FragmentController fragmentController;


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(FmConstant.UNLOGIN_ACTION)){
                 finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    /**
     * 初始化Ui
     */
    private void initView(){
       this.bottomTabBar = (BottomTabBar) findViewById(R.id.main_bottom_tab_bar);

       this.bottomTabBar.setLeftLayoutValue(new int[]{
               com.zhubo.control.R.drawable.program_manage,
               com.zhubo.control.R.drawable.tap_program_manage},
               getString(com.zhubo.control.R.string.program_manage));
       this.bottomTabBar.setMiddleLayoutValue(new int[]{
                       com.zhubo.control.R.drawable.program_sale,
                       com.zhubo.control.R.drawable.tap_program_sale},
               getString(com.zhubo.control.R.string.prouduct_sale)
       );
       this.bottomTabBar.setRightLayoutValue(new int[]{
                       com.zhubo.control.R.drawable.message,
                       com.zhubo.control.R.drawable.tap_message},
               getString(com.zhubo.control.R.string.private_messgae_comment)
       );

      this.bottomTabBar.setBottomTabBarClickCallBack(this);
      this.fragmentController  = new FragmentController(this, R.id.fragment_container);
    }

    /**
     * 初始化数据
     */
     private void initData(){
         this.bottomTabBar.clickTab(BottomTabBar.BottomTabBarButtonType.LEFT_BUTTON);
         IntentFilter intentFilter =new IntentFilter(FmConstant.UNLOGIN_ACTION);
         registerReceiver(receiver,intentFilter);
     }

    /**
     * 底部Tab button 点击回调
     * @param buttonType
     * @param view
     */
    @Override
    public void click(BottomTabBar.BottomTabBarButtonType buttonType, View view) {
        String fName        = null;
        Bundle bundle = new Bundle();
        BaseFragment newFragment = null;
        switch (buttonType) {
            case LEFT_BUTTON:
                fName = ProgramManageFragement.class.getName();
                newFragment = new ProgramManageFragement();
                ProgramManageFragement.setIsStopStateHide(false);
                break;
            case MIDDLE_BUTTON:
                fName = ProductSaleFragement.class.getName();
                newFragment = new ProductSaleFragement();
                break;
            case RIGHT_BUTTON:
                fName = MessageCommentFragement.class.getName();
                newFragment = new MessageCommentFragement();
                break;
        }
        newFragment.setArguments(bundle);
        newFragment.setNavigationCallBack(this);
        fragmentController.switchFragment(buttonType.ordinal(), fName, newFragment);
    }

    @Override
    public NavigationBar getNavigationBar() {
        return navigationBar;
    }

    @Override
    public void onNavItemClick(NavigationBar.NavigationBarItem navBarItem) {
        BaseFragment current = fragmentController.getCurrentFragment();
        if (current != null)   current.onNavItemClick(navBarItem);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    /**
     *
     */
    private void exit(){
        ZhuBoApplication.getInstance().exit(this);
    }
}
