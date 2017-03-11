package com.jay.imageloader.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import com.jay.utils.Md5;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 磁盘缓存
 */

public class DiskCacheStrategy implements JCacheStrategy {
    private static final DiskCacheStrategy INSTANCE = new DiskCacheStrategy();
    private String mCacheDir;

    private DiskCacheStrategy() {
    }

    public static DiskCacheStrategy getInstance(@NonNull String cacheDir) {
        INSTANCE.setCacheDir(cacheDir);
        return INSTANCE;
    }

    @Override
    public void put(String address, Bitmap bitmap) {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(mCacheDir + Md5.getMd5(address));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (os != null)
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public Bitmap get(String address) {
        return BitmapFactory.decodeFile(mCacheDir + Md5.getMd5(address));
    }

    public void setCacheDir(String path) {
        mCacheDir = path.endsWith("/") ? path : path + "/";
    }
}
