package com.jay.imageloader.cache;

import android.graphics.Bitmap;

/**
 * 内存与磁盘双缓存
 */

public class DoubleCacheStrategy implements JCacheStrategy {
    private MemoryCacheStrategy mMemoryCacheStrategy = MemoryCacheStrategy.getInstance();
    private DiskCacheStrategy mDiskCacheStrategy = DiskCacheStrategy.getInstance();

    private DoubleCacheStrategy() {}

    public static DoubleCacheStrategy getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public void put(String address, Bitmap bitmap) {
        mMemoryCacheStrategy.put(address, bitmap);
        mDiskCacheStrategy.put(address, bitmap);
    }

    @Override
    public Bitmap get(String address) {
        Bitmap bitmap = mMemoryCacheStrategy.get(address);
        if (bitmap == null) {
            bitmap = mDiskCacheStrategy.get(address);
            if (bitmap != null)
                mMemoryCacheStrategy.put(address, bitmap);
        }
        return bitmap;
    }

    public void setCacheDir(String cacheDir) {
        mDiskCacheStrategy.setCacheDir(cacheDir);
    }

    private static class InstanceHolder {
        private static final DoubleCacheStrategy INSTANCE = new DoubleCacheStrategy();
    }
}
