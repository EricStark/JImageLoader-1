package com.jay.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;

import com.jay.imageloader.cache.JCacheStrategy;
import com.jay.imageloader.config.RequestConfig;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 照片加载类
 */

public class JImageLoader {
    private static final Handler HANDLER = new Handler();
    //线程池
    private static ExecutorService sFixedThreadPool = Executors.newFixedThreadPool
            (Runtime.getRuntime().availableProcessors());

    public static void displayImage(final ImageView imageView, final RequestConfig config) {
        imageView.setImageDrawable(config.getPlaceHolder());
        imageView.setTag(config.getAddress());
        load(config, new Callback() {
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

    public static void load(RequestConfig config, Callback callback) {
        //读取缓存
        Bitmap bitmap = config.getCacheStrategy().get(config.getAddress());
        if (bitmap != null) {
            callback.success(bitmap);
            return;
        }
        //从网络加载
        downLoadImage(config.getAddress(), config.getCacheStrategy(), callback);
    }

    private static void downLoadImage(final String address, final JCacheStrategy cacheStrategy, final Callback callback) {
        sFixedThreadPool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    final Bitmap bitmap = address.startsWith("http://") ? fromHttp(address) : fromFile(address);
                    HANDLER.post(new Runnable() {
                        @Override
                        public void run() {
                            if (bitmap == null) {
                                callback.fail(new Exception("unknown"));
                            } else {
                                callback.success(bitmap);
                                cacheStrategy.put(address, bitmap);
                            }
                        }
                    });
                } catch (final IOException e) {
                    e.printStackTrace();
                    HANDLER.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.fail(e);
                        }
                    });
                }
            }
        });
    }

    private static Bitmap fromHttp(String address) throws IOException {
        URL url = new URL(address);
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        final Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
        connection.disconnect();
        return bitmap;
    }

    private static Bitmap fromFile(String address) {
        return BitmapFactory.decodeFile(address);
    }
}