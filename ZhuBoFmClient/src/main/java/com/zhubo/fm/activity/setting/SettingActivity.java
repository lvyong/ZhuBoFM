package com.zhubo.fm.activity.setting;

import android.os.Bundle;

import com.zhubo.control.activity.BaseActivity;
import com.zhubo.fm.R;

/**
 * 设置
 * Created by andy_lv on 2014/8/24.
 */
public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_layout);
        navigationBar.setTitle(R.string.settings);
    }
}
