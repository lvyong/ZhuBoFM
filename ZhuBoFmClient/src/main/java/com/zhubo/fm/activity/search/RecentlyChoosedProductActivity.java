package com.zhubo.fm.activity.search;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.andy.commonlibrary.net.exception.MessageException;
import com.andy.commonlibrary.util.ToastUtil;
import com.andy.corelibray.net.BusinessResponseHandler;
import com.zhubo.control.activity.BaseActivity;
import com.zhubo.control.bussiness.bean.ProductBean;
import com.zhubo.control.bussiness.db.RecentlyChooseProductDB;
import com.zhubo.fm.R;
import com.zhubo.fm.bll.common.FmConstant;
import com.zhubo.fm.bll.request.SearchRequestFactory;

import org.json.JSONObject;

import java.util.List;

/**
 * 近期已选产品
 * Created by andy_lv on 2014/9/12.
 */
public class RecentlyChoosedProductActivity extends BaseActivity {
    private final String TAG = "RecentlyChoosedProductActivity";
    private ListView listView;
    private Button  chooseButton;
    private int count;
    private TextView chooseCountTextView;
    private RecentlyChooseProductAdapter adapter;
    private RecentlyChoosedProductActivity self;
    private SearchRequestFactory searchRequestFactory;
    private RecentlyChooseProductDB recentlyChooseProductDB;

    private int addCount = 0;
    private int programId ;

    private Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case FmConstant.ADD_PRODUCT:
                    chooseProduct(adapter.getItem(msg.arg2).getId(),msg.arg1 == 1?false:true,msg.arg2);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        self = this;
        setContentView(R.layout.activity_recently_choosed_product_layout);
        initView();
        initData();
        setListener();
        setAddCount();
        reloadLocalData();
    }

    private void initView(){
        listView = (ListView) findViewById(R.id.recently_choosed_product_listview);
        chooseButton = (Button)findViewById(R.id.recently_choosed_choose_button);
        chooseCountTextView = (TextView)findViewById(R.id.recently_choosed_choosed_product_count);
        navigationBar.setBackBtnVisibility(View.VISIBLE);
        navigationBar.setActionBtnVisibility(View.INVISIBLE);
        navigationBar.setTitle(R.string.choose_product_recently);
    }

    private void setListener(){
        chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(addCount != 0){
                    Intent intent =new Intent(self,AddProductResultActivity.class);
                    intent.putExtra(FmConstant.PROGRAM_ID,programId);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else{
                    ToastUtil.toast(RecentlyChoosedProductActivity.this,"请选择产品");
                }
            }
        });
    }

    /**
     *
     */
    private void initData(){
        searchRequestFactory = new SearchRequestFactory(this);
        recentlyChooseProductDB = RecentlyChooseProductDB.getInstance();
        if(getIntent() != null && getIntent().hasExtra(FmConstant.PROGRAM_ID)){
            programId = getIntent().getIntExtra(FmConstant.PROGRAM_ID,0);
        }
    }

    /**
     * 读取本地已经存储的已选产品
     */
    private void reloadLocalData(){
        Thread thread =new Thread(){
            @Override
            public void run() {
                RecentlyChooseProductDB productDB =  RecentlyChooseProductDB.getInstance();
                final List<ProductBean> list  = productDB.getRecentlyChoooseProduct();
                if(null != list){
                    self.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter = new RecentlyChooseProductAdapter(self);
                            adapter.addData(list,true);
                            adapter.setHandler(handler);
                            listView.setAdapter(adapter);
                        }
                    });
                }
            }
        };
        thread.start();
    }
    /**
     *
     * @param productIds
     * @param cancel
     */
    private void chooseProduct(String productIds, final boolean cancel, final int positionInList){
        BusinessResponseHandler businessResponseHandler =
                new BusinessResponseHandler(this,true,!cancel?"产品选择中...":"产品取消选择中..."
                        ,false,true){
                    @Override
                    public void success(String response) {
                        Log.e(TAG, "----choseProduct==" + response);
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.optBoolean("success")){
                                if(cancel){
                                    ToastUtil.toast(self, "取消选中产品成功");
                                }else{
                                    ToastUtil.toast(self,"选中产品成功");
                                    saveChoosedProduct(adapter.getItem(positionInList));
                                }
                                handleProgram(!cancel);
                                adapter.setAddState(positionInList,!cancel);
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
        searchRequestFactory.chooseProduct(programId,productIds,cancel,businessResponseHandler);
    }

    /**
     *
     * @param isAdd
     */
    private void handleProgram(boolean isAdd){
        if(isAdd){
            addCount ++ ;
        }else{
            addCount --;
        }
        if(addCount <0){
            addCount = 0;
        }
        setAddCount();
    }


    /**
     * 设置已经增加了几个商品
     */
    private void setAddCount(){
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
        SpannableStringBuilder builder = new SpannableStringBuilder("您已选择了"+addCount+"件产品");
        int length = new String(addCount+"").length();
        builder.setSpan(redSpan, 5, 5+length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        chooseCountTextView.setText(builder);

        if(addCount >0){
            this.chooseButton.setBackgroundResource(R.drawable.button_enable_drawable);
            this.chooseButton.setTextColor(getResources().getColor(R.color.white));
        }else{
            this.chooseButton.setBackgroundResource(R.drawable.button_uneable_drawable);
            this.chooseButton.setTextColor(getResources().getColor(R.color.button_default_textcolor));
        }
    }

    /**
     * 保存用户已经选择过的产品
     */
    private void saveChoosedProduct(final ProductBean productBean){
        new Thread(){
            @Override
            public void run() {
                recentlyChooseProductDB.save(productBean);
            }
        }.start();
    }

}
