package com.zhubo.fm.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.andy.commonlibrary.net.exception.MessageException;
import com.andy.commonlibrary.util.FileUtil;
import com.andy.corelibray.CoreApplication;
import com.andy.corelibray.net.BusinessResponseHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhubo.control.bussiness.bean.AuthToken;
import com.zhubo.control.bussiness.bean.ComlumnBean;
import com.zhubo.control.bussiness.db.AuthTokenDB;
import com.zhubo.fm.R;
import com.zhubo.fm.activity.main.MainActivity;
import com.zhubo.fm.bll.common.FmConstant;
import com.zhubo.fm.bll.request.PrivateMessageRequest;
import com.zhubo.fm.bll.request.UserReqeustFactory;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 欢迎页面
 * Created by andy_lv on 2014/9/2.
 */
public class SplashActivity extends Activity {

    private final String TAG = "SplashActivity";

    private boolean againAuth = false;

    private int uuid ;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_layout);

        AuthTokenDB authTokenDB =  AuthTokenDB.getInstance();
        String apiToken = authTokenDB.getApiToken();
        ((CoreApplication)getApplication()).setApiToken(apiToken);
        Log.e(TAG,"-------db--apiToken="+apiToken);
       /* if(!TextUtils.isEmpty(apiToken)){
            launcher();
        }else{
            getToken();
        }*/
    }


    /**
     *
     */
    private void getToken(){
        /*Random random = new Random(1000);
        int temp ;
        do{
            temp  = random.nextInt(10000);
        }while(uuid == temp);*/
        uuid = 111333;
        ((CoreApplication) getApplication()).setUuid(uuid+"");
        UserReqeustFactory userReqeustFactory  = new UserReqeustFactory(this);
        userReqeustFactory.getToken(new BusinessResponseHandler() {
            @Override
            public void start() {

            }

            @Override
            public void success(String object) {
                Log.e(TAG,"-----------获取的数据=="+object);
                try{
                    JSONObject jsonObject = new JSONObject(object);
                    Gson gson = new Gson();
                    AuthToken authToken = gson.fromJson(jsonObject.optString("user"),AuthToken.class);

                    AuthTokenDB authTokenDB =  AuthTokenDB.getInstance();
                    authTokenDB.save(authToken);

                    String apiToken = authTokenDB.getApiToken();
                    Log.e(TAG,"-------apiToken="+apiToken);
                    ((CoreApplication)getApplication()).setApiToken(apiToken);

                    // login();
                }catch (Exception e){

                }
            }

            @Override
            public void fail(MessageException exception) {
                if(!againAuth){
                    getToken();
                    againAuth = true;
                }
            }
        });
    }




}
