package xeno.imageloadingdemo.activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import xeno.imageloadingdemo.R;

public class MainActivity extends AppCompatActivity {



    private String urlString = "http://image.tianjimedia.com/uploadImages/2012/067/N80N0GUA36N0.jpg";

    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView iv = (ImageView) findViewById(R.id.iv);

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(true)//设置下载的图片下载前是否重置，复位
                .cacheInMemory(true)//设置下载图片是否缓存到内存
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片解码类型
                .displayer(new FadeInBitmapDisplayer(300))//设置用户加载图片的task(这里是渐现)
                .build();

        //imageLoader.displayImage(urlString, img_loadView,options);
        ImageLoader.getInstance().displayImage(urlString, iv,options);

    }
}
