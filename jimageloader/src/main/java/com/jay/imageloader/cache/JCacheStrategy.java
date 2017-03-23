package com.jay.imageloader.cache;

import android.graphics.Bitmap;

import com.jay.imageloader.compress.JCompressStrategy;

/**
 * 图片缓存策略接口
 */

public interface JCacheStrategy {
    /**
     * 将图片放入缓存
     *
     * @param address          图片加载地址
     * @param bitmap           待缓存的图片
     * @param compressStrategy 图片压缩策略
     * @param options          图片压缩参数
     */
    void put(String address, Bitmap bitmap, JCompressStrategy compressStrategy,
             JCompressStrategy.CompressOptions options);

    /**
     * 从缓存中获取图片
     *
     * @param address 图片加载地址
     * @param options 图片压缩参数
     * @return 缓存的图片，没有则返回null
     */
    Bitmap get(String address, JCompressStrategy.CompressOptions options);
}
