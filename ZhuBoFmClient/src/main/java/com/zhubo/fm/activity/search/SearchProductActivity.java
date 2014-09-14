package com.zhubo.fm.activity.search;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.andy.commonlibrary.util.ToastUtil;
import com.zhubo.control.activity.common.SearchBar;
import com.zhubo.fm.R;
import com.zhubo.fm.bll.common.FmConstant;

/**
 * 搜查产品
 * Created by andy_lv on 2014/8/31.
 */
public class SearchProductActivity extends Activity implements View.OnClickListener,SearchBar.SearchCallBack{

    private SearchBar searchBar;
    private RelativeLayout all_product_layout,choose_product_recengly,
                           popular_product_layout,collect_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_search_product);
        initView();
        setListener();
    }

    /**
     * 初始化Ui
     */
    private void initView(){
      this.searchBar = (SearchBar) findViewById(R.id.activity_search_product_search_bar);
      this.all_product_layout = (RelativeLayout)findViewById(R.id.activity_search_all_product_layout);
      this.choose_product_recengly = (RelativeLayout)findViewById(R.id.
               activity_search_choose_prouct_recently_layout);
      this.popular_product_layout = (RelativeLayout)findViewById(R.id.activity_search_popular_product_layout);
      this.collect_layout         = (RelativeLayout)findViewById(R.id.activity_search_collect_layout);
    }

    private void setListener(){
         this.all_product_layout.setOnClickListener(this);
         this.choose_product_recengly.setOnClickListener(this);
         this.collect_layout.setOnClickListener(this);
         this.popular_product_layout.setOnClickListener(this);
         this.searchBar.setSearchCallBack(this);
    }

    @Override
    public void onClick(View view) {
       int id = view.getId();
       if(id == R.id.activity_search_all_product_layout){
           goSearch("all",searchBar.getSearchContent());
       }else if(id == R.id.activity_search_choose_prouct_recently_layout){
           Intent intent =new Intent(SearchProductActivity.this,RecentlyChoosedProductActivity.class);
           if(getIntent() != null && getIntent().hasExtra(FmConstant.PROGRAM_ID)){
               intent.putExtra(FmConstant.PROGRAM_ID,getIntent().getIntExtra(FmConstant.PROGRAM_ID,0));
           }
           startActivity(intent);
       }else if(id == R.id.activity_search_popular_product_layout){
           goAddProduct("hot",searchBar.getSearchContent(),getString(R.string.popular_product));
       }else if(id == R.id.activity_search_collect_layout){

       }
    }

    @Override
    public void search(String searBox) {
        goAddProduct("all",searBox,searBox);
    }

    private void goSearch(String type,String searBox){
        Intent intent = new Intent(SearchProductActivity.this,
                ProductCategoryActivity.class);
        intent.putExtra(FmConstant.SEARCH_CONTNET,searBox);
        intent.putExtra(FmConstant.SEARCH_TYPE,type);
        if(getIntent() != null && getIntent().hasExtra(FmConstant.PROGRAM_ID)){
            intent.putExtra(FmConstant.PROGRAM_ID,getIntent().getIntExtra(FmConstant.PROGRAM_ID,0));
        }
        startActivity(intent);
    }

    /**
     *
     * @param type
     * @param searchBox
     */
    private void goAddProduct(String type,String searchBox,String title){
        Intent intent = new Intent(SearchProductActivity.this,
                AddProductActivity.class);
        intent.putExtra(FmConstant.SEARCH_CONTNET,searchBox);
        intent.putExtra(FmConstant.SEARCH_TYPE,type);
        intent.putExtra(FmConstant.ADD_PRODUCT_PAGE_TITLE,title);
        if(getIntent() != null && getIntent().hasExtra(FmConstant.PROGRAM_ID)){
            intent.putExtra(FmConstant.PROGRAM_ID,getIntent().getIntExtra(FmConstant.PROGRAM_ID,0));
        }
        startActivity(intent);
    }
}
