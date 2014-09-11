package com.zhubo.control.activity.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andy.ui.libray.common.Dimension;
import com.zhubo.control.R;

/**
 * 左边Imagezview 右边TextView ,成蓝色
 * Created by andy_lv on 2014/8/24.
 */
public class LabelTextView extends LinearLayout {

    private TextView textView;
    private ImageView imageView;

    private int imageSrc;
    private float textSize;
    private int textColor;
    private String textValue;

    public LabelTextView(Context context) {
        super(context);
    }

    public LabelTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) {
            return;
        }
        initAttrs(context,attrs);
        initView();
    }

    /**
     * 初始化属性值
     *
     * @param context 上下文
     * @param attrs   属性
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        //获取xml中配置的属性资源
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LabelTextView);
        try {
            imageSrc        = typedArray.getResourceId(R.styleable.LabelTextView_rightImgSrc, 0);
            textSize        = typedArray.getDimension(R.styleable.LabelTextView_labeltextSize, 0.0f);
            textColor       = typedArray.getColor(R.styleable.LabelTextView_color, 0);
            textValue       = typedArray.getString(R.styleable.LabelTextView_textString);
        } finally {
            typedArray.recycle();
        }
    }

    /**
     * 初始化Ui
     */
    private void initView(){
        LayoutInflater.from(getContext()).inflate(R.layout.control_label_textview_layout, this, true);
        imageView = (ImageView) findViewById(R.id.labeltext_imageview);
        textView  = (TextView) findViewById(R.id.labeltext_textview);

        imageView.setImageResource(imageSrc);

        //字体大小
        if (Float.compare(textSize, 0.0f) > 0) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
        textView.setText(textValue);
        textView.setTextColor(textColor);
    }


    public void setText(String textValue){
        textView.setText(textValue);
    }

    public void  setImageView(int drawable){
        imageView.setImageResource(drawable);
    }

}
