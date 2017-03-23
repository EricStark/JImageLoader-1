package com.jay.imageloader.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.jay.imageloader.compress.JCompressStrategy;

/**
 * 内存缓存策略
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
    public void put(String address, Bitmap bitmap, JCompressStrategy compressStrategy,
                    JCompressStrategy.CompressOptions options) {
        mCache.put(catArgs(address, options), compressStrategy.compress(bitmap, options));
    }

    @Override
    public Bitmap get(String address, JCompressStrategy.CompressOptions options) {
        return mCache.get(catArgs(address, options));
    }

    private String catArgs(String srcAddress, JCompressStrategy.CompressOptions options) {
        return srcAddress + "?width=" + options.width + "&height="
                + options.height + "&quality=" + options.quality;
    }

    private static class InstanceHolder {
        private static final MemoryCacheStrategy INSTANCE = new MemoryCacheStrategy();
    }
}
