package com.jay.imageloader;

import android.graphics.Bitmap;

/**
 * JImageLoader的回调接口
 */

public interface Callback {
    void success(Bitmap bitmap);

    void fail(Exception e);
}
