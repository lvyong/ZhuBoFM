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
 * 音乐之声
 * Created by andy_lv on 2014/8/30.
 */
public class MusicCommentAdapter extends BaseAdapter {

    private Context context;

    public MusicCommentAdapter(Context context){
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
         ViewHolder viewHolder ;
         if(null == contentView){
            contentView = LayoutInflater.from(context).inflate(R.layout.fragement_message_music_list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.comment_img = (ImageView)contentView.findViewById(R.id.fragement_message_music_list_item_img);
            viewHolder.comment_name = (TextView)contentView.findViewById(R.id.fragement_message_music_comment_name);
            viewHolder.comment_content = (TextView)contentView.findViewById(R.id.fragement_message_music_comment_content);
            viewHolder.comment_date = (TextView)contentView.findViewById(R.id.fragement_message_music_comment_date);
            viewHolder.music_checkBox = (CheckBox)contentView.findViewById(R.id.music_comment_checkbox);
            contentView.setTag(viewHolder);
         }else{
           viewHolder = (ViewHolder) contentView.getTag();
         }
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, MessageDetailActivity.class);
                context.startActivity(intent);
            }
        });
        return contentView;

    }
    class ViewHolder{
        ImageView  comment_img;
        TextView   comment_name;
        TextView   comment_content;
        TextView   comment_date;
        CheckBox  music_checkBox;
    }

}
