package com.zhubo.fm.activity.live.fragement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andy.ui.libray.component.NavigationBar;
import com.zhubo.control.activity.fragement.BaseFragment;
import com.zhubo.fm.R;

/**
 * 即时互动
 * Created by andy_lv on 2014/8/30.
 */
public class RealTimeInteractFragement extends BaseFragment{

    @Override
    public void setNavigationBar(NavigationBar navigationBar) {
        navigationBar.setTitle(R.string.real_time_interact);
        navigationBar.setActionBtnVisibility(View.INVISIBLE);
        navigationBar.setBackBtnVisibility(View.VISIBLE);
        navigationBar.setBackButtonBackground(R.drawable.back);
    }

    @Override
    public void onNavItemClick(NavigationBar.NavigationBarItem navBarItem) {
       if(navBarItem == NavigationBar.NavigationBarItem.back){
           getActivity().finish();
       }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
