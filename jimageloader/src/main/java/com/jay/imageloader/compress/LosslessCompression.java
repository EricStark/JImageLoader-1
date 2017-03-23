package com.jay.imageloader.compress;

import android.graphics.Bitmap;

/**
 * 无损压缩策略
 */

public class LosslessCompression extends JCompressStrategy {
    private LosslessCompression() {
    }

    public static LosslessCompression getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public Bitmap compress(Bitmap bitmap, CompressOptions options) {
        return scale(bitmap, options.width, options.height);
    }

    /**
     * 按比例缩放图片
     *
     * @param bitmap    待缩放的bitmap
     * @param outWidth  输出宽度
     * @param outHeight 输出高度
     * @return 缩放后的bitmap
     */
    private Bitmap scale(Bitmap bitmap, int outWidth, int outHeight) {
        int srcWidth = bitmap.getWidth();
        int srcHeight = bitmap.getHeight();
        //参数有效性检查
        if ((outHeight >= srcHeight && outWidth >= srcWidth) || outHeight <= 0 || outWidth <= 0) {
            return bitmap;
        }
        //使图片压缩后宽高比不变
        float srcRatio = srcWidth / srcHeight;
        float outRatio = outWidth / outHeight;
        if (outRatio < srcRatio) {
            outHeight = (int) (outWidth / srcRatio);
        } else if (outRatio > srcRatio) {
            outWidth = (int) (outHeight * srcRatio);
        }
        return Bitmap.createScaledBitmap(bitmap, outWidth, outHeight, true);
    }

    private static class InstanceHolder {
        private static final LosslessCompression INSTANCE = new LosslessCompression();
    }
}
