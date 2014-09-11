package com.zhubo.control.activity.common;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhubo.control.R;

/**
 * 搜索框
 * Created by andy_lv on 2014/8/31.
 */
public class SearchBar extends RelativeLayout implements View.OnClickListener{

    private ImageView backImage;
    private TextView searButton;
    private EditText searchEdit;

    public SearchCallBack searchCallBack;

    private Activity activity;

    public interface  SearchCallBack {
        public void search(String searBox);

    }

    /**
     * 构造器
     * @param context
     */
    public SearchBar(Context context) {
        super(context);
    }

    /**
     * 构造器
     * @param context
     * @param attrs
     */
    public SearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(isInEditMode()){
            return ;
        }
        this.activity = (Activity) context;
        initView(context);
        setListener();
    }

    /**
     *
     * @param context
     */
    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.search_bar_layout,this);
        this.backImage  = (ImageView)findViewById(R.id.serchar_bar_back_button);
        this.searButton = (TextView)findViewById(R.id.search_bar_search_button);
        this.searchEdit = (EditText)findViewById(R.id.search_bar_search_box);
    }

    /**
     * 设置监听事件
     */
    private void setListener(){
       this.backImage.setOnClickListener(this);
       this.searButton.setOnClickListener(this);
       this.searchEdit.setOnEditorActionListener( new TextView.OnEditorActionListener() {
           @Override
           public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
               if(actionId == EditorInfo.IME_ACTION_SEARCH){
                   handleSearch();
                   return true;
               }
               return false;
           }
       });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.serchar_bar_back_button){
            activity.finish();
        }else if(id == R.id.search_bar_search_button){
            handleSearch();
        }
    }

    /**
     *  设置搜索回调
     * @param searchCallBack
     */
    public void setSearchCallBack(SearchCallBack searchCallBack){
        this.searchCallBack = searchCallBack;
    }

    private void  handleSearch(){
        String inputContent = searchEdit.getText().toString();
        if(!TextUtils.isEmpty(inputContent)){
            if(null != searchCallBack){
                searchCallBack.search(searchEdit.getText().toString());
            }
        }
    }

    public String getSearchContent(){
        return searchEdit.getText().toString();
    }
}
