package com.zhubo.control.activity.common;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.andy.ui.libray.common.Dimension;
import com.zhubo.control.R;
import com.zhubo.control.bussiness.bean.ComlumnBean;

import java.util.ArrayList;
import java.util.List;

/**
 * PopupView
 * Created by andy_lv on 2014/8/30.
 */
public class MessagePopView {
    private final String TAG = "MessagePopView";
    private FragmentActivity context;
    private String[] data;
    private PopupWindow popupWindow;
    private MessagePopViewAdapter adapter;
    private View achorView;
    private ListView listView;

    private MessagePopViewCallBack messagePopViewCallBack;

    public enum PopViewEnum{
        OPEN,CLOSE
    }

    View.OnTouchListener popupOnTouch = new View.OnTouchListener(){

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (popupWindow != null) {
                // 关闭弹出窗口
                popupWindow.setFocusable(false);
                popupWindow.dismiss();
                return true;
            }
            return false;
        }
    };

    public interface MessagePopViewCallBack{
        public void click(int selectPosition,ComlumnBean itemData);
        public void open();
        public void close();
    }

    /**
     * 构造器
     * @param context Context
     * @param data  PopView 列表数据
     */
      public MessagePopView(FragmentActivity context,View achorView,String[] data){
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

           listView = (ListView)contentView.findViewById(R.id.message_popup_listview) ;
           adapter = new MessagePopViewAdapter(this.context);
           listView.setAdapter(this.adapter);
           listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                  if(null != messagePopViewCallBack){
                      adapter.setSelectPosition(i);
                      popupWindow.dismiss();
                      messagePopViewCallBack.click(i,adapter.getItem(i));
                  }
              }
          });

           popupWindow.setBackgroundDrawable(new BitmapDrawable());
           popupWindow.setOutsideTouchable(true);
           popupWindow.setFocusable(true);

           this.popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
               @Override
               public void onDismiss() {
                   if(null != messagePopViewCallBack){
                       messagePopViewCallBack.close();
                   }
               }
           });
          popupWindow.getContentView().setOnTouchListener(popupOnTouch);
      }

    /**
     * 加载数据
     * @param dataList
     */
      public void setData(List<ComlumnBean> dataList){
          this.adapter.setData(dataList);
          this.popupWindow.update();
          this.listView.performItemClick(null,0,0);
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
               DisplayMetrics dm = new DisplayMetrics();
               context.getWindowManager().getDefaultDisplay().getMetrics(dm);
               int screenWidth = dm.widthPixels;
               int screenHeigh = dm.heightPixels;
               int x = (screenWidth - achorView.getWidth()) / 4;
               this.popupWindow.showAsDropDown(achorView,-x, Dimension.dip2px(15,context));
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
         private List<ComlumnBean> data;
         private int selectPosition = 0;

         public MessagePopViewAdapter(Context context){
             this.context = context;
         }

        /**
         * 设置数据
         * @param dataList
         */
         public void setData(List<ComlumnBean> dataList){
           if(null == data){
               data = new ArrayList<ComlumnBean>();
           }else{
               data.clear();
           }
           if(null != dataList){
              ComlumnBean messageComlumn = new ComlumnBean();
              messageComlumn.setName("私信");
              data.add(messageComlumn);
              data.addAll(dataList);
              notifyDataSetChanged();
           }
         }

         @Override
         public int getCount() {
            if(null == data){
                return 0;
            }
           return data.size();
         }

         @Override
         public ComlumnBean getItem(int position) {
           if(null == data){
               return null;
           }
           return data.get(position);
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
            viewHolder.labelTextView.setText(data.get(position).getName());
            return contentView;
         }

         private class ViewHolder{
             ImageView dotImageView;
             TextView  labelTextView;
         }
     }

}
