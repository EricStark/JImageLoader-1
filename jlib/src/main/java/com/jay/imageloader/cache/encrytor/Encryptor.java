package com.jay.imageloader.cache.encrytor;

/**
 * 加密策略接口
 */

public interface Encryptor {
    //返回加密后的字符串
    String encrypt(String str);
}
