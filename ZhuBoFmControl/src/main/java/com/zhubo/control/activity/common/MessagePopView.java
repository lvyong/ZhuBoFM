package com.zhubo.control.activity.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.andy.ui.libray.common.Dimension;
import com.zhubo.control.R;

/**
 * PopupView
 * Created by andy_lv on 2014/8/30.
 */
public class MessagePopView {

    private Context context;
    private String[] data;
    private PopupWindow popupWindow;
    private MessagePopViewAdapter adapter;
    private View achorView;

    private MessagePopViewCallBack messagePopViewCallBack;

    public enum PopViewEnum{
        OPEN,CLOSE
    }

    public interface MessagePopViewCallBack{
        public void click(int selectPosition,String itemData);
        public void open();
        public void close();
    }

    /**
     * 构造器
     * @param context Context
     * @param data  PopView 列表数据
     */
      public MessagePopView(Context context,View achorView,String[] data){
          this.context = context;
          this.achorView = achorView;
          this.data = data;
          initView();
      }

    /**
     *  初始化view
     */
      private void initView(){
           this.popupWindow = new PopupWindow(Dimension.dip2px(200,context),ViewGroup.LayoutParams.WRAP_CONTENT);
           View contentView = LayoutInflater.from(context).inflate(R.layout.message_popup_layout,null);
           this.popupWindow.setContentView(contentView);

           ListView listView = (ListView)contentView.findViewById(R.id.message_popup_listview) ;
           this.adapter = new MessagePopViewAdapter(this.context,this.data);
           listView.setAdapter(this.adapter);

           popupWindow.setBackgroundDrawable(new BitmapDrawable());
           popupWindow.setOutsideTouchable(true);

           this.popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
               @Override
               public void onDismiss() {
                   if(null != messagePopViewCallBack){
                       messagePopViewCallBack.close();
                   }
               }
           });
      }


    /**
     * PopView 打开关闭回调
     * @param callBack
     */
     public void setMessagePopViewCallBack(MessagePopViewCallBack callBack){
        this.messagePopViewCallBack = callBack;
     }

    /**
     *  PopupWindow 显示或者关闭
     */
     public void doAction(){
           if(popupWindow.isShowing()){
               popupWindow.dismiss();
           }else{
               this.popupWindow.showAsDropDown(achorView,Dimension.dip2px(-22,context), Dimension.dip2px(15,context));
               if(null != messagePopViewCallBack){
                   messagePopViewCallBack.open();
               }
           }
     }

    /**
     * PopupView adapter
     */
     private class MessagePopViewAdapter extends BaseAdapter{

         private Context context;
         private String[] data;
         private int selectPosition = 0;

         public MessagePopViewAdapter(Context context,String[] data){
             this.context = context;
             this.data    = data;
         }

         @Override
         public int getCount() {
            if(null == data){
                return 0;
            }
           return data.length;
         }

         @Override
         public String getItem(int position) {
           if(null == data){
               return "";
           }
           return data[position];
         }

         @Override
         public long getItemId(int i) {
             return 0;
         }

        /**
         * 设置选中位置
         * @param position
         */
         public void setSelectPosition(int position){
             this.selectPosition = position;
         }

        /**
         * 得到选中位置
         * @return
         */
         public int getSelectPosition(){
             return this.selectPosition;
         }

         @Override
         public View getView(final int position, View contentView, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if(null == contentView){
               contentView = LayoutInflater.from(context).inflate(R.layout.message_popup_item_layout,null);
               viewHolder = new ViewHolder();
               viewHolder.dotImageView = (ImageView)contentView.findViewById(R.id.message_popup_item_img);
               viewHolder.labelTextView = (TextView)contentView.findViewById(R.id.message_pop_item_textview);
               contentView.setTag(viewHolder);
            }else{
               viewHolder = (ViewHolder) contentView.getTag();
            }
            viewHolder.dotImageView.setVisibility(selectPosition == position ? View.VISIBLE : View.INVISIBLE);
            viewHolder.labelTextView.setText(data[position]);
            contentView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     if(null != messagePopViewCallBack){
                         selectPosition = position;
                         popupWindow.dismiss();
                         messagePopViewCallBack.click(position,adapter.getItem(position));
                     }
                 }
             });
            return contentView;
         }

         private class ViewHolder{
             ImageView dotImageView;
             TextView  labelTextView;
         }
     }

}
