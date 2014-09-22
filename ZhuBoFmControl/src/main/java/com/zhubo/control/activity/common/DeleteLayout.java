package com.zhubo.control.activity.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zhubo.control.R;

/**
 * 删除操作布局
 * Created by andy_lv on 2014/8/27.
 */
public class DeleteLayout extends LinearLayout implements View.OnClickListener{

    private Button deleteButton,clearButton,cancleButton;

    private DeleteClickListener deleteClickListener;

    private DeleteLayout self;


    public enum ButtonType{
        DELETE_BUTTON,CLEAR_BUTTON,CANCEL_BUTTON
    }

    /**
     * 布局中button点击回调
     */
    public interface DeleteClickListener {
        public void click(ButtonType buttonType);
    }

    /**
     *  构造器
     * @param context
     */
    public DeleteLayout(Context context) {
        super(context);
    }

    /**
     * 构造器
     * @param context
     * @param attrs
     */
    public DeleteLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(isInEditMode()){
            return ;
        }
        self = this;
        initView(context);
        setListener();
    }

    /**
     * 界面初始化
     */
    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.delete_layout,this);
        deleteButton = (Button)findViewById(R.id.delete_layout_delete_button);
        clearButton  = (Button)findViewById(R.id.delete_layout_clear_button);
        cancleButton = (Button)findViewById(R.id.delete_layout_cancel_button);

    }

    /**
     * 设置监听事件
     */
    private void setListener(){
        deleteButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        cancleButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.delete_layout_cancel_button){
            if(null != deleteClickListener){
                deleteClickListener.click(ButtonType.CANCEL_BUTTON);
                changeButtonState(ButtonType.CANCEL_BUTTON);
            }
        }else if(id == R.id.delete_layout_clear_button){
            if(null != deleteClickListener){
                deleteClickListener.click(ButtonType.CLEAR_BUTTON);
                changeButtonState(ButtonType.CLEAR_BUTTON);
            }
        }else if(id == R.id.delete_layout_delete_button){
            if(null != deleteClickListener){
                deleteClickListener.click(ButtonType.DELETE_BUTTON);
                changeButtonState(ButtonType.DELETE_BUTTON);
            }
        }
    }

    /**
     * 设置实现 DeleteClickListener 接口类
     * @param deleteClickListener
     */
    public void setDeleteClickListener(DeleteClickListener deleteClickListener){
        this.deleteClickListener = deleteClickListener;
    }

    /**
     * 改变buttonType的样式
     * @param buttonType
     */
    private void changeButtonState(ButtonType buttonType){
       switch (buttonType){
           case DELETE_BUTTON:
                setButtonState(deleteButton,true);
                setButtonState(clearButton,false);
                setButtonState(cancleButton,false);
                break;
           case CANCEL_BUTTON:
               setButtonState(deleteButton,false);
               setButtonState(clearButton,false);
               setButtonState(cancleButton,true);
                break;
           case CLEAR_BUTTON:
               setButtonState(deleteButton,false);
               setButtonState(clearButton,true);
               setButtonState(cancleButton,false);
               break;
       }
    }


    private void setButtonState(Button button,boolean enable){
        if(enable){
            button.setBackgroundResource(R.drawable.button_enable_drawable);
            button.setTextColor(getResources().getColor(R.color.white));
        }else{
            button.setBackgroundResource(R.drawable.button_uneable_drawable);
            button.setTextColor(getResources().getColor(
                    R.color.delete_layout_button_normal_color));
        }
    }

}
