package com.zhubo.fm.activity.search;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhubo.control.activity.common.ItotemImageView;
import com.zhubo.control.bussiness.bean.ProductBean;
import com.zhubo.control.bussiness.bean.ProgramBean;
import com.zhubo.fm.R;
import com.zhubo.fm.bll.common.FmConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


/**
 * Created by andy_lv on 2014/8/31.
 */
public class AddProductResultAdapter extends BaseAdapter {

    private Context context;
    private Handler handler;
    private boolean showAddButton ;

    private ArrayList<ProductBeanItem> data ;

    private SlideView mLastSlideViewWithStatusOn;

    public AddProductResultAdapter(Context context, boolean showAddButton){
        this.context = context;
        this.showAddButton = showAddButton;
    }

    public void setHandler(Handler handler){
        this.handler = handler;
    }

    @Override
    public int getCount() {
        if(null == data){
            return 0;
        }
        return data.size();
    }

    @Override
    public ProductBeanItem getItem(int i) {
        return data.get(i);
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     *
     * @param list
     * @param clear
     */
    public  void addData(List<ProductBean> list,boolean clear){
        if(clear){
            clear();
        }
        if(null == data){
            data = new ArrayList<ProductBeanItem>();
        }
        if(null != list && list.size() > 0){
            int size = list.size();
            List<ProductBeanItem> newList =new ArrayList<ProductBeanItem>();
            for(int i=0;i<size;i++){
                ProductBeanItem productBeanItem = new ProductBeanItem();
                productBeanItem.productBean = list.get(i);
                newList.add(productBeanItem);
            }
            this.data.addAll(newList);
        }
    }

    /**
     *
     */
    private void clear(){
        if(null != data){
            data.clear();
        }
    }


    @Override
    public View getView(final int position, View contentView, ViewGroup viewGroup) {
       ViewHolder viewHolder;
        SlideView slideView = (SlideView) contentView;
       final ProductBean productBean = data.get(position).productBean;
       if(null == slideView){
           View itemView = LayoutInflater.from(context).inflate(R.layout.activity_add_product_list_item,null);
           slideView = new SlideView(context);
           slideView.setContentView(itemView);
           viewHolder = new ViewHolder(slideView);
           slideView.setOnSlideListener(new SlideView.OnSlideListener() {
               @Override
               public void onSlide(View view, int status) {
                   if (mLastSlideViewWithStatusOn != null) {
                       mLastSlideViewWithStatusOn.shrink();
                   }
                   if (status == SLIDE_STATUS_ON) {
                       mLastSlideViewWithStatusOn = (SlideView)view;
                   }
                   if(status == SLIDE_STATUS_OFF){
                       mLastSlideViewWithStatusOn = null;
                   }
               }
           });
          slideView.setTag(viewHolder);
       }else{
          viewHolder = (ViewHolder) slideView.getTag();
       }
        ProductBeanItem item = data.get(position);
        item.slideView = slideView;
        item.slideView.shrink();

        viewHolder.addImageView.setVisibility(showAddButton ? View.VISIBLE : View.INVISIBLE);
        viewHolder.addImageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                 if(null != handler){
                     Message msg = Message.obtain(handler, FmConstant.ADD_PRODUCT,productBean.isAdd()? -1:1,position);
                     handler.sendMessage(msg);
                 }
           }
       });

       if(null != productBean){
           viewHolder.programNameTextView.setText(productBean.getName());
           viewHolder.programDescTextView.setText(productBean.getDescription());
           viewHolder.priceTextView.setText("¥ "+productBean.getPrice());
           viewHolder.renqiTextView.setText("人气 " +productBean.getViewCount()+"");
           viewHolder.saleTextView.setText("销量 "+productBean.getQuantity()+"");
           viewHolder.collectTextView.setText(productBean.getStarredCount()+"人收藏");
           viewHolder.addImageView.setImageResource(productBean.isAdd()?R.drawable.tap_add:
                   R.drawable.add);
           viewHolder.programImageView.setDefault(R.drawable.default_img);
           viewHolder.deleteHolder.setTag(position);
           viewHolder.deleteHolder.setOnClickListener(new View.OnClickListener(){
               @Override
               public void onClick(View view) {
                  Message msg = handler.obtainMessage(FmConstant.DELETE_PRODUCT,position,-1);
                  handler.sendMessage(msg);
               }
           });

           if (!TextUtils.isEmpty(productBean.getImageUrl())
                   && URLUtil.isHttpUrl(productBean.getImageUrl())) {
               if (viewHolder.programImageView.getTag() == null
                       || !((String) viewHolder.programImageView.getTag())
                       .equals(productBean.getImageUrl())) {
                   viewHolder.programImageView.setUrl(productBean.getImageUrl());
                   viewHolder.programImageView.setTag(productBean.getImageUrl());
                   viewHolder.programImageView.setIsLoad(false);
                   viewHolder.programImageView.reload(false);
               }
           }
       }
        if(mLastSlideViewWithStatusOn != null){
            mLastSlideViewWithStatusOn.shrink();
            mLastSlideViewWithStatusOn  = null;
        }
       return slideView;
    }


    public class ProductBeanItem{
         public   ProductBean productBean;
         public   SlideView slideView;
    }



    private class ViewHolder{
          ItotemImageView programImageView;
          TextView  programNameTextView;
          TextView  programDescTextView;
          TextView  priceTextView;
          ImageView addImageView;
          TextView  renqiTextView;
          TextView  saleTextView;
          TextView  collectTextView;
          public ViewGroup deleteHolder;

          public ViewHolder(View view){
              programImageView = (ItotemImageView) view.findViewById(R.id.activity_add_product_list_item_image);
              programNameTextView  = (TextView)view.findViewById(R.id.activity_add_product_list_item_programName);
              priceTextView = (TextView)view.findViewById(R.id.activity_add_product_list_item_priceTextView);
              addImageView = (ImageView)view.findViewById(R.id.activity_add_product_list_item_add);
              renqiTextView = (TextView)view.findViewById(R.id.activity_add_product_list_item_renqi_textview);
              saleTextView = (TextView)view.findViewById(R.id.activity_add_product_list_item_sale_textview);
              collectTextView = (TextView)view.findViewById(R.id.activity_add_product_list_item_collect_textview);
              programDescTextView = (TextView)view.findViewById(R.id.activity_add_product_list_item_programDesc);
              deleteHolder = (ViewGroup)view.findViewById(R.id.delete_holder);
          }
    }
}
