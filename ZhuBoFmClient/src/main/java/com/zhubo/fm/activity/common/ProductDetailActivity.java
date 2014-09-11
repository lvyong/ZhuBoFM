package com.zhubo.fm.activity.common;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.andy.ui.libray.component.NavigationBar;
import com.zhubo.control.activity.BaseActivity;
import com.zhubo.fm.R;

/**
 * 产品明细
 * Created by andy_lv on 2014/8/26.
 */
public class ProductDetailActivity extends BaseActivity {

     private TextView programNameTextView;
     private TextView priceTextView;
     private TextView timeTextView;
     private TextView countTextView;
     private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        navigationBar.setBackBtnVisibility(View.VISIBLE);
        navigationBar.setTitle(R.string.product_detail);
        initView();
        loadData();
    }

    /**
     * 初始化view
     */
    private void initView(){
        this.programNameTextView = (TextView) findViewById(R.id.product_detail_name_textview);
        this.priceTextView       = (TextView) findViewById(R.id.product_detail_price_textview);
        this.timeTextView        = (TextView) findViewById(R.id.product_detail_date_textview);
        this.countTextView       = (TextView) findViewById(R.id.product_detail_sale_textview);
        this.listView            = (ListView) findViewById(R.id.product_detail_listview);
    }

    /**'
     * 加载数据
     */
    private void loadData(){
     this.listView.setAdapter(new ProductDetailAdapter(this));
    }

    @Override
    public void onNavItemClick(NavigationBar.NavigationBarItem navBarItem) {
        if(navBarItem == NavigationBar.NavigationBarItem.back){
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /**
     * adapter
     */
    private class ProductDetailAdapter extends BaseAdapter{

        private Context context ;

        public ProductDetailAdapter(Context context){
            this.context = context;
        }


        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View contentView, ViewGroup viewGroup) {
           ViewHolder viewHolder;
           if(null == contentView){
               contentView = LayoutInflater.from(context).inflate(R.layout.product_detai_list_item,null);
               viewHolder = new ViewHolder();
               viewHolder.programNameTextView = (TextView)contentView.findViewById(R.id.product_detail_list_item_product_name);
               viewHolder.timeTextView  = (TextView)contentView.findViewById(R.id.product_detail_list_item_product_time);
               viewHolder.countTextView = (TextView)contentView.findViewById(R.id.product_detail_list_item_product_count);
               contentView.setTag(viewHolder);
           }else{
               viewHolder = (ViewHolder)contentView.getTag();
           }
           return contentView;
        }


        class ViewHolder{
            TextView programNameTextView;
            TextView timeTextView;
            TextView countTextView;
        }

    }
}
