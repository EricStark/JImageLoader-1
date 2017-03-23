package com.jay.imageloader;

import android.graphics.Bitmap;

/**
 * JImageLoader的回调接口
 */

public interface Callback {
    /**
     * 图片加载成功后回调此方法
     *
     * @param bitmap 加载出的图片
     */
    void success(Bitmap bitmap);

    /**
     * 图片加载失败后回调此方法
     *
     * @param e 错误原因
     */
    void fail(Exception e);
}
