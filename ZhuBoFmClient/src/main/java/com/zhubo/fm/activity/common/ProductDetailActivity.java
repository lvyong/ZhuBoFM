package com.zhubo.fm.activity.common;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.andy.commonlibrary.net.exception.MessageException;
import com.andy.commonlibrary.util.DateUtil;
import com.andy.commonlibrary.util.StringUtil;
import com.andy.corelibray.net.BusinessResponseHandler;
import com.andy.ui.libray.component.NavigationBar;
import com.google.gson.Gson;
import com.zhubo.control.activity.BaseActivity;
import com.zhubo.control.activity.common.ItotemImageView;
import com.zhubo.control.bussiness.bean.ProductBean;
import com.zhubo.control.bussiness.bean.ProductDetailBean;
import com.zhubo.control.bussiness.bean.SaleDetailBean;
import com.zhubo.fm.R;
import com.zhubo.fm.bll.common.FmConstant;
import com.zhubo.fm.bll.request.ProductSaleFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品明细
 * Created by andy_lv on 2014/8/26.
 */
public class ProductDetailActivity extends BaseActivity {

     private final  String TAG = "ProductDetailActivity";
     private ItotemImageView productImage;
     private TextView programNameTextView;
     private TextView priceTextView;
     private TextView chooseDateTextView;
     private TextView saleCountTextView;
     private TextView saleSumTextView;
     private ListView listView;

    private  String startDate = "";
    private  String endDate  = "";
    private ProductDetailActivity self;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        self = this;
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
        this.productImage  = (ItotemImageView)findViewById(R.id.product_detail_img);
        this.programNameTextView = (TextView) findViewById(R.id.product_detail_name_textview);
        this.priceTextView       = (TextView) findViewById(R.id.product_detail_price_textview);
        this.chooseDateTextView  = (TextView) findViewById(R.id.
                activity_product_detail_choose_date_textview);
        this.saleCountTextView       = (TextView) findViewById(
                R.id.fragement_program_sale_top_left_textview);
        this.saleSumTextView   = (TextView)findViewById(R.id.
                fragement_program_sale_top_right_textview);
        this.listView          = (ListView) findViewById(R.id.product_detail_listview);

        this.productImage.setImageResource(R.drawable.default_img);
    }

    /**'
     * 加载数据
     */
    private void loadData(){
        Intent intent = getIntent();
        if(getIntent() != null){
            String productId = intent.getStringExtra(FmConstant.PRODUCT_ID);
            startDate = intent.getStringExtra(FmConstant.START_DATE);
            endDate   = intent.getStringExtra(FmConstant.END_DATE);
            ProductSaleFactory saleFactory = new ProductSaleFactory(this);
            saleFactory.getProductDetail(productId,startDate,endDate,
                    new BusinessResponseHandler(){
                        @Override
                        public void success(String response) {
                            Log.e(TAG,"---------response=="+response);
                            Gson gson = new Gson();
                            ProductDetailBean productDetailBean = gson.fromJson(response,ProductDetailBean.class);
                            if(null != productDetailBean){
                                setProductDetail(productDetailBean.getProduct());
                                ProductDetailAdapter adapter = new ProductDetailAdapter(self);
                                adapter.addData(productDetailBean.getContents(),true);
                                listView.setAdapter(adapter);
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
    }

    /**
     *
     * @param productBean
     */
    private void setProductDetail(ProductBean productBean){
          if(null != productBean){
              productImage.setDefault(R.drawable.default_img);
              productImage.setUrl(productBean.getImageUrl());
              productImage.reload(false);
              programNameTextView.setText(productBean.getName());
              priceTextView.setText("¥ "+productBean.getPrice());

              chooseDateTextView.setText(startDate.replaceAll("-",".")+"-"+endDate.replaceAll("-","."));
              setTopLableValue(productBean.getQuantity(),productBean.getAmount());
          }
    }

    private void setTopLableValue(int totalProductCount,String totalSale){
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
        SpannableStringBuilder builder = new SpannableStringBuilder(getDays()+"天,共销售"+
                totalProductCount);
        int length = new String(totalProductCount+"").length();
        int firstLength = new String(getDays()+"天,共销售").length();
        builder.setSpan(redSpan, firstLength, firstLength+length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        saleCountTextView.setText(builder);

        SpannableStringBuilder builder1 = new SpannableStringBuilder("合计:¥ "+
                StringUtil.formatAmount(totalSale + ""));
        int length1 =StringUtil.formatAmount(totalSale+"").length();
        builder1.setSpan(redSpan, 3,5+length1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        saleSumTextView.setText(builder1);
    }


    private long getDays(){
        long day = 0;
        long sartMiliseconds = DateUtil.getMiliseconds(startDate, "yyyy-MM-dd");
        long endMiliseconds  = DateUtil.getMiliseconds(endDate,"yyyy-MM-dd");
        long cha = endMiliseconds - sartMiliseconds;
        if(cha ==0){
            day = 1;
        }else{
            day = cha/1000/60/60/24;
        }
        return day;
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
        private ArrayList<SaleDetailBean> data = null;

        public ProductDetailAdapter(Context context){
            this.context = context;
        }

        @Override
        public int getCount() {
           if(null == data){
               return 0;
           }
           return data.size();
        }

        @Override
        public Object getItem(int i) {
            if(null == data){
                return null;
            }
            return data.get(i);
        }
        /**
         *
         * @param list
         * @param clear
         */
        public  void addData(List<SaleDetailBean> list,boolean clear){
            if(clear){
                clear();
            }
            if(null == data){
                data = new ArrayList<SaleDetailBean>();
            }
            if(null != list && list.size() > 0){
                this.data.addAll(list);
            }
        }

        private void clear(){
            if(null != data){
                data.clear();
            }
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View contentView, ViewGroup viewGroup) {
           ViewHolder viewHolder;
           SaleDetailBean saleDetailBean = data.get(position);
           if(null == contentView){
               contentView = LayoutInflater.from(context).inflate(R.layout.product_detai_list_item,null);
               viewHolder = new ViewHolder();
               viewHolder.programNameTextView = (TextView)contentView.findViewById(R.id.product_detail_list_item_top_textview);
               viewHolder.timeTextView  = (TextView)contentView.findViewById(R.id.product_detail_list_item_product_time);
               viewHolder.quantityTextView = (TextView)contentView.findViewById(R.id.product_detail_list_item_product_quantity);
               viewHolder.saleTextView = (TextView)contentView.findViewById(R.id.product_detail_list_item_product_sale);
               contentView.setTag(viewHolder);
           }else{
               viewHolder = (ViewHolder)contentView.getTag();
           }
           if(null != saleDetailBean){
               viewHolder.programNameTextView.setText(saleDetailBean.getName());
               viewHolder.timeTextView.setText(saleDetailBean.getTime());
               viewHolder.quantityTextView.setText("销量:"+saleDetailBean.getQuantity()+"");
               viewHolder.saleTextView.setText("营业额：¥ "+saleDetailBean.getAmount()+"");
           }
           return contentView;
        }


        class ViewHolder{
            TextView programNameTextView;
            TextView timeTextView;
            TextView quantityTextView;
            TextView saleTextView;

        }

    }
}
