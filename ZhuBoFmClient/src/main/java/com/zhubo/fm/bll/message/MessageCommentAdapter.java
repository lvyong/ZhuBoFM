package com.zhubo.fm.bll.message;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhubo.control.activity.common.ItotemImageView;
import com.zhubo.control.bussiness.bean.ColumnCommentsBean;
import com.zhubo.control.bussiness.bean.MessageBean;
import com.zhubo.control.bussiness.bean.MessageGroupBean;
import com.zhubo.fm.R;
import com.zhubo.fm.activity.messagecomment.MessageDetailActivity;
import com.zhubo.fm.bll.common.FmConstant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 私信评论Adapter
 * Created by andy_lv on 2014/8/28.
 */
public class MessageCommentAdapter extends BaseAdapter {

    private Context context;
    private LinkedList<MessageGroupBean> data ;
    private boolean isCheckBoxVisible = false;

    public MessageCommentAdapter(Context context){
        this.context = context;
    }

    /**
     * 增加数据
     * @param list
     * @param isClear
     */
    public void setData(List<MessageGroupBean> list,boolean isClear){
        if(data == null){
            data = new LinkedList<MessageGroupBean>();
        }else{
            if(isClear){
                data.clear();
            }
        }
        data.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 选择或者取消全部
     * @param isChecked
     */
    public void checkAll(boolean isChecked){
        if(null != data){
            Iterator<MessageGroupBean> iterator = data.listIterator();
            while (iterator.hasNext()){
                MessageGroupBean messageGroupBean = iterator.next();
                messageGroupBean.setChecked(isChecked);
            }
        }
    }

    /**
     *
     * @return
     */
    public String getAllCheckedIds(){
        String ids= "";
        if(null != data){
            Iterator<MessageGroupBean> iterator = data.listIterator();
            while (iterator.hasNext()){
                MessageGroupBean messageGroupBean = iterator.next();
                if(messageGroupBean.isChecked()){
                    MessageGroupBean.FromUser  fromUser= messageGroupBean.getFromUser();
                    if(null != fromUser){
                        ids +=fromUser.getId()+",";
                    }
                }
            }
        }
        if(!TextUtils.isEmpty(ids)){
            ids = ids.substring(0,ids.length()-1);
        }
        return ids;
    }

    /**
     * 显示隐藏checkbox
     * @param show
     */
    public void showCheckbox(boolean show){
        isCheckBoxVisible = show;
        checkAll(false);
    }

    @Override
    public int getCount() {
        if(null == data){
            return 0;
        }
        return data.size();
    }

    @Override
    public MessageGroupBean getItem(int i) {
        if(data == null){
            return null;
        }
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
       final MessageGroupBean messageGroupBean = data.get(position);
        if(null == contentView){
            contentView = LayoutInflater.from(context).inflate(R.layout.fragement_message_comment_message_list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.checkBox                = (CheckBox) contentView.findViewById(R.id.message_comment_checkbox);
            viewHolder.imageView               = (ItotemImageView)contentView.findViewById(R.id.message_comment_list_item_img);
            viewHolder.dateTextView            = (TextView)contentView.findViewById(R.id.message_comment_list_item_date);
            viewHolder.listenerTextView        = (TextView)contentView.findViewById(R.id.message_comment_list_item_listener);
            viewHolder.messageContextTextView  = (TextView)contentView.findViewById(R.id.message_comment_list_item_message_context);
            viewHolder.right_arrow             = (ImageView)contentView.findViewById(R.id.message_comment_list_item_arrow_img);
            contentView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)contentView.getTag();
        }
        contentView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(null != messageGroupBean){
                    long userId  = messageGroupBean.getFromUserId();
                    Intent intent =new Intent(context, MessageDetailActivity.class);
                    intent.putExtra(FmConstant.MESSAGE_FROM_SOMEBODY,userId+"");
                    context.startActivity(intent);
                }
            }
        });
        viewHolder.checkBox.setVisibility(isCheckBoxVisible?View.VISIBLE:View.GONE);
        viewHolder.right_arrow.setVisibility(isCheckBoxVisible?View.GONE:View.VISIBLE);
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                messageGroupBean.setChecked(b);
            }
        });
        if(null != messageGroupBean){
            viewHolder.checkBox.setChecked(messageGroupBean.isChecked());
            MessageGroupBean.FromUser fromUser = messageGroupBean.getFromUser();
            if(null != fromUser){
                viewHolder.imageView.setDefault(R.drawable.default_img);
                viewHolder.listenerTextView.setText(fromUser.getNickname());
                if (!TextUtils.isEmpty(fromUser.getImageUrl())
                        && URLUtil.isHttpUrl(fromUser.getImageUrl())) {
                    if (viewHolder.imageView.getTag() == null
                            || !((String) viewHolder.imageView.getTag())
                            .equals(fromUser.getImageUrl())) {
                        viewHolder.imageView.setUrl(fromUser.getImageUrl());
                        viewHolder.imageView.setTag(fromUser.getImageUrl());
                        viewHolder.imageView.setIsLoad(false);
                        viewHolder.imageView.reload(false);
                    }
                }
            }
            MessageBean  messageBean = messageGroupBean.getMessage();
            if(null != messageBean){
                viewHolder.messageContextTextView.setText(messageBean.getContent());
                String createTime = messageBean.getCreatedAt();
                if(!TextUtils.isEmpty(createTime)){
                    String[] createArray = createTime.split(" ");
                    if(null != createArray && createArray.length == 2){
                        String date = createArray[0].substring(5);
                        String time  = createArray[1].substring(0,5);
                        viewHolder.dateTextView.setText(date+" "+time);
                    }
                }
            }
        }
        return contentView;
    }


    class ViewHolder{
         CheckBox  checkBox;
         ItotemImageView imageView;
         ImageView right_arrow;
         TextView  listenerTextView;
         TextView  dateTextView;
         TextView  messageContextTextView;
    }
}
