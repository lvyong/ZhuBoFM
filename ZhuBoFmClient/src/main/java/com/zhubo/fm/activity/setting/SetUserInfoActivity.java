package com.zhubo.fm.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.andy.commonlibrary.util.ToastUtil;
import com.andy.ui.libray.component.NavigationBar;
import com.zhubo.control.activity.BaseActivity;
import com.zhubo.fm.R;
import com.zhubo.fm.bll.common.FmConstant;

/**
 * 设置用户签名
 * Created by andy_lv on 2014/9/16.
 */
public class SetUserInfoActivity extends BaseActivity{

    private EditText userInfoEdit;
    private TextView countTextView;
    private String previousStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setuserinfo_layout);
        initView();
        initData();
        setListener();
    }

    /**
     * 初始化view
     */
    private void initView(){
       this.userInfoEdit = (EditText) findViewById(
               R.id.activity_setuserinfo_editext);
       this.countTextView = (TextView)findViewById(
               R.id.activity_setuserinfo_count_textview);

        navigationBar.setBackBtnVisibility(View.VISIBLE);
        navigationBar.setTitle(R.string.qianming);
        navigationBar.setActionBtnText(R.string.finish);
        navigationBar.setActionBtnTextColor(getResources().getColor(R.color.blue_2f89f0));
    }

    /**
     * 初始化数据
     */
    private void initData(){
      Intent intent = getIntent();
      if(null != intent && intent.hasExtra(FmConstant.USER_QIANMING)){
          previousStr = intent.getStringExtra(FmConstant.USER_QIANMING);
          userInfoEdit.setText(previousStr);
          countTextView.setText(previousStr.length()+"/24");
      }
    }

    /**
     * 设置监听事件
     */
    private void setListener(){
        userInfoEdit.addTextChangedListener(new TextWatcher() {
            private int selectionStart;
            private int selectionEnd;
            private CharSequence temp;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                temp = charSequence;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                countTextView.setText(editable.length()+"/24");
                selectionStart = userInfoEdit.getSelectionStart();
                selectionEnd = userInfoEdit.getSelectionEnd();
                if (temp.length() > 24) {
                    editable.delete(selectionStart - 1, selectionEnd);
                    userInfoEdit.setText(editable);
                    int tempSelection = userInfoEdit.getSelectionEnd();
                    // 设置光标在最后
                    userInfoEdit.setSelection(tempSelection);
                }
            }
        });
    }


    @Override
    public void onNavItemClick(NavigationBar.NavigationBarItem navBarItem) {
        super.onNavItemClick(navBarItem);
        if(navBarItem == NavigationBar.NavigationBarItem.action){
            String text = userInfoEdit.getText().toString();
            if(TextUtils.isEmpty(text)){
                ToastUtil.toast(this,"请输入签名内容");
            }else if(text.equals(previousStr)){
                ToastUtil.toast(this,"您没有修改签名内容");
            }else{

            }
        }
    }
}
