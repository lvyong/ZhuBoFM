package com.andy.ui.libray.pullrefreshview;


import android.content.Context;
import android.util.AttributeSet;

import com.andy.ui.libray.waterfall.ItotemWaterView;


public class PullToRefreshItotemWaterView extends PullToRefreshBase<ItotemWaterView> {
	public static final String LOG_TAG = "dongdianzhou" + PullToRefreshItotemWaterView.class.getName();

	public PullToRefreshItotemWaterView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullToRefreshItotemWaterView(Context context, int mode) {
		super(context, mode);
	}

	public PullToRefreshItotemWaterView(Context context) {
		super(context);
	}
	
	@Override
	protected ItotemWaterView createRefreshableView(Context context,
			AttributeSet attrs) {
		ItotemWaterView itotemWaterView = new ItotemWaterView(context, attrs);
		return itotemWaterView;
	}

	@Override
	protected boolean isReadyForPullDown() {
		return mRefreshableView.getScrollY() <= 0;
	}

	@Override
	protected boolean isReadyForPullUp() {
		return mRefreshableView.getScrollY() >= (mRefreshableView.getMaxCloumnHeight() - mRefreshableView.getHeight());
	}

}
