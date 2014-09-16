package com.zhubo.fm.activity.setting;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zhubo.fm.R;

/**
 * Created by andy_lv on 2014/9/16.
 */
public class SetUserPhoto {

    private Context context;
    private Dialog dialog ;
    private LinearLayout takePhotoLayout,openAlbumLayout;
    private Button cancelButton;
    private View rootView;

    public  SetUserPhoto(Context context){
         this.context = context;
         ceateDialog();
         setListener();
    }

    private void ceateDialog(){
      dialog = new Dialog(context,com.andy.ui.libray.R.style.dialog_normal);
      rootView = LayoutInflater.from(context).inflate(R.layout.choose_photo_dialog_layout,null);
      dialog.setContentView(rootView
              ,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
              ViewGroup.LayoutParams.MATCH_PARENT));
      takePhotoLayout = (LinearLayout) rootView.findViewById(R.id.
              choose_photo_dialog_take_photo_layout);
      openAlbumLayout = (LinearLayout)rootView.findViewById(R.id.
              choose_photo_dialog_photo_album_layout);
      cancelButton  = (Button)rootView.findViewById(R.id.choose_photo_dialog_cancel_button);

      dialog.setCanceledOnTouchOutside(true);

    }

    private void setListener(){
        takePhotoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               takePhotoLayout.setBackgroundColor(context.getResources().getColor(R.color.linear_choose_bg));
               openAlbumLayout.setBackgroundColor(context.getResources().getColor(R.color.transparent_background));
            }
        });
        openAlbumLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAlbumLayout.setBackgroundColor(context.getResources().getColor(R.color.linear_choose_bg));
                takePhotoLayout.setBackgroundColor(context.getResources().getColor(R.color.transparent_background));
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    /**
     *
     */
    public void show(){
       if(!dialog.isShowing()){
           dialog.show();
           WindowManager windowManager = ((Activity)context).getWindowManager();
           Display display = windowManager.getDefaultDisplay();
           WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
           lp.width = (int)(display.getWidth()); //设置宽度
           dialog.getWindow().setAttributes(lp);
       }
    }

    /**
     *
     */
    public void dismiss(){
        if(dialog.isShowing()){
            dialog.dismiss();;
        }
    }
}
