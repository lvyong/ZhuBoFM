package com.andy.commonlibrary.util;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * Created by andy_lv on 2014/8/28.
 */
public class AnimUtil {

    private AnimUtil(){}

    /**
     * 得到平移动画
     * @param fromX
     * @param toX
     * @param fromY
     * @param toY
     * @param duration
     * @param fillAfter
     * @return
     */
    public static TranslateAnimation getTransAnim(float fromX,float toX,float fromY,float toY,int duration,boolean fillAfter) {
        TranslateAnimation mTranslateAnimation = new TranslateAnimation(fromX, toX, fromY, toY);
        mTranslateAnimation.setInterpolator(new LinearInterpolator());
        mTranslateAnimation.setDuration(duration);
        mTranslateAnimation.setFillAfter(fillAfter);
        return mTranslateAnimation;
    }
}
