package com.zhubo.fm.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhubo.control.activity.BaseActivity;
import com.zhubo.control.activity.common.ItotemImageView;
import com.zhubo.fm.R;

/**
 * 设置
 * Created by andy_lv on 2014/8/24.
 */
public class SettingActivity extends BaseActivity {

    private ItotemImageView userPhotoImageView;
    private TextView  qianmingTextView;
    private Button newButton;
    private SetUserPhoto userPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_layout);
        initView();
        setListener();
        initData();
    }

    /**
     * 初始化view
     */
    private void initView(){
        navigationBar.setTitle(R.string.settings);
        userPhotoImageView = (ItotemImageView)findViewById(R.id.settings_photo_img);
        qianmingTextView = (TextView)findViewById(R.id.settings_qianming);
        newButton  = (Button)findViewById(R.id.activity_settings_layout_new_button);
    }

    private void setListener(){
        userPhotoImageView.setOnClickListener(this);
        findViewById(R.id.activity_settings_qianming_layout).setOnClickListener(this);
        findViewById(R.id.settings_change_password_layout).setOnClickListener(this);
        findViewById(R.id.settings_update_layout).setOnClickListener(this);
        findViewById(R.id.about_us_layout).setOnClickListener(this);
        findViewById(R.id.suggest_feedback_layout).setOnClickListener(this);
    }

    private void initData(){
         userPhoto = new SetUserPhoto(this);
    }

    @Override
    protected void onViewClick(View view) {
        super.onViewClick(view);
        switch (view.getId()){
            case R.id.activity_settings_qianming_layout:
                 startActivity(new Intent(this,SetUserInfoActivity.class));
                 break;
            case R.id.settings_change_password_layout:
                break;
            case R.id.settings_update_layout:
                break;
            case R.id.about_us_layout:
                break;
            case R.id.suggest_feedback_layout:
                break;
            case R.id.settings_photo_img:
                 userPhoto.show();
                break;
        }
    }
}
