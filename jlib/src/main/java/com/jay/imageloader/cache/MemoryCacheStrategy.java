package com.jay.imageloader.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * 内存缓存
 */

public class MemoryCacheStrategy implements JCacheStrategy {
    private LruCache<String, Bitmap> mCache;

    private MemoryCacheStrategy() {
        //获取最大内存
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        //计算缓存区大小
        int cacheSize = maxMemory / 8;
        mCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }

    public static MemoryCacheStrategy getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public void put(String address, Bitmap bitmap) {
        mCache.put(address, bitmap);
    }

    @Override
    public Bitmap get(String address) {
        return mCache.get(address);
    }

    private static class InstanceHolder {
        private static final MemoryCacheStrategy INSTANCE = new MemoryCacheStrategy();
    }
}
