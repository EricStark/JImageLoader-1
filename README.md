# JImageLoader
一个建议的图片加载库，支持多线程加载图片，图片缓存，图片压缩等功能。

## 依赖
```Gradle
repositories {
    maven {
        url 'https://dl.bintray.com/jay86/maven'
    }
}

dependencies {
    compile 'compile 'com.jay:jimageloader:1.0.2''
}
```

## 使用
+ 构造```RequestConfig```对象：
  ```Java
  RequestConfig requestConfig = new RequestConfig.Builder(context)
        .from("http://www.jikedaohang.com/images/gaoxiao.jpg")  //设置图片地址
        .error(R.drawable.ic_error_black_24dp)  //设置加载失败时的占位图
        .placeHolder(R.drawable.ic_android_black_24dp)  //设置加载未完成前的占位图
        .compressionStrategy(LosslessCompression.getInstance()) //设置图片压缩策略
        .size(200, 200) //图片大小
        .cacheStrategy(DoubleCacheStrategy.getInstance())   //设置图片缓存策略
        .cacheDir("/sdcard/cache")  //设置缓存目录
        .quality(100)   //设置图片质量（1-100）
        .scaleType(ImageView.ScaleType.CENTER_CROP)  //设置图片的scaleType
        .build();
  ```
+ 加载图片
  ```Java
   //将图片设置到imageView上
  JImageLoader.displayImage(imageView, config);
  //仅加载图片
  JImageLoader.submitLoadRequest(requestConfig, new Callback() {
    	@Override
    	public void success(Bitmap bitmap) {
     
    	}
 
    	@Override
    	public void fail(Exception e) {
 
    	}
});
  ```
+ 缓存策略
你可以通过```RequestConfig.Builder```类的```cacheStrategy()```方法设置缓存策略，库中提供了以下4种缓存策略：
  + ```DiskCacheStrategy```（磁盘缓存）；
  + ```MemoryCacheStrategy```（内存缓存）；
  + ```NoneCacheStrategy```（不使用缓存）；
  + ```DoubleCacheStrategy```（磁盘与内存双缓存）【默认】。

  如果以上缓存策略无法满足需求，可以通过实现```CacheStrategy```接口自定义缓存策略。
+ 压缩策略
你可以通过```RequestConfig.Builder```类的```compressionStrategy()```方法设置图片压缩策略，库中提供了以下2种图片压缩策略：
  + ```LosslessCompression```（无损压缩，即仅改变图片大小，不改变图片质量）；
  + ```NoneCompression```（不使用缓存）。
  
  如果以上图片压缩策略无法满足需求，你可以通过继承```JCompressStrategy```类自定义图片缓存策略。

**注：```RequestConfig.Builder```类的```size()```和```quality()```方法是否失效取决于设置的图片压缩策略。（通过这两个方法及```scaleType()```方法设置的参数会被打包成```JCompressStrategy.CompressOptions```对象并传递给```JCompressStrategy```的```compress()```方法，在获取图片时会调用这个方法对图片进行压缩）**

## 缺陷
+ 当不使用缓存策略时压缩策略也没法使用
+ 设置缓存目录因为一些bug暂时取消

## 开发者
Jay Li [1432562823l@gmail.com](mailto:1432562823l@gmail.com)

## 开源许可
```
Copyright 2017 Jay Li
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```