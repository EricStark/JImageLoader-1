package com.jay.imageloader.compress;

import android.graphics.Bitmap;

/**
 * 不压缩图片
 */

public class NoneCompression extends JCompressStrategy {
    public static NoneCompression getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private NoneCompression() {}

    @Override
    public Bitmap compress(Bitmap bitmap, CompressOptions options) {
        return bitmap;
    }

    private static class InstanceHolder {
        private static final NoneCompression INSTANCE = new NoneCompression();
    }
}
