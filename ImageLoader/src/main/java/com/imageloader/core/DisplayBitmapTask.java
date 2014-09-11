package com.imageloader.core;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.imageloader.core.assist.ImageLoadingListener;
import com.imageloader.utils.BitmapUtil;
import com.imageloader.utils.ThumbnailUtils;

/**
 * Displays bitmap in {@link ImageView}. Must be called on UI thread.
 * 
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 * @see ImageLoadingListener
 */
final class DisplayBitmapTask implements Runnable {

	private final Bitmap bitmap;
	private final ImageView imageView;
	private final boolean compress;
	private final boolean stroke;
	private final int strokeColor;
	private final int strokeWidth;
	private final ImageLoadingListener listener;
	private final int strokeCorner;
	
	//FIXME 这个类加了一个压缩参数，传true的时候会将Bitmap压缩成ImageView的大小，在调用ImageView的setImageBitmap方法

	public DisplayBitmapTask(Bitmap bitmap, ImageLoadingInfo imageLoadingInfo) {
		this.bitmap = bitmap;
//		this.imageLoadingInfo = imageLoadingInfo;
		imageView = imageLoadingInfo.imageView;
		listener = imageLoadingInfo.listener;
		compress = imageLoadingInfo.options.isCompress();
		stroke = imageLoadingInfo.options.isStroke();
		strokeColor = imageLoadingInfo.options.getStrokeColor();
		strokeWidth = imageLoadingInfo.options.getStrokeWidth();
		strokeCorner= imageLoadingInfo.options.getStrokeCorner();
		
	}

	public void run() {
		Bitmap thumb;
		if(compress){
			Log.i("DisplayBitmapTask------------", "------runing ===---compress--------");
			thumb = ThumbnailUtils.extractThumbnail(bitmap, imageView.getWidth()<=0?100:imageView.getWidth(), imageView.getHeight()<=0?100:imageView.getWidth());
		}else{
			Log.i("DisplayBitmapTask------------", "------runing ===compress---false--------");
			thumb = bitmap;
		}
		
//		bitmap.recycle();
		
		if(stroke){
			Log.i("DisplayBitmapTask------------", "------runing ===compress---stroke--------");
			thumb = BitmapUtil.getStrokeCornerBitmap(thumb, strokeCorner, strokeColor, strokeWidth);
		}
		
//		if(compress){
//			Log.i("liaowenxin","imageView ----------width:"+imageView.getWidth()+",height:"+imageView.getHeight());
//			Bitmap thumb = ThumbnailUtils.extractThumbnail(bitmap, imageView.getWidth()<=0?100:imageView.getWidth(), imageView.getHeight()<=0?100:imageView.getWidth());
//		}else{
//			imageView.setImageBitmap(bitmap);
//			listener.onLoadingComplete(bitmap);
//		}
		imageView.setImageBitmap(thumb);
		listener.onLoadingComplete(thumb);
		
	}
}
