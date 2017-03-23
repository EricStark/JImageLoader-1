package com.jay.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jay.imageloader.JImageLoader;
import com.jay.imageloader.cache.DoubleCacheStrategy;
import com.jay.imageloader.compress.NoneCompression;
import com.jay.imageloader.config.RequestConfig;

public class MainActivity extends AppCompatActivity {
    private Button mButton;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.button);
        mImageView = (ImageView) findViewById(R.id.imageView);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestConfig config = new RequestConfig.Builder(MainActivity.this)
                        .from("http://ok4qp4ux0.bkt.clouddn.com/img-222c4cafc0af1718a6a3b45224cf5229.jpg")
                        .scaleType(ImageView.ScaleType.CENTER_CROP)
                        .cacheStrategy(DoubleCacheStrategy.getInstance())
                        .compressionStrategy(NoneCompression.getInstance())
                        .error(R.drawable.ic_error)
                        .placeHolder(R.drawable.ic_hold)
                        .build();
                JImageLoader.displayImage(mImageView, config);
            }
        });
    }
}
