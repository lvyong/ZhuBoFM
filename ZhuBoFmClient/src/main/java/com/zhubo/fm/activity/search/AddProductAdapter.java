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
import com.zhubo.fm.R;
import com.zhubo.fm.bll.common.FmConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy_lv on 2014/9/10.
 */
public class AddProductAdapter extends BaseAdapter{

    private Context context;
    private Handler handler;
    private boolean showAddButton ;

    private ArrayList<ProductBean> data ;


    public AddProductAdapter(Context context, boolean showAddButton){
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
    public ProductBean getItem(int i) {
        return data.get(i);
    }

    /**
     * 设置产品收藏状态
     * @param position
     * @param  isAdd
     */
    public void setAddState(int position,boolean isAdd){
        getItem(position).setAdd(isAdd);
        notifyDataSetChanged();
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
            data = new ArrayList<ProductBean>();
        }
        if(null != list && list.size() > 0){
            this.data.addAll(list);
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
        final ProductBean productBean = data.get(position);
        if(null == contentView){
            contentView = LayoutInflater.from(context).inflate(R.layout.activity_add_product_list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.programImageView = (ItotemImageView) contentView.findViewById(R.id.activity_add_product_list_item_image);
            viewHolder.programNameTextView  = (TextView)contentView.findViewById(R.id.activity_add_product_list_item_programName);
            viewHolder.priceTextView = (TextView)contentView.findViewById(R.id.activity_add_product_list_item_priceTextView);
            viewHolder.addImageView = (ImageView)contentView.findViewById(R.id.activity_add_product_list_item_add);
            viewHolder.renqiTextView = (TextView)contentView.findViewById(R.id.activity_add_product_list_item_renqi_textview);
            viewHolder.saleTextView = (TextView)contentView.findViewById(R.id.activity_add_product_list_item_sale_textview);
            viewHolder.collectTextView = (TextView)contentView.findViewById(R.id.activity_add_product_list_item_collect_textview);
            viewHolder.programDescTextView = (TextView)contentView.findViewById(R.id.activity_add_product_list_item_programDesc);
            contentView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) contentView.getTag();
        }
        viewHolder.addImageView.setVisibility(showAddButton ? View.VISIBLE : View.INVISIBLE);
        viewHolder.addImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null != handler){
                        Message msg = Message.obtain(handler, FmConstant.ADD_PRODUCT,productBean.isAdd()? 1:-1,position);
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
        return contentView;
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
    }
}
