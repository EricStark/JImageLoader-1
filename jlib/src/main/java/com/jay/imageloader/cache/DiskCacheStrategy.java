package com.jay.imageloader.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jay.imageloader.cache.encrytor.Encryptor;
import com.jay.imageloader.cache.encrytor.Md5Encryptor;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 磁盘缓存
 */

public class DiskCacheStrategy implements JCacheStrategy {
    private Encryptor mEncryptor = new Md5Encryptor();  //默认采用md5加密命名文件
    private String mCacheDir;

    private DiskCacheStrategy() {}

    public static DiskCacheStrategy getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public void put(String address, Bitmap bitmap) {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(mCacheDir + mEncryptor.encrypt(address));
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
        return BitmapFactory.decodeFile(mCacheDir + mEncryptor.encrypt(address));
    }

    public void setCacheDir(String path) {
        mCacheDir = path.endsWith("/") ? path : path + "/";
    }

    private static class InstanceHolder {
        private static final DiskCacheStrategy INSTANCE = new DiskCacheStrategy();
    }
}
