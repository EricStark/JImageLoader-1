package com.jay.imageloader.compress;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * 图片压缩接口
 */

public abstract class JCompressStrategy {
    /**
     * 压缩图片
     * @param bitmap 待压缩的图片
     * @param options 压缩参数
     * @return 压缩后的图片
     */
    public abstract Bitmap compress(Bitmap bitmap, CompressOptions options);

    /**
     * 压缩参数类
     */

    public static class CompressOptions {
        //压缩后的宽度、高度
        public int width = -1;
        public int height = -1;
        //图片质量
        public int quality = 100;
        public ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER_CROP;
    }
}
