package com.jay.imageloader.cache;

import android.graphics.Bitmap;

import com.jay.imageloader.compress.JCompressStrategy;

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

    public void setCacheDir(String cacheDir) {
        mDiskCacheStrategy.setCacheDir(cacheDir);
    }

    @Override
    public void put(String address, Bitmap bitmap, JCompressStrategy compressStrategy, JCompressStrategy.CompressOptions options) {
        mMemoryCacheStrategy.put(address, bitmap, compressStrategy, options);
        mDiskCacheStrategy.put(address, bitmap, compressStrategy, options);
    }

    @Override
    public Bitmap get(String address, JCompressStrategy.CompressOptions options) {
        Bitmap bitmap = mMemoryCacheStrategy.get(address, options);
        if (bitmap == null) {
            bitmap = mDiskCacheStrategy.get(address, options);
//            if (bitmap != null)
//                mMemoryCacheStrategy.put(address, bitmap, NoneCompression.getInstance(), options);
        }
        return bitmap;
    }

    private static class InstanceHolder {
        private static final DoubleCacheStrategy INSTANCE = new DoubleCacheStrategy();
    }
}
