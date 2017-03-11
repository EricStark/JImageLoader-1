package com.jay.imageloader.cache;

import android.graphics.Bitmap;

/**
 * 图片缓存策略接口
 */

public interface JCacheStrategy {
    void put(String address, Bitmap bitmap);

    Bitmap get(String address);
}
