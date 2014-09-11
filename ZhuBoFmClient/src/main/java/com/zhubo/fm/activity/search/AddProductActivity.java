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
import android.widget.TextView;

import com.andy.commonlibrary.net.exception.MessageException;
import com.andy.commonlibrary.util.ToastUtil;
import com.andy.corelibray.net.BusinessResponseHandler;
import com.andy.ui.libray.common.Dimension;
import com.andy.ui.libray.pullrefreshview.PullToRefreshBase;
import com.andy.ui.libray.pullrefreshview.PullToRefreshListView;
import com.google.gson.Gson;
import com.zhubo.control.activity.BaseActivity;
import com.zhubo.control.bussiness.bean.SearchResultBean;
import com.zhubo.fm.R;
import com.zhubo.fm.bll.common.FmConstant;
import com.zhubo.fm.bll.request.SearchRequestFactory;

import org.json.JSONObject;

/**
 * 搜索到的产品，且可以添加产品
 * Created by andy_lv on 2014/8/31.
 */
public class AddProductActivity extends BaseActivity {

    private final String TAG = "AddProductActivity";
    private PullToRefreshListView listview;
    private TextView search_count_textView;
    private TextView range_up_textview;
    private TextView add_result_textview;
    private Button addButton;

    private SearchRequestFactory searchRequestFactory;

    private AddProductAdapter adapter;
    private String searchContent;
    private String type;
    private String categoryId ;

    private OrderEum currentOrder = OrderEum.DESC;

    private AddProductActivity self;
    private int currentPage = 1;
    private int programId ;
    private int addCount = 0;

    /**
     * 排序
     */
    public enum OrderEum{
         ASCE("sales"),DESC("sales!");
         private String value;

        private OrderEum(String value){
            this.value = value;
        }
        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }


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
        setContentView(R.layout.activity_add_product_layout);
        initView();
        setListener();
        setData();
        setAddCount();
        searchProduct(1);
    }

    /**
     * 初始化view
     */
    private void initView(){
       this.listview = (PullToRefreshListView)findViewById(R.id.activity_add_product_listview);
       this.search_count_textView = (TextView)findViewById(R.id.add_product_count_textview);
       this.range_up_textview = (TextView)findViewById(R.id.add_product_range_textview);
       this.add_result_textview = (TextView)findViewById(R.id.activity_add_product_choosed_product_count);
       this.addButton = (Button)findViewById(R.id.activity_add_product_choose_button);
    }

    /**
     * 设置监听事件
     */
    private void setListener(){
       addButton.setOnClickListener(this);
       range_up_textview.setOnClickListener(this);
       listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {
           @Override
           public void onPullDownToRefresh() {
               currentPage = 1;
               searchProduct(currentPage);
           }

           @Override
           public void onPullUpToRefresh() {
               searchProduct(currentPage);
           }
       });
    }

    @Override
    protected void onViewClick(View view) {
        super.onViewClick(view);
        int id = view.getId();
        if(id == R.id.activity_add_product_choose_button){
            Intent intent =new Intent(AddProductActivity.this,AddProductResultActivity.class);
            intent.putExtra(FmConstant.PROGRAM_ID,programId);
            startActivity(intent);
        }else if(id == R.id.add_product_range_textview){
            range();
        }
    }

    /**
     *
     */
    private void setData(){
        navigationBar.setBackBtnVisibility(View.VISIBLE);
        navigationBar.setActionBtnVisibility(View.INVISIBLE);
        Intent intent = getIntent();
        if(null != intent){
            String title = intent.getStringExtra(FmConstant.ADD_PRODUCT_PAGE_TITLE);
            navigationBar.setTitle(title);
            if(intent.hasExtra(FmConstant.PROGRAM_ID)){
                programId = intent.getIntExtra(FmConstant.PROGRAM_ID,0);
            }
        }

        if(getIntent() != null){
             searchContent = getIntent()!=null &&
                    getIntent().hasExtra(FmConstant.SEARCH_CONTNET)
                    ? getIntent().getStringExtra(FmConstant.SEARCH_CONTNET):"";

             type = getIntent()!=null &&
                    getIntent().hasExtra(FmConstant.SEARCH_TYPE)
                    ? getIntent().getStringExtra(FmConstant.SEARCH_TYPE):"";

             categoryId = getIntent()!=null &&
                    getIntent().hasExtra(FmConstant.CATEGORY_ID)
                    ? getIntent().getStringExtra(FmConstant.CATEGORY_ID):"";

        }

    }

    /**
     * 设置已经增加了几个商品
     */
    private void setAddCount(){
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
        SpannableStringBuilder builder = new SpannableStringBuilder("您已选择了"+addCount+"件产品");
        int length = new String(addCount+"").length();
        builder.setSpan(redSpan, 5, 5+length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        add_result_textview.setText(builder);
    }


    private void setSearchCount(){
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
        SpannableStringBuilder builder = new SpannableStringBuilder("您此次操作共搜索到"+adapter.getCount()+"件商品");
        int length = new String(adapter.getCount()+"").length();
        builder.setSpan(redSpan, 9, 9+length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        search_count_textView.setText(builder);
    }

    /**
     * 排序搜索
     */
    private void range(){
        int drawable = 0;
        if(currentOrder == OrderEum.DESC){
            drawable = R.drawable.range_up;
            currentOrder = OrderEum.ASCE;
        }else{
            drawable = R.drawable.range_up;
            currentOrder = OrderEum.DESC;
        }
        currentPage = 1;
        searchProduct(1);
        range_up_textview.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable, 0);
        range_up_textview.setCompoundDrawablePadding(Dimension.dip2px(2, this));
    }

    /**
     *
     * @param page
     */
    public void searchProduct(final int page){
        if(null == searchRequestFactory){
            searchRequestFactory = new SearchRequestFactory(this);
        }else{
            searchRequestFactory.cancle();
        }
         searchRequestFactory.searchProduct(page,type,categoryId,searchContent,currentOrder.getValue()
           ,new BusinessResponseHandler(this,true){
               @Override
               public void success(String response) {
                   Gson gson = new Gson();
                   SearchResultBean searchResultBean = gson.fromJson(response,SearchResultBean.class);
                   if(null != searchResultBean){
                       if(null == adapter){
                           adapter = new AddProductAdapter(self,true);
                           adapter.setHandler(handler);
                           listview.getAdapterView().setAdapter(adapter);
                       }
                       adapter.addData(searchResultBean.getProducts(),page == 1? true: false);
                       adapter.notifyDataSetChanged();
                       if(searchResultBean.isHasNext()){
                           currentPage = currentPage+1;
                       }else{
                           ToastUtil.toast(self, "没有更多数据");
                       }
                   }
                   listview.onRefreshComplete();
                   setSearchCount();
               }

               @Override
               public void fail(MessageException exception) {
                   super.fail(exception);
                   listview.onRefreshComplete();
               }

               @Override
               public void cancel() {
                   super.cancel();
                   listview.onRefreshComplete();
               }
           });
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
                        Log.e(TAG,"----choseProduct=="+response);
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.optBoolean("success")){
                                 if(cancel){
                                     ToastUtil.toast(self,"取消选中产品成功");
                                 }else{
                                     ToastUtil.toast(self,"选中产品成功");
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
}
