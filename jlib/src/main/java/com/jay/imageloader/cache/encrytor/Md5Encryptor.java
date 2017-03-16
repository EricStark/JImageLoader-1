package com.jay.imageloader.cache.encrytor;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5加密
 */

public class Md5Encryptor implements Encryptor {
    @Override
    public String encrypt(String str) {
        String result = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            md5.update(str.getBytes());
            result = new BigInteger(1, md5.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }
}
