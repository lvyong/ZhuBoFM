package com.zhubo.fm.activity.live.fragement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
    private int programId ;

    @Override
    public void setNavigationBar(NavigationBar navigationBar) {
        navigationBar.setBackBtnVisibility(View.VISIBLE);
        navigationBar.setBackButtonBackground(R.drawable.back);
        navigationBar.setActionBtnTextColor(getResources().getColor(R.color.blue_2f89f0));
        initData();
        navigationBar.setTitle(R.string.live);
        if (TextUtils.isEmpty(textView.getText())) {
            navigationBar.setActionBtnText(R.string.write_note);
        } else {
            navigationBar.setActionBtnText(R.string.edit);
        }
        navigationBar.setActionCompoundDrawablesWithIntrinsicBounds(R.drawable.edit, 0, 0, 0);
        navigationBar.setActionBtnVisibility(View.VISIBLE);
    }


    @Override
    public void onNavItemClick(NavigationBar.NavigationBarItem navBarItem) {
       if(navBarItem == NavigationBar.NavigationBarItem.action){
          String note = textView.getText().toString();
          Intent intent = new Intent(getActivity(), EditNoteActivity.class);
          intent.putExtra(FmConstant.PROGRAM_NOTE,note);
          intent.putExtra(FmConstant.PROGRAM_ID,programId);
          intent.putExtra(FmConstant.FROM_PAGE,
                  LiveProgramNoteFragement.class.getSimpleName());
          getActivity().startActivityForResult(intent, FmConstant.LIVE_NOTE_FRAGEMENT);
       }else if(navBarItem == NavigationBar.NavigationBarItem.back){
           getActivity().finish();
       }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(null == rootView){
           rootView = inflater.inflate(R.layout.fragememt_live_note,container,false);
           initView();
           initData();
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
    }

    /**
     * 初始化数据
     */
    private void initData(){
        Bundle bundle = getArguments();
        if(null!= bundle){
            if(bundle.containsKey(FmConstant.PROGRAM_ID)){
                programId =bundle.getInt(FmConstant.PROGRAM_ID);
            }
            if(bundle.containsKey(FmConstant.PROGRAM_NOTE)){
                String note = bundle.getString(FmConstant.PROGRAM_NOTE);
                if(!TextUtils.isEmpty(note)){
                    textView.setText(note);
                    empty_layout.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                }else{
                    empty_layout.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
