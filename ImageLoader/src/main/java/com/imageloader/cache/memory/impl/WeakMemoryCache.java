package com.imageloader.cache.memory.impl;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import android.graphics.Bitmap;

import com.imageloader.cache.memory.BaseMemoryCache;

/**
 * Memory cache with {@linkplain java.lang.ref.WeakReference weak references} to {@linkplain android.graphics.Bitmap bitmaps}
 * 
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class WeakMemoryCache extends BaseMemoryCache<String, Bitmap> {
	@Override
	protected Reference<Bitmap> createReference(Bitmap value) {
		return new WeakReference<Bitmap>(value);
	}
}
