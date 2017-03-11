package com.jay.imageloader.cache;

import android.graphics.Bitmap;

/**
 * 不使用缓存
 */

public class NoneCacheStrategy implements JCacheStrategy {
    private static final NoneCacheStrategy INSTANCE = new NoneCacheStrategy();

    private NoneCacheStrategy() {
    }

    public static NoneCacheStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public void put(String address, Bitmap bitmap) {

    }

    @Override
    public Bitmap get(String address) {
        return null;
    }
}
