package com.zhubo.fm.activity.live;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.andy.ui.libray.component.NavigationBar;
import com.zhubo.control.activity.BaseActivity;
import com.zhubo.control.activity.common.BottomTabBar;
import com.zhubo.control.activity.fragement.BaseFragment;
import com.zhubo.control.activity.fragement.FragmentController;
import com.zhubo.fm.R;
import com.zhubo.fm.activity.live.fragement.ListenerBenefitFragement;
import com.zhubo.fm.activity.live.fragement.LiveProgramNoteFragement;
import com.zhubo.fm.activity.live.fragement.RealTimeInteractFragement;
import com.zhubo.fm.activity.realtimeinteract.RealTimeInteractActivity;
import com.zhubo.fm.bll.common.FmConstant;

/**
 * 直播
 * Created by andy_lv on 2014/8/30.
 */
public class LiveActivity extends BaseActivity implements BottomTabBar.BottomTabBarClickCallBack,BaseFragment.NavigationCallBack{

    private BottomTabBar bottomTabBar;
    private FragmentController fragmentController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_layout);
        initView();
        initData();
    }

    /**
     * 初始化view
     */
    private void initView(){
        bottomTabBar = (BottomTabBar) findViewById(R.id.activity_live_bottom_tabbar);
        bottomTabBar.setLeftLayoutValue(new int[]{R.drawable.program_manage ,
                R.drawable.tap_program_manage},getString(R.string.program_comment));
        bottomTabBar.setMiddleLayoutValue(new int[]{R.drawable.money,
                R.drawable.tap_money}, getString(R.string.listener_benefit));
        bottomTabBar.setRightLayoutValue(new int[]{R.drawable.message,
                R.drawable.tap_message}, getString(R.string.real_time_interact));

        this.bottomTabBar.setBottomTabBarClickCallBack(this);
        this.fragmentController  = new FragmentController(this, R.id.activity_live_content_layout);
    }

    /**
     * 初始化数据
     */
    private void initData(){
        this.bottomTabBar.clickTab(BottomTabBar.BottomTabBarButtonType.LEFT_BUTTON);
    }

    @Override
    public void click(BottomTabBar.BottomTabBarButtonType buttonType, View view) {
        String fName        = null;
        Bundle bundle = null;
        BaseFragment newFragment = null;
        switch (buttonType) {
            case LEFT_BUTTON:
                fName = LiveProgramNoteFragement.class.getName();
                newFragment = new LiveProgramNoteFragement();
                bundle = getIntent().getBundleExtra(FmConstant.BUNDLE_DATA);
                break;
            case MIDDLE_BUTTON:
                fName = ListenerBenefitFragement.class.getName();
                newFragment = new ListenerBenefitFragement();
                bundle = getIntent().getBundleExtra(FmConstant.BUNDLE_DATA);
                break;
            case RIGHT_BUTTON:
              /*  fName = RealTimeInteractFragement.class.getName();
                newFragment = new RealTimeInteractFragement();*/
                startActivity(new Intent(this, RealTimeInteractActivity.class));
                return ;
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case FmConstant.LIVE_NOTE_FRAGEMENT:
                if(resultCode == Activity.RESULT_OK){
                    setResult(Activity.RESULT_OK);
                    finish();
                }
        }
    }
}
