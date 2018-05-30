package xeno.imageloadingdemo.application;

import android.app.Application;

//import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
//import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
//import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
//import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

/**
 * Created by xeno on 2018/2/13.
 */

public class DemoApplication extends Application {

    private static final int THREAD_COUNT = 2;
    private static final int PRIORITY = 2;
    private static final int DISK_CACHE_SIZE = 50 * 1024 * 1024;
    private static final int CONNECTION_TIME_OUT = 5 * 1000;
    private static final int READ_TIME_OUT = 30 * 1000;

    @Override
    public void onCreate() {
        super.onCreate();

//        ImageLoaderConfiguration config = new ImageLoaderConfiguration
//                .Builder(this)
//                .threadPoolSize(3)
//                .threadPriority(Thread.NORM_PRIORITY)
//                .denyCacheImageMultipleSizesInMemory()
//                .memoryCache(new WeakMemoryCache())
//                .diskCacheSize(DISK_CACHE_SIZE)
//                .diskCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候URL加密
//                .tasksProcessingOrder(QueueProcessingType.LIFO)
//                .imageDownloader(new BaseImageDownloader(this,CONNECTION_TIME_OUT,READ_TIME_OUT))
//                .build();
//        ImageLoader.getInstance().init(config);
    }


}
