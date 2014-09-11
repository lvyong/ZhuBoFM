package com.zhubo.control.activity.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhubo.control.R;

/**
 * 底部Tab Bar
 * Created by andy_lv on 2014/8/23.
 */
public class BottomTabBar extends LinearLayout{

    private LinearLayout leftLayout,middleLayout,rightLayout;
    private ImageView leftImageView,middleImageView,rightImageView;
    private TextView leftTextView, middleTextView,rightTextView;

    private BottomTabBarClickCallBack bottomTabBarClickCallBack;

    public enum BottomTabBarButtonType{
        LEFT_BUTTON,MIDDLE_BUTTON,RIGHT_BUTTON
    }

    private BottomTabBarButtonType currentSelectTab;

    public interface BottomTabBarClickCallBack {
        public void  click(BottomTabBarButtonType buttonType,View view);
    }


    public BottomTabBar(Context context) {
        super(context);
        initView(context);
    }

    public BottomTabBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        setListener();
    }

    /**
     * 初始化View
     */
    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.bottom_tab_bar_layout,this);

        leftLayout   = (LinearLayout) findViewById(R.id.tab_bar_left_layout);
        middleLayout = (LinearLayout) findViewById(R.id.tab_bar_middle_layout);
        rightLayout  = (LinearLayout) findViewById(R.id.tab_bar_right_layout);

        leftImageView   = (ImageView) findViewById(R.id.tab_bar_left_imageview);
        middleImageView = (ImageView) findViewById(R.id.tab_bar_middle_imageview);
        rightImageView  = (ImageView) findViewById(R.id.tab_bar_right_imageview);

        leftTextView    = (TextView) findViewById(R.id.tab_bar_left_text);
        middleTextView  = (TextView) findViewById(R.id.tab_bar_middle_text);
        rightTextView   = (TextView) findViewById(R.id.tab_bar_right_text);
    }

    /**
     * 设置监听事件
     */
    private void setListener(){
        leftLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tabButtonClickHandle(BottomTabBarButtonType.LEFT_BUTTON,leftLayout);
            }
        });

        middleLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tabButtonClickHandle(BottomTabBarButtonType.MIDDLE_BUTTON,middleLayout);
            }
        });

        rightLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tabButtonClickHandle(BottomTabBarButtonType.RIGHT_BUTTON,rightLayout);
            }
        });
    }

    /**
     * 设置tab button click 事件回调
     * @param callBack
     */
    public void setBottomTabBarClickCallBack(BottomTabBarClickCallBack callBack){
        this.bottomTabBarClickCallBack = callBack;
    }

    /**
     * 设置左边tab imageview ,text值
     * @param imgResource
     * @param text
     */
    public void setLeftLayoutValue(int[] imgResource,String text){
        this.leftImageView.setImageResource(imgResource[0]);
        this.leftImageView.setTag(imgResource);
        this.leftTextView.setText(text);
    }

    /**
     *设置中间tab imageview ,text值
     * @param imgResource
     * @param text
     */
    public void setMiddleLayoutValue(int[] imgResource,String text){
        this.middleImageView.setImageResource(imgResource[0]);
        this.middleImageView.setTag(imgResource);
        this.middleTextView.setText(text);
    }

    /**
     * 设置右边tab imageview ,text值
     * @param imgResource
     * @param text
     */
    public void setRightLayoutValue(int[] imgResource,String text){
        this.rightImageView.setImageResource(imgResource[0]);
        this.rightImageView.setTag(imgResource);
        this.rightTextView.setText(text);
    }

    /**
     * 改变Tab 状态
     * @param lastSelectTab
     * @param type 0 表示上一次选择的tab,1表示当前选中的tab
     */
    private void changeTabState(BottomTabBarButtonType lastSelectTab,int type){
         ImageView imageView = null;
         switch (lastSelectTab){
             case LEFT_BUTTON:
                  imageView = leftImageView;
                  break;
             case MIDDLE_BUTTON:
                  imageView = middleImageView;
                  break;
             case RIGHT_BUTTON:
                  imageView = rightImageView;
                  break;
         }
         if(null != imageView){
             int[] tag = (int[]) imageView.getTag();
             if(null != tag){
                 imageView.setImageResource(tag[type]);
             }
         }
    }

    /**
     * tab button 点击事件处理
     * @param buttonType
     * @param buttonLayout
     */
    private void tabButtonClickHandle(BottomTabBarButtonType buttonType,LinearLayout buttonLayout){
        if(currentSelectTab == buttonType){
            return ;
        }
        if(null == currentSelectTab){
            currentSelectTab = buttonType;
        }
        changeTabState(currentSelectTab,0);
        currentSelectTab = buttonType;
        changeTabState(currentSelectTab,1);
        if(null != bottomTabBarClickCallBack){
            bottomTabBarClickCallBack.click(buttonType
                    ,buttonLayout);
        }
    }

    /**
     *  主动去控制某个Tab的点击
     * @param buttonType
     */
    public void clickTab(BottomTabBarButtonType buttonType){
        if(currentSelectTab == buttonType){
            return ;
        }
        LinearLayout linearLayout = null ;
        switch (buttonType){
            case LEFT_BUTTON:
                 linearLayout = leftLayout;
                 break;
            case MIDDLE_BUTTON:
                 linearLayout = middleLayout;
                 break;
            case RIGHT_BUTTON:
                 linearLayout = rightLayout;
                 break;
        }
        if(null != linearLayout){
              linearLayout.performClick();
        }
    }


}
