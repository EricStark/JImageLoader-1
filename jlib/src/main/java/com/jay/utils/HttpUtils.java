package com.jay.utils;

import android.os.Handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 网络请求工具类
 */

public class HttpUtils {
    public static void sendHttpRequest(final String address, final String param, final Callback callback) {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setReadTimeout(5 * 1000);
                    connection.setConnectTimeout(10 * 1000);
                    if (param == null)
                        connection.setRequestMethod("GET");
                    else {
                        connection.setRequestMethod("POST");
                        connection.setDoOutput(true);
                        OutputStream os = connection.getOutputStream();
                        os.write(param.getBytes());
                        os.flush();
                        os.close();
                    }
                    if (connection.getResponseCode() == 200) {
                        final byte[] temp = read(connection.getInputStream());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onResponse(new Response(temp));
                            }
                        });
                    } else throw new Exception("无法连接服务器");
                } catch (final Exception e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFail(e);
                        }
                    });
                } finally {
                    if (connection != null)
                        connection.disconnect();
                }
            }
        }).start();
    }

    private static byte[] read(InputStream is) throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] temp = new byte[1024];
        int len;
        while ((len = is.read(temp)) != -1)
            outputStream.write(temp, 0, len);
        is.close();
        return outputStream.toByteArray();
    }

    public interface Callback {
        void onResponse(Response response);

        void onFail(Exception e);
    }

    public static class Response {
        private String mData;

        public Response(byte[] data) {
            mData = new String(data);
        }

        public String getData() {
            return mData;
        }
    }
}
