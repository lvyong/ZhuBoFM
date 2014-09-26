package com.zhubo.control.activity.common;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.andy.commonlibrary.util.AppUtil;
import com.andy.commonlibrary.util.ToastUtil;
import com.imageloader.core.ImageLoader;
import com.imageloader.core.assist.FailReason;
import com.imageloader.core.assist.ImageLoadingListener;

import com.zhubo.control.R;

public class ItotemImageView extends ImageView {
	private static final String TAG="ItotemImageView";
	private String url;
	private com.imageloader.core.DisplayImageOptions options;
	private boolean isLoad;
	private Bitmap bitmap;
	private int backResouceBg; 
	
    private boolean isLoadBgInRecyle=false;
    
    private int default_drawalbe;

    private boolean isRoundedCorner = false;
    private boolean isCircle  = false;

	public ItotemImageView(Context context) {
		super(context);
		init();
	}

	public ItotemImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public ItotemImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void init() {
		options = new com.imageloader.core.DisplayImageOptions.Builder()
				// 缓存到sd卡
				.cacheOnDisc() 
				//缓存到内存，慎用，如果手动控制bitmap回收，会出问题
				.cacheInMemory() 
				.build();
		default_drawalbe = R.drawable.empty;
		
	}

	public void setDefault(int resId) {
		default_drawalbe = resId;
	}

	
	public void setIsisLoadBgInRecyle(boolean isLoadBgInRecyle){
		this.isLoadBgInRecyle = isLoadBgInRecyle;
	}

    public void setRoundedCorner(boolean isRoundedCorner){
        this.isRoundedCorner = isRoundedCorner;
    }

    /**
     *
     * @param isCircle
     */
    public void setIsCircle(boolean isCircle){
        this.isCircle = isCircle;
    }
	
	@Override
	public void setBackgroundDrawable(Drawable d) {
		super.setBackgroundDrawable(d);
	}
	
	@Override
	public void setBackgroundResource(int resid) {
		super.setBackgroundResource(resid);
		backResouceBg = resid;
	}
	
	public void setUrl(String imageUrl) {
		url = imageUrl;
	}
	
	public String getUrl(){
		return url;
	}

	
	@Override
	public void setImageBitmap(Bitmap bm) {
		if (bm != null) {
			bitmap = bm;
		}
		super.setImageBitmap(bitmap);
	}
	
	
	public Bitmap getImageBitmap(){
		return this.bitmap;
	}
	
	public void setIsLoad(boolean isLoad){
		this.isLoad = isLoad;
	}
	
	
	public void recycle(boolean showDefault) {
		ImageLoader.getInstance().cancelDisplayTask(this);
		if(isLoadBgInRecyle){
			setBackgroundResource(backResouceBg);
		}
		
		isLoad = false;
	}

	public void reload(final boolean loadBig) {
		if (!isLoad) {
			setImageResource(default_drawalbe);
		    if(!TextUtils.isEmpty(url)){
		    	isLoad = true;
			      ImageLoader.getInstance().displayImage(url, this, options, new ImageLoadingListener() {

						@Override
						public void onLoadingStarted() {
						}

						@Override
						public void onLoadingFailed(FailReason failReason) {

						}

						@Override
						public void onLoadingComplete(Bitmap loadedImage) {
							if(isRoundedCorner){
                                setImageBitmap(AppUtil.getRoundedCornerBitmap(loadedImage,0.3f));
                            }
                            if(isCircle){
                                setImageBitmap(AppUtil.getCircleBitmap(loadedImage));
                            }
						}
						@Override
						public void onLoadingCancelled() {

						}
					});
			}
		}
	}
}
