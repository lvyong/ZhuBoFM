package com.zhubo.fm.activity.live.fragement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andy.ui.libray.component.NavigationBar;
import com.zhubo.control.activity.fragement.BaseFragment;
import com.zhubo.fm.R;
import com.zhubo.fm.activity.common.EditNoteActivity;
import com.zhubo.fm.bll.common.FmConstant;

/**
 * 正在直播文稿
 * Created by andy_lv on 2014/8/30.
 */
public class LiveProgramNoteFragement extends BaseFragment {

    private View rootView;
    private TextView textView;
    private RelativeLayout empty_layout;
    private ImageView backImg;
    private TextView titleTextView;
    private TextView rightTextView;
    private int programId ;
    private NavigationBar navigationBar;
    private static boolean isStopStateHide = false;

    public static void setIsStopStateHide(boolean hide){
        isStopStateHide = hide;
    }

    @Override
    public void setNavigationBar(NavigationBar navigationBar) {
       this.navigationBar = navigationBar;
    }


    @Override
    public void onNavItemClick(NavigationBar.NavigationBarItem navBarItem) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(null == rootView){
           rootView = inflater.inflate(R.layout.fragememt_live_note,container,false);
           initView();
           initData();
           setListener();
        }else{
            ((ViewGroup)rootView.getParent()).removeView(rootView);
        }
        return rootView;
    }

    /**
     * 初始化view
     */
    private void initView(){
       textView = (TextView)rootView.findViewById(R.id.fragememnt_live_note_textview);
       empty_layout = (RelativeLayout)rootView.findViewById(R.id.fragement_live_note_empty_layout);
       backImg   = (ImageView)rootView.findViewById(R.id.fragement_live_program_note_back);
       rightTextView  = (TextView)rootView.findViewById(R.id.fragement_live_program_right_text);
       titleTextView = (TextView)rootView.findViewById(R.id.fragement_live_program_title);
    }

    /**
     * 初始化数据
     */
    private void initData(){
        titleTextView.setText(getResources().getString(R.string.live));
        Bundle bundle = getArguments();
        if(null!= bundle){
            if(bundle.containsKey(FmConstant.PROGRAM_ID)){
                programId =bundle.getInt(FmConstant.PROGRAM_ID);
            }
            if(bundle.containsKey(FmConstant.PROGRAM_NOTE)){
                String note = bundle.getString(FmConstant.PROGRAM_NOTE);
                setNote(note);
            }
        }
    }

    public void setNote(String note){
        if(!TextUtils.isEmpty(note)){
            textView.setText(note);
            empty_layout.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
            rightTextView.setText(getString(R.string.edit));
        }else{
            empty_layout.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
            rightTextView.setText(getString(R.string.write_note));
        }
    }

    private void setListener(){
        backImg.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                    getActivity().finish();
            }
        });

        rightTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                setIsStopStateHide(true);
                String note = textView.getText().toString();
                Intent intent = new Intent(getActivity(), EditNoteActivity.class);
                intent.putExtra(FmConstant.PROGRAM_NOTE,note);
                intent.putExtra(FmConstant.PROGRAM_ID,programId);
                intent.putExtra(FmConstant.FROM_PAGE,
                        LiveProgramNoteFragement.class.getSimpleName());
                getActivity().startActivityForResult(intent, FmConstant.LIVE_NOTE_FRAGEMENT);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        navigationBar.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(!isStopStateHide){
            navigationBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(navigationBar.getVisibility() == View.GONE && isStopStateHide){
            navigationBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
