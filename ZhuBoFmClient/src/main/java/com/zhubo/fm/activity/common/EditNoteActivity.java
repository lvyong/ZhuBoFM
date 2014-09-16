package com.zhubo.fm.activity.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.andy.commonlibrary.net.exception.MessageException;
import com.andy.commonlibrary.util.AppUtil;
import com.andy.commonlibrary.util.ToastUtil;
import com.andy.corelibray.net.BusinessResponseHandler;
import com.andy.ui.libray.component.NavigationBar;
import com.zhubo.control.activity.BaseActivity;
import com.zhubo.fm.R;
import com.zhubo.fm.activity.live.fragement.LiveProgramNoteFragement;
import com.zhubo.fm.bll.common.FmConstant;
import com.zhubo.fm.bll.request.NoteRequestFactory;

import org.json.JSONObject;

/**
 * 编辑文稿
 * Created by andy_lv on 2014/8/25.
 */
public class EditNoteActivity extends BaseActivity {

    private final String TAG = "EditNoteActivity";
    private EditText editText;

    private int programId;
    private NoteRequestFactory noteRequestFactory;
    private EditNoteActivity self;
    private String previousStr = "";

    @Override
    public void onNavItemClick(NavigationBar.NavigationBarItem navBarItem) {
        if(navBarItem == NavigationBar.NavigationBarItem.action){
           if(!editText.isEnabled()){
               editText.setEnabled(true);
               navigationBar.setActionBtnText(R.string.finish);
           }else{
               sendTextToServer();
           }
        }else if(navBarItem == NavigationBar.NavigationBarItem.back){
            canBack();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        self = this;
        setContentView(R.layout.activity_edit_note);
        editText = (EditText)findViewById(R.id.edit_note_editext);
        initData();
    }

    private void initData(){
        navigationBar.setBackBtnVisibility(View.VISIBLE);

        Intent intent = getIntent();
        if(null != intent){
            navigationBar.setTitle(R.string.edit_note);
            navigationBar.setActionBtnTextColor(getResources().getColor(R.color.blue_2f89f0));
            String data = intent.getStringExtra(FmConstant.PROGRAM_NOTE);
            previousStr = data;
            Log.e(TAG,"---------note=="+data);
            if(TextUtils.isEmpty(data)){
                navigationBar.setTitle(R.string.create_note);
                navigationBar.setActionBtnText(R.string.finish);
                editText.setEnabled(true);
                editText.setFocusable(true);
                AppUtil.showSoftKeyboard(this,editText);
            }else{
                editText.setText(data);
                if(intent.hasExtra(FmConstant.FROM_PAGE) &&
                        intent.getStringExtra(FmConstant.FROM_PAGE)
                                .equals(LiveProgramNoteFragement.class.getSimpleName())){
                    editText.setEnabled(true);
                    navigationBar.setActionBtnText(R.string.finish);
                    editText.setFocusable(true);
                    AppUtil.showSoftKeyboard(this,editText);
                }else{
                    editText.setEnabled(false);
                    navigationBar.setActionBtnText(R.string.edit);
                }
            }
            if(intent.hasExtra(FmConstant.PROGRAM_ID)){
                programId = intent.getIntExtra(FmConstant.PROGRAM_ID,0);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        canBack();
    }

    /**
     * 判断是否可以退出当前Activity
     */
    private void canBack(){
        if(!editText.isInEditMode()){
            if(editText.getText().toString().equals(getIntent().getStringExtra(
                    FmConstant.PROGRAM_NOTE))){
                setResult(RESULT_CANCELED);
            }else{
                setResult(RESULT_OK);
            }
            finish();
        }else{
            ToastUtil.toast(this,R.string.finish_editing_text);
        }
    }

    /**
     * 提交编辑文稿
     */
    private void sendTextToServer(){
        String data = editText.getText().toString();
        if(!TextUtils.isEmpty(data)){
            if(data.equals(previousStr)){
                ToastUtil.toast(this,R.string.you_cannot_edit_note);
                return ;
            }
            saveNote(data);
        }else{
            ToastUtil.toast(this,R.string.please_program_note);
        }
    }


    public void saveNote(String text){
       if(noteRequestFactory == null){
           noteRequestFactory = new NoteRequestFactory(this);
       }else{
           noteRequestFactory.cancel();
       }

       noteRequestFactory.saveNote(programId+"",text,new BusinessResponseHandler(){
           @Override
           public void success(String response) {
               Log.e(TAG,"----saveNote="+response);
               try{

                   JSONObject jsonObject = new JSONObject(response);
                   if(jsonObject.optBoolean("success")){
                       ToastUtil.toast(self,"文稿上传成功");
                       setResult(Activity.RESULT_OK);
                       self.finish();
                    }
             }catch (Exception e){

               }
           }

           @Override
           public void fail(MessageException exception) {
               super.fail(exception);
           }

           @Override
           public void cancel() {
               super.cancel();
               noteRequestFactory.cancel();
           }
       });
    }
}
