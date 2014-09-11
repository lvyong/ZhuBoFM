package com.zhubo.fm.activity.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.andy.commonlibrary.net.exception.MessageException;
import com.andy.corelibray.net.BusinessResponseHandler;
import com.andy.ui.libray.component.NavigationBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhubo.control.activity.BaseActivity;
import com.zhubo.control.bussiness.bean.CategoryBean;
import com.zhubo.control.bussiness.bean.ProgramBean;
import com.zhubo.fm.R;
import com.zhubo.fm.bll.common.FmConstant;
import com.zhubo.fm.bll.request.SearchRequestFactory;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * 类目浏览
 * Created by andy_lv on 2014/8/31.
 */
public class ProductCategoryActivity extends BaseActivity{

    private ListView listView;
    private ProductCategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category_layout);
        initView();
        setListener();
        initData();
    }

    private void initView(){
        navigationBar.setTitle(R.string.category_browser);
        navigationBar.setBackBtnVisibility(View.VISIBLE);
        navigationBar.setBackButtonBackground(R.drawable.back);
        listView = (ListView)findViewById(R.id.activity_product_category_layout_listview);
    }

    private void setListener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                      CategoryBean categoryBean = adapter.getItem(position);
                      String searchBox = getIntent()!=null &&
                              getIntent().hasExtra(FmConstant.SEARCH_CONTNET)
                              ? getIntent().getStringExtra(FmConstant.SEARCH_CONTNET):"";

                      String type = getIntent()!=null &&
                              getIntent().hasExtra(FmConstant.SEARCH_TYPE)
                              ? getIntent().getStringExtra(FmConstant.SEARCH_TYPE):"";


                      Intent intent = new Intent(ProductCategoryActivity.this,
                              AddProductActivity.class);
                      intent.putExtra(FmConstant.SEARCH_TYPE,type);
                      intent.putExtra(FmConstant.SEARCH_CONTNET,searchBox);
                      intent.putExtra(FmConstant.CATEGORY_ID,categoryBean.getId());
                      if(getIntent() != null && getIntent().hasExtra(FmConstant.PROGRAM_ID)){
                        intent.putExtra(FmConstant.PROGRAM_ID,
                                getIntent().getIntExtra(FmConstant.PROGRAM_ID,0));
                       }
                      intent.putExtra(FmConstant.ADD_PRODUCT_PAGE_TITLE,categoryBean.getName());
                      startActivity(intent);
            }
        });
    }

    private void initData(){
        SearchRequestFactory searchRequestFactory = new SearchRequestFactory(this);
        searchRequestFactory.getCategorys(new BusinessResponseHandler(this,true){
            @Override
            public void success(String response) {
                try{
                    if (!TextUtils.isEmpty(response)) {
                        JSONObject jsonObject = new JSONObject(response);
                        if(null != jsonObject){
                            Gson gson = new Gson();
                            ArrayList<CategoryBean> list = gson.fromJson(
                                    jsonObject.optString("categories"),
                                    new TypeToken<List<CategoryBean>>() {
                                    }.getType());
                            adapter = new
                                    ProductCategoryAdapter(ProductCategoryActivity.this,list);
                            listView.setAdapter(adapter);

                        }
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


    @Override
    public void onNavItemClick(NavigationBar.NavigationBarItem navBarItem) {
        super.onNavItemClick(navBarItem);
    }

    /**
     *
     */
    private  class ProductCategoryAdapter extends BaseAdapter{

        private Context context;

        private ArrayList<CategoryBean> dataList;

        public  ProductCategoryAdapter(Context context,ArrayList<CategoryBean> list){
            this.context = context;
            this.dataList = list;
        }

        @Override
        public int getCount() {
            if(null == dataList){
                return 0;
            }
            return dataList.size();
        }

        @Override
        public CategoryBean getItem(int position) {
            if(null == dataList){
                return null;
            }
            return dataList.get(position);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View contentView, ViewGroup viewGroup) {
           ViewHolder viewHolder;
           CategoryBean categoryBean = dataList.get(position);
           if(null == contentView){
             contentView = LayoutInflater.from(context).inflate(R.layout.product_category_list_item,null);
             viewHolder = new ViewHolder();
             viewHolder.leftTextView = (TextView)contentView.findViewById(R.id.product_category_list_item_label);
             contentView.setTag(viewHolder);
           }else{
             viewHolder = (ViewHolder) contentView.getTag();
           }
           if(null != categoryBean){
               viewHolder.leftTextView.setText(categoryBean.getName());
           }
           return contentView;
        }

        private class ViewHolder{
            TextView leftTextView;
        }
    }


}
