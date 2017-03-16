package com.jay.imageloader.cache;

import android.graphics.Bitmap;

/**
 * 不使用缓存
 */

public class NoneCacheStrategy implements JCacheStrategy {
    private NoneCacheStrategy() {}

    public static NoneCacheStrategy getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public void put(String address, Bitmap bitmap) {}

    @Override
    public Bitmap get(String address) {
        return null;
    }

    private static class InstanceHolder {
        private static final NoneCacheStrategy INSTANCE = new NoneCacheStrategy();
    }
}
