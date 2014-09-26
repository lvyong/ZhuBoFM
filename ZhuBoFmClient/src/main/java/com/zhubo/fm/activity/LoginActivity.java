package com.zhubo.fm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.andy.commonlibrary.net.exception.MessageException;
import com.andy.commonlibrary.util.SharePreferUtil;
import com.andy.commonlibrary.util.ToastUtil;
import com.andy.corelibray.net.BusinessResponseHandler;
import com.google.gson.Gson;
import com.zhubo.control.activity.BaseActivity;
import com.zhubo.control.bussiness.bean.UserBean;
import com.zhubo.fm.R;
import com.zhubo.fm.ZhuBoApplication;
import com.zhubo.fm.activity.main.MainActivity;
import com.zhubo.fm.bll.common.FmConstant;
import com.zhubo.fm.bll.request.UserReqeustFactory;

import org.json.JSONObject;


/**
 * Created by andy_lv on 2014/9/20.
 */
public class LoginActivity extends BaseActivity {

    private final String TAG = "LoginActivity";
    private EditText loginEdit;
    private EditText passwordEdit;
    private Button loginButton;
    private String loginName;
    private String password;
    private SharePreferUtil sharePreferUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        navigationBar.setTitle(R.string.login);
        navigationBar.setBackBtnVisibility(View.INVISIBLE);
        initView();
        setListener();
        initData();
    }

    private void initView(){
       loginEdit       = (EditText)findViewById(R.id.activity_login_user_edittext);
       passwordEdit    = (EditText)findViewById(R.id.activity_login_password_edittext);
       loginButton     = (Button)findViewById(R.id.activity_login_login_button);

       loginEdit.setText("anchor");
       passwordEdit.setText("admin");
    }

    /**
     * 初始化数据
     */
    private void initData(){
        sharePreferUtil = SharePreferUtil.getInstance(this);
        loginName = sharePreferUtil.getString(FmConstant.LOGIN_ACCOUNT);
        password = sharePreferUtil.getString(FmConstant.PASSWORD);

        loginEdit.setText(loginName);
        passwordEdit.setText(password);
    }

    private void setListener(){
       loginButton.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View view) {
               if(canLogin()){
                   login();
               }
           }
       });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private boolean canLogin(){
        boolean isResult = false;
        loginName = loginEdit.getText().toString();
        password = passwordEdit.getText().toString();
        if(TextUtils.isEmpty(loginName)){
            ToastUtil.toast(this, "请输入您的账户");
        }
        if(TextUtils.isEmpty(password)) {
            ToastUtil.toast(this, "请输入您的密码");
        }

       sharePreferUtil.saveString(FmConstant.LOGIN_ACCOUNT,loginName);
       sharePreferUtil.saveString(FmConstant.PASSWORD,password);
        isResult = true;
        return isResult;
    }

    /**
     * 请求数据
     */
    private void login(){
        UserReqeustFactory userReqeustFactory  = new UserReqeustFactory(this);

        userReqeustFactory.login(loginName,password,
                new BusinessResponseHandler(LoginActivity.this,true,"登录中...") {

            @Override
            public void success(String object) {
                sharePreferUtil.saveBoolean(FmConstant.IS_LOGIN,true);
                try{
                    ZhuBoApplication application =
                            (ZhuBoApplication) LoginActivity.this.getApplication();
                    JSONObject jsonObject =new JSONObject(object);
                    String user = jsonObject.optString("user");
                    Gson gson =new Gson();
                    UserBean userBean = gson.fromJson(user, UserBean.class);
                    application.setUserBean(userBean);
                    launcher();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void fail(MessageException exception) {
                super.fail(exception);
            }
        });
    }

    /**
     *
     */
    private void launcher(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 500);
    }
}
