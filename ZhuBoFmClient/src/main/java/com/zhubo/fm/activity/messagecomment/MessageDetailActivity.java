package com.zhubo.fm.activity.messagecomment;


import android.os.Bundle;
import android.widget.ListView;

import com.andy.ui.libray.pullrefreshview.PullToRefreshBase;
import com.andy.ui.libray.pullrefreshview.PullToRefreshListView;
import com.zhubo.control.activity.BaseActivity;
import com.zhubo.fm.R;

/**
 * 私信详情
 * Created by andy_lv on 2014/8/30.
 */
public class MessageDetailActivity extends BaseActivity{

    private PullToRefreshListView listView;
    private MessageDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_detail_layout);
        initView();
        initData();
        setListener();
    }

    /**
     * 初始化view
     */
    private void initView(){
        navigationBar.setTitle(R.string.message_detail);
        navigationBar.setActionBtnBackground(R.drawable.delete);
        listView = (PullToRefreshListView) findViewById(R.id.message_detail_listview);
    }

    /**
     *
     */
    private void initData(){
     adapter = new MessageDetailAdapter(this);
     listView.getAdapterView().setAdapter(adapter);
    }

    /**
     *
     */
    private void setListener(){
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {
            @Override
            public void onPullDownToRefresh() {
                listView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh() {
                listView.onRefreshComplete();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
