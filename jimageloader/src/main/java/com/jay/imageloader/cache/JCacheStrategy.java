package com.jay.imageloader.cache;

import android.graphics.Bitmap;

import com.jay.imageloader.compress.JCompressStrategy;

/**
 * 图片缓存策略接口
 */

public interface JCacheStrategy {
    /**
     * 缓存图片
     * @param address 图片地址
     * @param bitmap 待缓存的图片
     * @param compressStrategy 压缩策略
     * @param options 压缩参数
     */
    void put(String address, Bitmap bitmap, JCompressStrategy compressStrategy,
             JCompressStrategy.CompressOptions options);

    Bitmap get(String address, JCompressStrategy.CompressOptions options);
}
