package com.zhubo.fm.activity.setting;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.andy.commonlibrary.net.exception.MessageException;
import com.andy.commonlibrary.util.ToastUtil;
import com.andy.corelibray.net.BusinessResponseHandler;
import com.zhubo.control.activity.BaseActivity;
import com.zhubo.fm.R;
import com.zhubo.fm.bll.request.UserReqeustFactory;

/**
 * Created by andy_lv on 2014/9/20.
 */
public class ChangePasswordActivity extends BaseActivity{

    private EditText oldPasswordEditText;
    private EditText newPasswordEditText;
    private EditText confirmEdit;
    private Button saveButton;

    private String oldPass,newPass,conPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_change_passord);
        initView();
        setListener();
    }

    private void initView(){
        navigationBar.setTitle(R.string.change_password);
        navigationBar.setBackBtnVisibility(View.VISIBLE);
        navigationBar.setActionBtnVisibility(View.INVISIBLE);
        oldPasswordEditText =(EditText)findViewById(R.id.change_password_oldpassword);
        newPasswordEditText =(EditText)findViewById(R.id.change_password_newPassword);
        confirmEdit =(EditText)findViewById(R.id.change_password_confirmPasword);
        saveButton  = (Button)findViewById(R.id.change_password_save_Button);
    }

    private void setListener(){
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(canChange()){
                    changePassword();
                }
            }
        });
    }


    private boolean canChange(){
        boolean isResult = false;
        oldPass = oldPasswordEditText.getText().toString();
        newPass  = newPasswordEditText.getText().toString();
        conPass = confirmEdit.getText().toString();
        if(TextUtils.isEmpty(oldPass)){
            ToastUtil.toast(this,"请输入原始密码");
            return isResult ;
        }

        if(TextUtils.isEmpty(newPass)){
            ToastUtil.toast(this,"请输入新密码");
            return isResult ;
        }

        if(TextUtils.isEmpty(conPass)){
            ToastUtil.toast(this,"请输入确认密码");
            return isResult ;
        }

        if(!newPass.equals(conPass)){
            ToastUtil.toast(this,"您输入的新密码与确认密码不一致");
            return isResult;
        }
        return isResult = true;
    }


    private void changePassword(){
        UserReqeustFactory userFactory = new UserReqeustFactory(this);
        userFactory.changePassword(oldPass,newPass,new BusinessResponseHandler(this,true){
            @Override
            public void success(String response) {
                super.success(response);
                ToastUtil.toast(ChangePasswordActivity.this,"您的密码修改成功");
                ChangePasswordActivity.this.finish();
            }

            @Override
            public void fail(MessageException exception) {
                super.fail(exception);
            }
        });
    }



}
