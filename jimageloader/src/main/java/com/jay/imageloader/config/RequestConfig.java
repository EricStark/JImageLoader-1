package com.jay.imageloader.config;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.jay.imageloader.cache.DiskCacheStrategy;
import com.jay.imageloader.cache.DoubleCacheStrategy;
import com.jay.imageloader.cache.JCacheStrategy;
import com.jay.imageloader.compress.JCompressStrategy;
import com.jay.imageloader.compress.LosslessCompression;

import java.io.File;
import java.net.URL;

/**
 * 参数设置类
 */

public class RequestConfig {
    private Context mContext;
    //加载地址
    private String mAddress;
    private JCacheStrategy mCacheStrategy;
    private JCompressStrategy mCompressStrategy;
    private JCompressStrategy.CompressOptions mCompressOptions;
    //图片未加载（加载失败）时的占位图
    private Drawable mPlaceHolder;
    private Drawable mErrorHolder;

    private RequestConfig() {
    }

    public Context getContext() {
        return mContext;
    }

    public Drawable getErrorHolder() {
        return mErrorHolder;
    }

    public Drawable getPlaceHolder() {
        return mPlaceHolder;
    }

    public String getAddress() {
        return mAddress;
    }

    public JCacheStrategy getCacheStrategy() {
        return mCacheStrategy;
    }

    public JCompressStrategy.CompressOptions getCompressOptions() {
        return mCompressOptions;
    }

    public JCompressStrategy getCompressStrategy() {
        return mCompressStrategy;
    }

    public static class Builder {
        private RequestConfig mConfig = new RequestConfig();
        //缓存目录
        private String mCacheDir;

        public Builder(@NonNull Context context) {
            mConfig.mContext = context;
            mConfig.mCompressOptions = new JCompressStrategy.CompressOptions();
        }

        /**
         * 构建RequestConfig类对象
         *
         * @return RequestConfig类对象
         */
        public RequestConfig build() {
            checkConfig();
            return mConfig;
        }

        /**
         * 检测参数是否合理性，对不合理的参数进行默认处理
         */
        private void checkConfig() {
            JCacheStrategy cacheStrategy = mConfig.getCacheStrategy();
            if (cacheStrategy == null)
                mConfig.mCacheStrategy = DoubleCacheStrategy.getInstance();
            JCompressStrategy compressStrategy = mConfig.getCompressStrategy();
            if (compressStrategy == null)
                mConfig.mCompressStrategy = LosslessCompression.getInstance();
            //对磁盘或双缓存设置缓存目录
            if (cacheStrategy instanceof DiskCacheStrategy) {
                if (mCacheDir == null)
                    mCacheDir = mConfig.mContext.getExternalCacheDir().getPath();
                ((DiskCacheStrategy) cacheStrategy).setCacheDir(mCacheDir);
            } else if (cacheStrategy instanceof DoubleCacheStrategy) {
                if (mCacheDir == null)
                    mCacheDir = mConfig.mContext.getExternalCacheDir().getPath();
                ((DoubleCacheStrategy) cacheStrategy).setCacheDir(mCacheDir);
            }
        }

        /**
         * 设置图片加载错误时的占位图
         *
         * @param drawable 图片加载错误时的占位图
         */
        public Builder error(Drawable drawable) {
            mConfig.mErrorHolder = drawable;
            return this;
        }

        /**
         * 设置图片加载错误时的占位图
         *
         * @param id 图片加载错误时的占位图的资源ID
         */
        public Builder error(@DrawableRes int id) {
            error(mConfig.mContext.getResources().getDrawable(id));
            return this;
        }

        /**
         * 设置图片未加载完成时的占位图
         *
         * @param drawable 图片未加载完成时的占位图
         */
        public Builder placeHolder(Drawable drawable) {
            mConfig.mPlaceHolder = drawable;
            return this;
        }

        /**
         * 设置图片未加载完成时的占位图
         *
         * @param id 图片未加载完成时的占位图的资源ID
         */
        public Builder placeHolder(@DrawableRes int id) {
            placeHolder(mConfig.mContext.getResources().getDrawable(id));
            return this;
        }

        /**
         * 设置图片的缓存策略
         * 提供了四种缓存策略，分别为：
         * NoneCacheStrategy（不使用缓存）；
         * MemoryCacheStrategy（内存缓存）；
         * DiskCacheStrategy（SD卡缓存）；
         * DoubleCacheStrategy（内存与SD卡双缓存）【默认】。
         * 如果以上需求不能满足需求，可通过实现JCacheStrategy接口自定义缓存策略
         *
         * @param cacheStrategy 缓存策略
         */
        public Builder cacheStrategy(@Nullable JCacheStrategy cacheStrategy) {
            mConfig.mCacheStrategy = cacheStrategy;
            return this;
        }

        /**
         * 设置图片的位置
         *
         * @param url 图片的URL
         */
        public Builder from(URL url) {
            mConfig.mAddress = url.toString();
            return this;
        }

        /**
         * 设置图片的位置
         *
         * @param address 描述图片位置的字符串
         */
        public Builder from(String address) {
            mConfig.mAddress = address;
            return this;
        }

        /**
         * 设置图片的位置
         *
         * @param file 包含图片信息的File对象
         */
        public Builder from(File file) {
            from(file.getPath());
            return this;
        }

        /**
         * 设置图片的缓存目录(有bug暂时关闭)
         *
         * @param cacheDir 图片的缓存目录
         */
        private Builder cacheDir(String cacheDir) {
            mCacheDir = cacheDir;
            return this;
        }

        /**
         * 设置输出的图片的大小，是否生效取决于压缩策略的具体实现
         *
         * @param width  图片的输出宽度
         * @param height 图片的输出高度
         */
        public Builder size(int width, int height) {
            mConfig.mCompressOptions.width = width;
            mConfig.mCompressOptions.height = height;
            return this;
        }

        /**
         * 设置ImageView的scaleType属性
         *
         * @param scaleType ImageView的scaleType属性
         */
        public Builder scaleType(ImageView.ScaleType scaleType) {
            mConfig.mCompressOptions.scaleType = scaleType;
            return this;
        }

        /**
         * 设置输出的图片的质量，是否生效取决于压缩策略的具体实现
         *
         * @param quality 图片质量（1-100）
         */
        public Builder quality(int quality) {
            mConfig.mCompressOptions.quality = quality;
            return this;
        }

        /**
         * 设置图片的压缩策略
         * 提供了两种压缩策略，分别为：
         * LosslessCompression（无损压缩策略，即仅参照size（）方法设置的参数按原比例缩放图片）【默认】；
         * NoneCompression（不压缩）。
         * 如果以上策略无法满足需求，可通过继承JCompressStrategy类自定义压缩策略
         *
         * @param compressStrategy 压缩策略
         */
        public Builder compressionStrategy(@Nullable JCompressStrategy compressStrategy) {
            mConfig.mCompressStrategy = compressStrategy;
            return this;
        }
    }
}