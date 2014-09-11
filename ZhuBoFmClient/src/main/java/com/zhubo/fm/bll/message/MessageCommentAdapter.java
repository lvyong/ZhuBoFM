package com.zhubo.fm.bll.message;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhubo.fm.R;
import com.zhubo.fm.activity.messagecomment.MessageDetailActivity;

/**
 * 私信评论Adapter
 * Created by andy_lv on 2014/8/28.
 */
public class MessageCommentAdapter extends BaseAdapter {

    private Context context;

    public MessageCommentAdapter(Context context){
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
            contentView = LayoutInflater.from(context).inflate(R.layout.fragement_message_comment_message_list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.checkBox                = (CheckBox) contentView.findViewById(R.id.message_comment_checkbox);
            viewHolder.imageView               = (ImageView)contentView.findViewById(R.id.message_comment_list_item_img);
            viewHolder.dateTextView            = (TextView)contentView.findViewById(R.id.message_comment_list_item_date);
            viewHolder.listenerTextView        = (TextView)contentView.findViewById(R.id.message_comment_list_item_listener);
            viewHolder.messageContextTextView  = (TextView)contentView.findViewById(R.id.message_comment_list_item_message_context);
            contentView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)contentView.getTag();
        }
        contentView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, MessageDetailActivity.class);
                context.startActivity(intent);
            }
        });
        return contentView;
    }


    class ViewHolder{
         CheckBox  checkBox;
         ImageView imageView;
         TextView  listenerTextView;
         TextView  dateTextView;
         TextView  messageContextTextView;
    }
}
