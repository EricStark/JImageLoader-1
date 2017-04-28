package com.jay.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;

import com.jay.imageloader.cache.JCacheStrategy;
import com.jay.imageloader.compress.JCompressStrategy;
import com.jay.imageloader.config.RequestConfig;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 照片加载类
 */

public class JImageLoader {
    private static final Handler UI_HANDLER = new Handler();
    private static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();

    private static ExecutorService sFixedThreadPool = new ThreadPoolExecutor(THREAD_COUNT,
            THREAD_COUNT, 0L, TimeUnit.MILLISECONDS,
            new MyLinkedBlockingDeque<Runnable>());

    /**
     * 将图片加载到imageView
     *
     * @param imageView 图片显示位置
     * @param config    加载参数设置
     */
    public static void displayImage(final ImageView imageView, final RequestConfig config) {
        JCompressStrategy.CompressOptions options = config.getCompressOptions();
        imageView.setImageDrawable(config.getPlaceHolder());
        imageView.setScaleType(options.scaleType);
        if (options.width <= 0 || options.height <= 0) {
            options.width = imageView.getWidth();
            options.height = imageView.getHeight();
        }
        imageView.setTag(config.getAddress());
        submitLoadRequest(config, new Callback() {
            @Override
            public void success(Bitmap bitmap) {
                if (imageView.getTag().equals(config.getAddress()))
                    imageView.setImageBitmap(bitmap);
            }

            @Override
            public void fail(Exception e) {
                e.printStackTrace();
                imageView.setImageDrawable(config.getErrorHolder());
            }
        });
    }

    /***
     * 将图片加载结果返回
     * @param config 加载参数
     * @param callback 回调接口
     */
    public static void submitLoadRequest(final RequestConfig config, final Callback callback) {
        sFixedThreadPool.submit(new Runnable() {
            @Override
            public void run() {
                //读取缓存
                JCacheStrategy cacheStrategy = config.getCacheStrategy();
                Bitmap bitmap = cacheStrategy.get(config.getAddress(), config.getCompressOptions());
                if (bitmap != null) {
                    returnResultInUiThread(callback, bitmap, null);
                    return;
                }
                //加载
                loadImage(config, callback);
            }
        });
    }

    private static void loadImage(RequestConfig config, Callback callback) {
        try {
            //判断请求类型
            String address = config.getAddress();
            final Bitmap bitmap = address.startsWith("http") ? fromHttp(address) : fromFile(address);
            if (bitmap == null) {
                returnResultInUiThread(callback, null, new Exception("unknown"));
            } else {
                JCompressStrategy compressStrategy = config.getCompressStrategy();
                JCompressStrategy.CompressOptions options = config.getCompressOptions();
                returnResultInUiThread(callback, compressStrategy.compress(bitmap, options), null);
                JCacheStrategy cacheStrategy = config.getCacheStrategy();
                cacheStrategy.put(address, bitmap, compressStrategy, options);
            }
        } catch (final IOException e) {
            e.printStackTrace();
            returnResultInUiThread(callback, null, e);
        }
    }

    //从网络中加载
    private static Bitmap fromHttp(String address) throws IOException {
        URL url = new URL(address);
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        final Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
        connection.disconnect();
        return bitmap;
    }

    //从文件中加载
    private static Bitmap fromFile(String address) {
        return BitmapFactory.decodeFile(address);
    }

    //返回结果
    private static void returnResultInUiThread(final Callback callback, final Bitmap bitmap, final Exception e) {
        UI_HANDLER.post(new Runnable() {
            @Override
            public void run() {
                if (e == null)
                    callback.success(bitmap);
                else
                    callback.fail(e);
            }
        });
    }

    /**
     * 重写take与poll实现LIFO
     */
    private static class MyLinkedBlockingDeque<T> extends LinkedBlockingDeque<T> {
        @Override
        public T take() throws InterruptedException {
            return takeLast();
        }

        @Override
        public T poll() {
            return pollLast();
        }
    }
}