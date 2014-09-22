package com.zhubo.fm.activity.messagecomment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.andy.commonlibrary.util.AppUtil;
import com.zhubo.control.activity.common.ItotemImageView;
import com.zhubo.control.bussiness.bean.MessageBean;
import com.zhubo.control.bussiness.bean.MessageGroupBean;
import com.zhubo.control.bussiness.bean.UserBean;
import com.zhubo.fm.R;
import com.zhubo.fm.ZhuBoApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy_lv on 2014/9/9.
 */
public class MessageDetailAdapter extends BaseAdapter{

    private Context context;

    private final int other_type = 0;
    private final int me_type = 1;

    public UserBean myUserBean;

    private MessageGroupBean.FromUser fromUser;

    private ArrayList<MessageBean> data ;


    public MessageDetailAdapter(Context context){
        this.context = context;
        myUserBean = ZhuBoApplication.getInstance().getUserBean();
    }

    public void setOtherUser(MessageGroupBean.FromUser fromUser){
        this.fromUser = fromUser;
    }

    /**
     * 设置数据
     * @param list
     * @param isClear
     */
    public void setData(List<MessageBean> list,boolean isClear){
        if(null == data){
            data = new ArrayList<MessageBean>();
        }else{
            if(isClear){
                data.clear();
            }
        }
        this.data.addAll(list);
    }

    @Override
    public int getCount() {
        if(null == data){
            return 0;
        }
        return data.size();
    }

    @Override
    public MessageBean getItem(int i) {
        if(null == data){
            return  null;
        }
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
      return 0;
    }

    private String formageDate(String createTime){
        String result = "";
        if(!TextUtils.isEmpty(createTime)){
            String[] createArray = createTime.split(" ");
            if(null != createArray && createArray.length == 2){
                String date = createArray[0].substring(5);
                String time  = createArray[1].substring(0,5);
                result = date+" "+time;
            }
        }
        return result;
    }

    // 每个convert view都会调用此方法，获得当前所需要的view样式
    @Override
    public int getItemViewType(int position) {
        MessageBean messageBean = getItem(position);
        if(messageBean.getFromUserId() == 1){
            return me_type;
        }else{
            return other_type;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup viewGroup) {
        OtherViewHolder otherViewHolder = null;
        MeViewHolder    meViewHolder = null;
        MessageBean messageBean = getItem(position);
        int type = getItemViewType(position);
        if(null == contentView){
            LayoutInflater inflater = LayoutInflater.from(context);
            switch (type){
                case other_type:
                    contentView = inflater.inflate(R.layout.message_come_other_layout,null);
                    otherViewHolder = new OtherViewHolder();
                    otherViewHolder.otherImageView = (ItotemImageView)
                            contentView.findViewById(R.id.message_come_other_imageview);
                    otherViewHolder.otherImageView.setRoundedCorner(true);
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
                    meViewHolder.meImageView.setRoundedCorner(true);
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
                if(null != messageBean){
                    otherViewHolder.otherImageView.setDefault(R.drawable.default_img);
                    if(null != fromUser){
                        if (!TextUtils.isEmpty(fromUser.getImageUrl())
                                && URLUtil.isHttpUrl(fromUser.getImageUrl())) {
                            if (otherViewHolder.otherImageView.getTag() == null
                                    || !((String) otherViewHolder.otherImageView.getTag())
                                    .equals(fromUser.getImageUrl())) {
                                otherViewHolder.otherImageView.setUrl(fromUser.getImageUrl());
                                otherViewHolder.otherImageView.setTag(fromUser.getImageUrl());
                                otherViewHolder.otherImageView.setIsLoad(false);
                                otherViewHolder.otherImageView.reload(false);
                            }
                        }
                    }

                    String startTime = messageBean.getCreatedAt();
                    if(!TextUtils.isEmpty(startTime)){
                        otherViewHolder.otherTimeTextView.setText(formageDate(startTime));
                    }
                    String content = messageBean.getContent();
                    if(!TextUtils.isEmpty(content)){
                        otherViewHolder.otherMessageTextView.setText(content);
                    }
                    if(null != fromUser){
                        otherViewHolder.otherNameTextView.setText(fromUser.getNickname());
                    }
                }
                break;
            case me_type:
                meViewHolder.meImageView.setDefault(R.drawable.default_img);
                if(null != myUserBean){
                    if (!TextUtils.isEmpty(myUserBean.getImageUrl())
                            && URLUtil.isHttpUrl(myUserBean.getImageUrl())) {
                        if ( meViewHolder.meImageView.getTag() == null
                                || !((String) meViewHolder.meImageView.getTag())
                                .equals(myUserBean.getImageUrl())) {
                            meViewHolder.meImageView.setUrl(fromUser.getImageUrl());
                            meViewHolder.meImageView.setTag(fromUser.getImageUrl());
                            meViewHolder.meImageView.setIsLoad(false);
                            meViewHolder.meImageView.reload(false);
                        }
                    }
                }
                String startTime = messageBean.getCreatedAt();
                if(!TextUtils.isEmpty(startTime)){
                    meViewHolder.meTimeTextView.setText(formageDate(startTime));
                }
                String content = messageBean.getContent();
                if(!TextUtils.isEmpty(content)){
                    meViewHolder.meMessageTextView.setText(content);
                }

                if(null != myUserBean){
                    meViewHolder.meNameTextView.setText(myUserBean.getNickname());
                }
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
