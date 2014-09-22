package com.zhubo.fm.activity.setting;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.andy.commonlibrary.util.SharePreferUtil;
import com.andy.commonlibrary.util.ToastUtil;
import com.andy.corelibray.ui.common.DialogController;
import com.andy.ui.libray.dialog.AlertDialog;
import com.google.gson.Gson;
import com.zhubo.control.activity.BaseActivity;
import com.zhubo.control.activity.common.ItotemImageView;
import com.zhubo.control.bussiness.bean.UserBean;
import com.zhubo.fm.R;
import com.zhubo.fm.ZhuBoApplication;
import com.zhubo.fm.activity.LoginActivity;
import com.zhubo.fm.bll.common.FmConstant;
import com.zhubo.fm.bll.util.UploadPhoto;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import static com.andy.ui.libray.dialog.AlertDialog.Builder.AlertDialogClickListener;

/**
 * 设置
 * Created by andy_lv on 2014/8/24.
 */
public class SettingActivity extends BaseActivity implements SetUserPhoto.UserPhotoCallBack{

    private final String TAG = "SettingActivity";
    private ItotemImageView userPhotoImageView;
    private TextView  qianmingTextView;
    private Button newButton;
    private SetUserPhoto userPhoto;
    private SharePreferUtil sharePreferUtil;

    private final int START_SET_USER_ACTIVITY_REQUESTCODE = 1000;
    private final int OPEN_ALBUM = 2000;
    private final int TAKE_PHOTO = 3000;
    private final int UPLOAD_IMG = 4000;
    private final int PHOTO_RESOULT = 5000;

    private String photo_path = "";


    private Handler handler  =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case UPLOAD_IMG:
                    if(msg.arg1 == 1){
                        ZhuBoApplication zhuBoApplication = ZhuBoApplication.getInstance();
                        String iamgeUrl = zhuBoApplication.getUserBean().getImageUrl();
                        userPhotoImageView.recycle(true);
                        userPhotoImageView.setUrl("http://api.mallfm.bjcathay.com"+iamgeUrl);
                        userPhotoImageView.reload(false);
                        ToastUtil.toast(SettingActivity.this,"头像设置成功");
                    }else if(msg.arg1 == 0){
                        ToastUtil.toast(SettingActivity.this,"头像设置失败");
                    }
                    break;
            }
        }
    };


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
        findViewById(R.id.activity_settings_unlogin_button).setOnClickListener(this);
    }

    private void initData(){
         userPhoto = new SetUserPhoto(this);
         userPhoto.setSetUserPhotoCallBack(this);
         sharePreferUtil = SharePreferUtil.getInstance(this);

         UserBean userBean = ZhuBoApplication.getInstance().getUserBean();
         if(null != userBean){
             qianmingTextView.setText(userBean.getSignature());
             userPhotoImageView.setUrl("http://api.mallfm.bjcathay.com"+userBean.getImageUrl());
             userPhotoImageView.reload(false);
         }
    }

    @Override
    protected void onViewClick(View view) {
        super.onViewClick(view);
        switch (view.getId()){
            case R.id.activity_settings_qianming_layout:
                 Intent intent =new Intent(this,SetUserInfoActivity.class);
                 startActivityForResult(intent,START_SET_USER_ACTIVITY_REQUESTCODE);
                 break;
            case R.id.settings_change_password_layout:
                startActivity(new Intent(this,ChangePasswordActivity.class));
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
           case R.id.activity_settings_unlogin_button:
                unlogin();
                break;
        }
    }

    /**
     * 退出登陆
     */
    private void unlogin(){
        DialogController.getInstance().showAlertDialog(this,"提示","您确定要退出吗?","取消","确定",
                new AlertDialogClickListener(){

                    @Override
                    public void clickCallBack(AlertDialog.Builder.ButtonTypeEnum typeEnum, AlertDialog alertDialog) {
                        if(typeEnum == AlertDialog.Builder.ButtonTypeEnum.LEFT_BUTTON){
                           alertDialog.dismiss();
                        }else if(typeEnum == AlertDialog.Builder.ButtonTypeEnum.RIGHT_BUTTON){
                            alertDialog.dismiss();
                            sendBroadcast(new Intent(FmConstant.UNLOGIN_ACTION));
                            sharePreferUtil.saveBoolean(FmConstant.IS_LOGIN,false);
                            Intent intent =new Intent(SettingActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

    }

    @Override
    public void takePhoto() {
        userPhoto.dismiss();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment
                .getExternalStorageDirectory(), "temp.jpg")));
        startActivityForResult(intent, TAKE_PHOTO);
    }

    @Override
    public void openAlbum() {
        //打开相册
        try{
            userPhoto.dismiss();
            Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT);
            innerIntent.setType("image/*");
            Intent wrapperIntent = Intent.createChooser(innerIntent, "选择二维码图片");
            this.startActivityForResult(wrapperIntent, OPEN_ALBUM);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 收缩图片
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 400);
        intent.putExtra("outputY",400);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_RESOULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case START_SET_USER_ACTIVITY_REQUESTCODE:
                if(resultCode == Activity.RESULT_OK){
                    UserBean userBean = ZhuBoApplication.getInstance().getUserBean();
                    if(null != userBean){
                        qianmingTextView.setText(userBean.getSignature());
                    }
                }
                break;
            case OPEN_ALBUM:
                readAlbumPhoto(data);
                break;
            case TAKE_PHOTO:
                // 设置文件保存路径
                Bitmap bitmap  =  decodeFile(Environment.getExternalStorageDirectory()
                        + "/temp.jpg");
                uploadPhoto(bitmap);
                 break;
            case PHOTO_RESOULT:
                if(null == data){
                    return ;
                }
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    uploadPhoto(photo);
                }
                break;
        }
    }


    public void readAlbumPhoto(Intent data){
        if(null == data){
            return ;
        }
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor= this.managedQuery(data.getData(),proj,null,null,null);
        if(cursor != null){
            if (cursor.moveToFirst()) {
                photo_path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor = null ;
        }
        Bitmap bitmap  =  decodeFile(photo_path);
        uploadPhoto(bitmap);
    }

    /**
     * 解析图片
     * @param path
     * @return
     */
    private Bitmap decodeFile(String path){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 先获取原大小
        Bitmap scanBitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false; // 获取新的大小
        int sampleSize = (int) (options.outHeight / (float) 200);
        if (sampleSize <= 0)
            sampleSize = 1;
        options.inSampleSize = sampleSize;
        options.outHeight = 400;
        options.outWidth  = 400;
        scanBitmap = BitmapFactory.decodeFile(path, options);
        return scanBitmap;
    }

    private void uploadPhoto(final  Bitmap bitmap){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                String result=  UploadPhoto.postFile("http://api.mallfm.bjcathay.com/api/user/set_avatar",
                        null,baos.toByteArray());
                try{
                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.optBoolean("success")){
                        Log.e(TAG,"---------上传图片=="+result);
                        Gson gson = new Gson();
                       final UserBean userBean = gson.fromJson(jsonObject.optString("user"),UserBean.class);
                        ZhuBoApplication.getInstance().getUserBean().setImageUrl(userBean.getImageUrl());
                         Message message =new Message();
                         message.arg1 = 1;
                         message.what = UPLOAD_IMG;
                         handler.sendMessage(message);
                    }else{
                        Message message =new Message();
                        message.arg1 = 0;
                        message.what = UPLOAD_IMG;
                        handler.sendMessage(message);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }}).start();
    }
}
