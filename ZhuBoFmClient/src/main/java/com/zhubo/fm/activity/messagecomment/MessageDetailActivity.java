package com.zhubo.fm.activity.messagecomment;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.andy.commonlibrary.net.exception.MessageException;
import com.andy.commonlibrary.util.ToastUtil;
import com.andy.corelibray.net.BusinessResponseHandler;
import com.andy.ui.libray.pullrefreshview.PullToRefreshBase;
import com.andy.ui.libray.pullrefreshview.PullToRefreshListView;
import com.google.gson.Gson;
import com.zhubo.control.activity.BaseActivity;
import com.zhubo.control.bussiness.bean.MessgaeDetailListBean;
import com.zhubo.fm.R;
import com.zhubo.fm.bll.common.FmConstant;
import com.zhubo.fm.bll.request.PrivateMessageRequest;

/**
 * 私信详情
 * Created by andy_lv on 2014/8/30.
 */
public class MessageDetailActivity extends BaseActivity{

    private PullToRefreshListView listView;
    private MessageDetailAdapter adapter;
    private int currentPage = 1;
    private PrivateMessageRequest privateMessageRequest;
    private EditText editText;
    private Button sendButton;



    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_detail_layout);
        initView();
        setListener();
        initData();
    }

    /**
     * 初始化view
     */
    private void initView(){
        navigationBar.setTitle(R.string.message_detail);
        navigationBar.setActionBtnVisibility(View.INVISIBLE);
        listView = (PullToRefreshListView) findViewById(R.id.message_detail_listview);
        editText = (EditText)findViewById(R.id.message_detail_edit);
        sendButton =(Button)findViewById(R.id.messsage_detail_send_button);
    }

    /**
     *
     */
    private void initData(){
     adapter = new MessageDetailAdapter(this);
     listView.getAdapterView().setAdapter(adapter);
     privateMessageRequest = new PrivateMessageRequest(this);
     Intent intent = getIntent();
     if(null != intent && intent.hasExtra(FmConstant.MESSAGE_FROM_SOMEBODY)){
         from = intent.getStringExtra(FmConstant.MESSAGE_FROM_SOMEBODY);
         loadMessage(currentPage);
     }
    }

    /**
     *
     */
    private void setListener(){
        sendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                 String message = editText.getText().toString();
                 if(!TextUtils.isEmpty(message)){
                     sendMessage(message);
                 }else{
                     ToastUtil.toast(MessageDetailActivity.this,"请输入发送内容");
                 }
            }
        });
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {
            @Override
            public void onPullDownToRefresh() {
                currentPage = 1;
                loadMessage(currentPage);
            }

            @Override
            public void onPullUpToRefresh() {
                loadMessage(currentPage);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /**
     * 请求数据
     * @param page
     */
     private void loadMessage(final int page){
         privateMessageRequest.getMessageDetail(page,from,new
                 BusinessResponseHandler(){
                     @Override
                     public void success(String response) {
                         super.success(response);
                       listView.onRefreshComplete();
                       Gson gson =new Gson();
                       MessgaeDetailListBean messgaeDetailListBean =
                               gson.fromJson(response,MessgaeDetailListBean.class);
                       adapter.setOtherUser(messgaeDetailListBean.getFromUser());
                       adapter.setData(messgaeDetailListBean.getMessages(),page == 1?true:false);
                       adapter.notifyDataSetChanged();
                         if(messgaeDetailListBean.isHasNext()){
                             currentPage = currentPage+1;
                         }else{
                             if(page != 1){
                                 ToastUtil.toast(MessageDetailActivity.this, "没有更多数据");
                             }
                         }
                     }

                     @Override
                     public void fail(MessageException exception) {
                         super.fail(exception);
                         listView.onRefreshComplete();
                     }

                     @Override
                     public void cancel() {
                         super.cancel();
                         listView.onRefreshComplete();
                     }
                 });
     }

    /**
     * 发布私信
     * @param message
     */
     public void sendMessage(String message){
         privateMessageRequest.sendMessage(from,message,new BusinessResponseHandler(this,true,"发送中..."){
             @Override
             public void success(String response) {
                 super.success(response);
                 editText.setText("");
                 ToastUtil.toast(MessageDetailActivity.this,"发送成功");
                 loadMessage(currentPage);
             }

             @Override
             public void fail(MessageException exception) {
                 super.fail(exception);
             }
         });
     }
}
