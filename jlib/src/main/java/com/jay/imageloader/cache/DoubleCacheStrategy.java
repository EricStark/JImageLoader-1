package com.jay.imageloader.cache;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

/**
 * 内存与磁盘双缓存
 */

public class DoubleCacheStrategy implements JCacheStrategy {
    private static final DoubleCacheStrategy INSTANCE = new DoubleCacheStrategy();
    private static MemoryCacheStrategy sMemoryCacheStrategy = MemoryCacheStrategy.getInstance();
    private static DiskCacheStrategy sDiskCacheStrategy;

    private DoubleCacheStrategy() {}

    public static DoubleCacheStrategy getInstance(@NonNull String cacheDir) {
        if (sDiskCacheStrategy == null)
            sDiskCacheStrategy = DiskCacheStrategy.getInstance(cacheDir);
        return INSTANCE;
    }

    @Override
    public void put(String address, Bitmap bitmap) {
        sMemoryCacheStrategy.put(address, bitmap);
        sDiskCacheStrategy.put(address, bitmap);
    }

    @Override
    public Bitmap get(String address) {
        Bitmap bitmap = sMemoryCacheStrategy.get(address);
        if (bitmap == null) {
            bitmap = sDiskCacheStrategy.get(address);
            if (bitmap != null)
                sMemoryCacheStrategy.put(address, bitmap);
        }
        return bitmap;
    }
}
