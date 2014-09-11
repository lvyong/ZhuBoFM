package com.imageloader.cache.disc.impl;

import java.io.File;

import com.imageloader.cache.disc.BaseDiscCache;
import com.imageloader.cache.disc.DiscCacheAware;
import com.imageloader.cache.disc.naming.FileNameGenerator;
import com.imageloader.cache.disc.naming.HashCodeFileNameGenerator;

/**
 * Default implementation of {@linkplain DiscCacheAware disc cache}. Cache size is unlimited.
 * 
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 * @see BaseDiscCache
 */
public class UnlimitedDiscCache extends BaseDiscCache {
	/**
	 * @param cacheDir
	 *            Directory for file caching
	 */
	public UnlimitedDiscCache(File cacheDir) {
		this(cacheDir, new HashCodeFileNameGenerator());
	}

	/**
	 * @param cacheDir
	 *            Directory for file caching
	 * @param fileNameGenerator
	 *            Name generator for cached files
	 */
	public UnlimitedDiscCache(File cacheDir, FileNameGenerator fileNameGenerator) {
		super(cacheDir, fileNameGenerator);
	}

	@Override
	public void put(String key, File file) {
		// Do nothing
	}
}
