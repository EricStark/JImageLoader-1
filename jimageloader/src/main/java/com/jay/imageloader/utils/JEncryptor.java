package com.jay.imageloader.utils;

/**
 * 加密策略接口
 */

public interface JEncryptor {
    /**
     * 对文件名进行加密
     *
     * @param str 未加密字符串
     * @return 加密后的字符串
     */
    String encrypt(String str);
}
