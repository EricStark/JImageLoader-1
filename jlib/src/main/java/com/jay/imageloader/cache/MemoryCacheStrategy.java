package com.jay.imageloader.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.jay.utils.Md5;

/**
 * 内存缓存
 */

public class MemoryCacheStrategy implements JCacheStrategy {
    private static final MemoryCacheStrategy INSTANCE = new MemoryCacheStrategy();
    private LruCache<String, Bitmap> mCache;

    private MemoryCacheStrategy() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 5;
        mCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }

    public static MemoryCacheStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public void put(String address, Bitmap bitmap) {
        mCache.put(Md5.getMd5(address), bitmap);
    }

    @Override
    public Bitmap get(String address) {
        return mCache.get(Md5.getMd5(address));
    }
}
