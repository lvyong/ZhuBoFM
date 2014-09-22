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
import com.zhubo.control.bussiness.bean.MessageGroupBean;
import com.zhubo.fm.R;
import com.zhubo.fm.activity.messagecomment.MessageDetailActivity;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 音乐之声
 * Created by andy_lv on 2014/8/30.
 */
public class MusicCommentAdapter extends BaseAdapter {

    private final String TAG = "MusicCommentAdapter";
    private Context context;
    private LinkedList<ColumnCommentsBean> data ;
    private boolean isCheckBoxVisible = false;


    public MusicCommentAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
       if(null == data){
           return 0;
       }
       return data.size();
    }

    /**
     * 增加数据
     * @param list
     * @param isClear
     */
    public void setData(List<ColumnCommentsBean> list,boolean isClear){
        if(data == null){
            data = new LinkedList<ColumnCommentsBean>();
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
            Iterator<ColumnCommentsBean> iterator = data.listIterator();
            while (iterator.hasNext()){
                ColumnCommentsBean columnCommentsBean = iterator.next();
                columnCommentsBean.setChecked(isChecked);
            }
            notifyDataSetChanged();
        }
    }

    /**
     *
     * @return
     */
    public String getAllCheckedIds(){
        String ids= "";
        if(null != data){
            Iterator<ColumnCommentsBean> iterator = data.listIterator();
            while (iterator.hasNext()){
                ColumnCommentsBean columnCommentsBean = iterator.next();
                if(columnCommentsBean.isChecked()){
                    ColumnCommentsBean.ColumnUser user = columnCommentsBean.getUser();
                    if(null != user){
                        ids +=user.getId()+",";
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
    public Object getItem(int position) {
        if(data == null){
            return null;
        }
        return data.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private String formateDate(String date){
        String result = "";
        String[] dateArray = date.split(" ");
        if(dateArray.length == 2){
            String split1 = dateArray[0];
            String split2 = dateArray[1];
            if(split1.contains("-")){
                String[] array = split1.split("-");
                if(null != array && array.length == 3){
                    int month = Integer.parseInt(array[1],10);
                    int day = Integer.parseInt(array[2],10);
                    result  = array[0]+"年"+month+"月"+day+"日";
                }
                result = result +" "+ split2.substring(0,5);
            }
        }
        return  result;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup viewGroup) {
        Log.i(TAG,"------------positon=="+position);
         ViewHolder viewHolder ;
         final  ColumnCommentsBean columnCommentsBean = data.get(position);
         if(null == contentView){
            contentView = LayoutInflater.from(context).inflate(R.layout.fragement_message_music_list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.comment_img = (ItotemImageView)contentView.findViewById(R.id.fragement_message_music_list_item_img);
            viewHolder.comment_name = (TextView)contentView.findViewById(R.id.fragement_message_music_comment_name);
            viewHolder.comment_content = (TextView)contentView.findViewById(R.id.fragement_message_music_comment_content);
            viewHolder.comment_date = (TextView)contentView.findViewById(R.id.fragement_message_music_comment_date);
            viewHolder.music_checkBox = (CheckBox)contentView.findViewById(R.id.music_comment_checkbox);
            contentView.setTag(viewHolder);
         }else{
           viewHolder = (ViewHolder) contentView.getTag();
         }
        viewHolder.music_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                columnCommentsBean.setChecked(b);
            }
        });
        viewHolder.music_checkBox.setVisibility(isCheckBoxVisible?View.VISIBLE:View.GONE);
        if(null != columnCommentsBean){
            viewHolder.music_checkBox.setChecked(columnCommentsBean.isChecked());
            ColumnCommentsBean.ColumnUser user = columnCommentsBean.getUser();
            if(null != user){
                viewHolder.comment_name.setText(user.getNickname());
            }
            String body = columnCommentsBean.getBody();
            if(!TextUtils.isEmpty(body)){
                viewHolder.comment_content.setText(body);
            }
            String crateTime = columnCommentsBean.getCreatedAt();
            viewHolder.comment_date.setText(formateDate(crateTime));
            ColumnCommentsBean.ColumnContent content = columnCommentsBean.getContent();
            if(null != content) {
                viewHolder.comment_img.setDefault(R.drawable.default_img);
                if (!TextUtils.isEmpty(content.getImageUrl())
                        && URLUtil.isHttpUrl(content.getImageUrl())) {
                    if (viewHolder.comment_img.getTag() == null
                            || !((String) viewHolder.comment_img.getTag())
                            .equals(content.getImageUrl())) {
                        viewHolder.comment_img.setUrl(content.getImageUrl());
                        viewHolder.comment_img.setTag(content.getImageUrl());
                        viewHolder.comment_img.setIsLoad(false);
                        viewHolder.comment_img.reload(false);
                    }
                }
            }
        }

        return contentView;

    }
    class ViewHolder{
        ItotemImageView comment_img;
        TextView   comment_name;
        TextView   comment_content;
        TextView   comment_date;
        CheckBox  music_checkBox;
    }

}
