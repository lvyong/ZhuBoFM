package com.zhubo.fm.activity.search;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.andy.commonlibrary.net.exception.MessageException;
import com.andy.commonlibrary.util.ToastUtil;
import com.andy.corelibray.net.BusinessResponseHandler;
import com.andy.ui.libray.component.NavigationBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhubo.control.activity.BaseActivity;
import com.zhubo.control.bussiness.bean.ProductBean;
import com.zhubo.fm.R;
import com.zhubo.fm.bll.common.FmConstant;
import com.zhubo.fm.bll.request.SearchRequestFactory;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy_lv on 2014/8/31.
 */
public class AddProductResultActivity extends BaseActivity {
    private final String TAG = "AddProductResultActivity";
    private ListViewCompat listview;
    private AddProductResultAdapter adapter;
    private SearchRequestFactory searchRequestFactory;
    private AddProductResultActivity self;
    private int programId ;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case FmConstant.DELETE_PRODUCT:
                     deleteProduct(adapter.getItem(msg.arg1).productBean.getId());
                     break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        self = this;
        setContentView(R.layout.activity_add_product_result_layout);
        initView();
        initData();
    }

    private void initView(){
        this.listview = (ListViewCompat) findViewById(R.id.activity_add_product_result_listview);
        navigationBar.setTitle(R.string.choosed_product);
        navigationBar.setBackButtonBackground(R.drawable.back);
        navigationBar.setBackBtnVisibility(View.VISIBLE);
        navigationBar.setActionBtnText(R.string.continue_add);
        navigationBar.setActionBtnVisibility(View.VISIBLE);
        navigationBar.setActionBtnTextColor(getResources().getColor(R.color.blue_3088EF));
    }

    private void initData(){
      Intent intent = getIntent();
      if(null != intent && intent.hasExtra(FmConstant.PROGRAM_ID)){
          programId = intent.getIntExtra(FmConstant.PROGRAM_ID,0);
          loadData(programId+"");
      }

    }

    @Override
    public void onNavItemClick(NavigationBar.NavigationBarItem navBarItem) {
        super.onNavItemClick(navBarItem);
        if(navBarItem == NavigationBar.NavigationBarItem.action){
                   finish();
        }
    }

    /**
     *
     * @param programId
     */
    public void loadData(String programId){
        searchRequestFactory = new SearchRequestFactory(this);
        searchRequestFactory.getAllProductsOfProgram(programId
                , new BusinessResponseHandler(this, true) {
            @Override
            public void success(String response) {
                Log.e(TAG,"----getAllProductsOfProgram:"+response);
                try{
                    Gson gson = new Gson();
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject != null){
                        ArrayList<ProductBean> list =
                                gson.fromJson(jsonObject.optString("products"),
                                        new TypeToken<List<ProductBean>>() {
                                        }.getType());
                        adapter = new AddProductResultAdapter(AddProductResultActivity.this, false);
                        adapter.addData(list,true);
                        adapter.setHandler(handler);
                        listview.setAdapter(adapter);
                    }

                }catch(Exception e){

                }
            }

            @Override
            public void fail(MessageException exception) {
                super.fail(exception);
            }

            @Override
            public void cancel() {
                super.cancel();

            }
        });
    }

    /**
     * 删除产品
     * @param productIds
     */
    private void deleteProduct(final String productIds){
        BusinessResponseHandler businessResponseHandler =
                new BusinessResponseHandler(this,true,"产品删除中..."
                        ,false,true){
                    @Override
                    public void success(String response) {
                        Log.e(TAG,"----deleteProduct=="+response);
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.optBoolean("success")){
                                ToastUtil.toast(self, "删除产品成功");
                                loadData(programId+"");
                            }
                        }catch (Exception e){

                        }
                    }
                    @Override
                    public void fail(MessageException exception) {
                        super.fail(exception);
                    }

                    @Override
                    public void cancel() {
                        super.cancel();
                        searchRequestFactory.cancle();
                    }
                };
        searchRequestFactory.chooseProduct(programId,productIds,true,businessResponseHandler);
    }
}
