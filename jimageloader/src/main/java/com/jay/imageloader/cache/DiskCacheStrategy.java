package com.jay.imageloader.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jay.imageloader.compress.JCompressStrategy;
import com.jay.imageloader.utils.JEncryptor;
import com.jay.imageloader.utils.Md5Encryptor;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 磁盘缓存
 */

public class DiskCacheStrategy implements JCacheStrategy {
    private JEncryptor mJEncryptor = new Md5Encryptor();  //默认采用md5加密命名文件
    private String mCacheDir;

    private DiskCacheStrategy() {}

    public static DiskCacheStrategy getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public void put(String address, Bitmap bitmap, JCompressStrategy compressStrategy,
                    JCompressStrategy.CompressOptions options) {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(mCacheDir + mJEncryptor.encrypt(catArgs(address, options)));
            compressStrategy.compress(bitmap, options)  //压缩图片
                    .compress(Bitmap.CompressFormat.PNG, 100, os);  //保存图片
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
    public Bitmap get(String address, JCompressStrategy.CompressOptions options) {
        return BitmapFactory.decodeFile(mCacheDir + mJEncryptor.encrypt(catArgs(address, options)));
    }

    private String catArgs(String srcAddress, JCompressStrategy.CompressOptions options) {
        return srcAddress + "?width=" + options.width + "&height="
                + options.height + "&quality=" + options.quality;
    }

    public void setCacheDir(String path) {
        mCacheDir = path.endsWith("/") ? path : path + "/";
    }

    private static class InstanceHolder {
        private static final DiskCacheStrategy INSTANCE = new DiskCacheStrategy();
    }
}
