package com.zhubo.fm.activity.realtimeinteract;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.zhubo.control.activity.common.ItotemImageView;
import com.zhubo.fm.R;

/**
 * Created by andy_lv on 2014/9/9.
 */
public class RealTimeViewPageAdapter extends PagerAdapter {
    private Activity context;

    private int[] image_array = {R.drawable.ic_launcher, R.drawable.ic_launcher,R.drawable.ic_launcher};

    public RealTimeViewPageAdapter(Activity context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return image_array.length;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        if(object != null){
            ItemView view = (ItemView)object;
            ((ViewPager)container).removeView(view);
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (ItemView)object;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ItemView itemView =new ItemView(context);
        itemView.setSrcImage(image_array[position]);
        ((ViewPager) container).addView(itemView);
        return itemView;
    }

    /**
     * item view
     */
    class ItemView extends FrameLayout {
        ItotemImageView imageView;
        Activity context;

        public ItemView(Context context) {
            super(context);
            this.context = (Activity) context;
            initView();
        }

        private void initView() {
            LayoutInflater.from(context).inflate(
                    R.layout.activity_real_time_interact_viewpage_item_layout, this);
            imageView = (ItotemImageView) findViewById(
                    R.id.activity_real_time_interact_viewpage_itme_imageview);
        }

        private void setSrcImage(int src_id) {
            imageView.setImageResource(src_id);
        }
    }
}
