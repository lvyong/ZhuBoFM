package com.zhubo.fm.activity.realtimeinteract;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.andy.ui.libray.pullrefreshview.PullToRefreshBase;
import com.andy.ui.libray.pullrefreshview.PullToRefreshListView;
import com.zhubo.control.activity.BaseActivity;
import com.zhubo.fm.R;

/**
 * 即时互动
 * Created by andy_lv on 2014/8/31.
 */
public class RealTimeInteractActivity extends BaseActivity {

    private ViewPager viewPager;
    private PullToRefreshListView listview;
    private RealTimeViewPageAdapter viewPageAdapter;
    private RealTimeInteractAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_time_interact_layout);
        initView();
        initData();
        setLitener();
    }

    /**
     * 初始化view
     */
    private void initView(){
        viewPager = (ViewPager)findViewById(R.id.activity_realtime_interact_viewpager);
        listview = (PullToRefreshListView)findViewById(
                R.id.activity_realtime_interact_listview);
    }

    /**
     *
     */
    private void initData(){
        viewPageAdapter = new RealTimeViewPageAdapter(this);
        viewPager.setAdapter(viewPageAdapter);
        adapter = new RealTimeInteractAdapter(this);
        listview.getAdapterView().setAdapter(adapter);
    }

    private void setLitener(){
        listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {
            @Override
            public void onPullDownToRefresh() {
                listview.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh() {
                listview.onRefreshComplete();
            }
        });

    }

}
