package com.jay.imageloader.config;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;

import com.jay.imageloader.cache.JCacheStrategy;
import com.jay.imageloader.cache.MemoryCacheStrategy;
import com.jay.imageloader.cache.NoneCacheStrategy;

import java.io.File;
import java.net.URL;

/**
 * 参数设置类
 */

public class RequestConfig {
    private Context mContext;
    private String mAddress;
    private JCacheStrategy mCacheStrategy = MemoryCacheStrategy.getInstance();
    private Drawable mPlaceHolder;
    private Drawable mErrorHolder;

    public RequestConfig(Context context) {
        mContext = context;
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

    public RequestConfig error(Drawable drawable) {
        mErrorHolder = drawable;
        return this;
    }

    public RequestConfig error(@DrawableRes int id) {
        error(mContext.getResources().getDrawable(id));
        return this;
    }

    public RequestConfig placeHolder(Drawable drawable) {
        mPlaceHolder = drawable;
        return this;
    }

    public RequestConfig placeHolder(@DrawableRes int id) {
        placeHolder(mContext.getResources().getDrawable(id));
        return this;
    }

    public RequestConfig cacheStrategy(JCacheStrategy cacheStrategy) {
        if (cacheStrategy == null)
            mCacheStrategy = NoneCacheStrategy.getInstance();
        else
            mCacheStrategy = cacheStrategy;
        return this;
    }

    public RequestConfig from(URL url) {
        mAddress = url.toString();
        return this;
    }

    public RequestConfig from(String address) {
        mAddress = address;
        return this;
    }

    public RequestConfig from(File file) {
        from(file.getPath());
        return this;
    }
}