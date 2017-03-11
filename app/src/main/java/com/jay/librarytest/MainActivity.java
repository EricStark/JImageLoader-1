package com.jay.librarytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.jay.imageloader.JImageLoader;
import com.jay.imageloader.cache.DiskCacheStrategy;
import com.jay.imageloader.config.RequestConfig;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView imageView = (ImageView) findViewById(R.id.image);
        RequestConfig config = new RequestConfig(this)
                .cacheStrategy(DiskCacheStrategy.getInstance(getExternalCacheDir().getPath()))
                .from("http://www.jikedaohang.com/images/first_code.jpg");
        JImageLoader.displayImage(imageView, config);
    }
}
