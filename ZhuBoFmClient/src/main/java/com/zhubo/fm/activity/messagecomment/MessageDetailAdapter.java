package com.zhubo.fm.activity.messagecomment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.andy.commonlibrary.util.AppUtil;
import com.zhubo.control.activity.common.ItotemImageView;
import com.zhubo.fm.R;

/**
 * Created by andy_lv on 2014/9/9.
 */
public class MessageDetailAdapter extends BaseAdapter{

    private Context context;

    private final int other_type = 0;
    private final int me_type = 1;


    public MessageDetailAdapter(Context context){
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

    // 每个convert view都会调用此方法，获得当前所需要的view样式
    @Override
    public int getItemViewType(int position) {
        int p = position%2;
        if(p ==0){
            return me_type;
        }
        return other_type;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup viewGroup) {
        OtherViewHolder otherViewHolder = null;
        MeViewHolder    meViewHolder = null;
        int type = getItemViewType(position);
        if(null == contentView){
            LayoutInflater inflater = LayoutInflater.from(context);
            switch (type){
                case other_type:
                    contentView = inflater.inflate(R.layout.message_come_other_layout,null);
                    otherViewHolder = new OtherViewHolder();
                    otherViewHolder.otherImageView = (ItotemImageView)
                            contentView.findViewById(R.id.message_come_other_imageview);
                    otherViewHolder.otherNameTextView = (TextView)contentView.findViewById(R.id.message_come_other_peopleName);
                    otherViewHolder.otherTimeTextView = (TextView)contentView.findViewById(R.id.message_come_other_time);
                    otherViewHolder.otherMessageTextView = (TextView)contentView.findViewById(R.id.message_come_other_textview);
                    contentView.setTag(otherViewHolder);
                    break;
                case me_type:
                    contentView = inflater.inflate(R.layout.message_come_me_layout,null);
                    meViewHolder = new MeViewHolder();
                    meViewHolder.meImageView = (ItotemImageView)
                            contentView.findViewById(R.id.message_come_me_imageview);
                    meViewHolder.meNameTextView = (TextView)contentView.findViewById(R.id.message_come_me_peopleName);
                    meViewHolder.meTimeTextView = (TextView)contentView.findViewById(R.id.message_come_me_time);
                    meViewHolder.meMessageTextView = (TextView)contentView.findViewById(R.id.message_come_me_textview);
                    contentView.setTag(meViewHolder);
            }
        }else {
            switch (type){
                case other_type:
                    otherViewHolder = (OtherViewHolder) contentView.getTag();
                    break;
                case me_type:
                    meViewHolder = (MeViewHolder) contentView.getTag();
                    break;
            }
        }

        switch (type){
            case other_type:
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.meimei);
                otherViewHolder.otherImageView.setImageBitmap(AppUtil.getRoundedCornerBitmap(bitmap, 3.0f));
                otherViewHolder.otherMessageTextView.setText("我来啦，我来啦，我来啦，我来啦，我来啦，我来啦，" +
                        "我来啦，我来啦，我来啦，我来啦，我来啦，");
                otherViewHolder.otherTimeTextView.setText("09.09 22:00");
                otherViewHolder.otherNameTextView.setText("张三");
                break;
            case me_type:
                Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(),R.drawable.meimei);
                meViewHolder.meImageView.setImageBitmap(AppUtil.getRoundedCornerBitmap(bitmap1,3.0f));
                meViewHolder.meMessageTextView.setText("我来啦，我来啦，我来啦，我来啦，我来啦，我来啦，" +
                        "我来啦，我来啦，我来啦，我来啦，我来啦，");
                meViewHolder.meTimeTextView.setText("09.09 22:14");
                meViewHolder.meNameTextView.setText("张五");
                break;
        }

        return contentView;
    }

    class OtherViewHolder{
        ItotemImageView otherImageView;
        TextView otherNameTextView;
        TextView otherTimeTextView;
        TextView otherMessageTextView;
    }

    class MeViewHolder{
        ItotemImageView meImageView;
        TextView  meNameTextView;
        TextView  meTimeTextView;
        TextView  meMessageTextView;
    }
}
