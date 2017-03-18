package com.jay.imageloader.cache;

import android.graphics.Bitmap;

import com.jay.imageloader.compress.JCompressStrategy;

/**
 * 不使用缓存
 */

public class NoneCacheStrategy implements JCacheStrategy {
    private NoneCacheStrategy() {}

    public static NoneCacheStrategy getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public void put(String address, Bitmap bitmap, JCompressStrategy compressStrategy, JCompressStrategy.CompressOptions options) {}

    @Override
    public Bitmap get(String address, JCompressStrategy.CompressOptions options) {
        return null;
    }

    private static class InstanceHolder {
        private static final NoneCacheStrategy INSTANCE = new NoneCacheStrategy();
    }
}
