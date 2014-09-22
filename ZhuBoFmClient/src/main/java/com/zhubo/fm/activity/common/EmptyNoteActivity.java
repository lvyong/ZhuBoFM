package com.zhubo.fm.activity.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhubo.fm.R;
import com.zhubo.fm.bll.common.FmConstant;

/**
 * 创建文稿空白页面
 * Created by andy_lv on 2014/9/18.
 */
public class EmptyNoteActivity extends Activity{

    private TextView textView;
    private RelativeLayout empty_layout;
    private ImageView backImg;
    private TextView titleTextView;
    private TextView rightTextView;

    private EmptyNoteActivity self;
    private int programId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        self = this;
        setContentView(R.layout.fragememt_live_note);
        initView();
        initData();
        setListener();
    }

    private void initView(){
        textView = (TextView)findViewById(R.id.fragememnt_live_note_textview);
        empty_layout = (RelativeLayout)findViewById(R.id.fragement_live_note_empty_layout);
        backImg   = (ImageView)findViewById(R.id.fragement_live_program_note_back);
        rightTextView  = (TextView)findViewById(R.id.fragement_live_program_right_text);
        titleTextView = (TextView)findViewById(R.id.fragement_live_program_title);
    }

    private void initData(){
        Intent intent = getIntent();
        if(null != intent){
            programId = intent.getIntExtra(FmConstant.PROGRAM_ID,0);
        }
        textView.setVisibility(View.GONE);
        empty_layout.setVisibility(View.VISIBLE);
        titleTextView.setText(getResources().getString(R.string.write_note));
        rightTextView.setText(getResources().getString(R.string.write_note));
    }

    /**
     *
     */
    private void setListener(){
        backImg.setOnClickListener(new View.OnClickListener(){

          @Override
          public void onClick(View view) {
              self.setResult(Activity.RESULT_CANCELED);
              self.finish();
          }
      });

      rightTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(self, EditNoteActivity.class);
                intent.putExtra(FmConstant.PROGRAM_NOTE,"");
                intent.putExtra(FmConstant.PROGRAM_ID,programId);
                self.startActivityForResult(intent, FmConstant.EMPTY_NOTE_TO_EDIT_NOTE);
                self.finish();
            }
      });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case FmConstant.EMPTY_NOTE_TO_EDIT_NOTE:
                 if(resultCode == Activity.RESULT_OK){
                     setResult(Activity.RESULT_OK);
                     finish();
                 }
        }
    }
}
