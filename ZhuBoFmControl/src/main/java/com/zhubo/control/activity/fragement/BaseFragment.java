package com.zhubo.control.activity.fragement;

import android.os.Bundle;
import android.view.View;

import com.andy.corelibray.ui.common.BaseFragement;
import com.andy.ui.libray.component.NavigationBar;


public abstract class BaseFragment extends BaseFragement implements View.OnClickListener{

    private NavigationCallBack navigationCallBack;

    public interface NavigationCallBack {
        public NavigationBar getNavigationBar();
    }

    public abstract void setNavigationBar(NavigationBar navigationBar);

    public abstract void onNavItemClick(NavigationBar.NavigationBarItem navBarItem);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        NavigationBar nav = navigationCallBack !=null ?navigationCallBack.getNavigationBar():null;
        if (nav != null){
            nav.clear();
            setNavigationBar(nav);
        }
    }

    /**
     *
     * @param navigationCallBack
     */
    public void setNavigationCallBack(NavigationCallBack navigationCallBack){
        this.navigationCallBack = navigationCallBack;
    }

}
