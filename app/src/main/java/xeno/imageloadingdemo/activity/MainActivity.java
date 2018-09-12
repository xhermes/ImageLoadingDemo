package xeno.imageloadingdemo.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import xeno.imageloadingdemo.R;
import xeno.imageloadingdemo.phoenix.xphotoview.XPhotoView;

//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
//import com.nostra13.universalimageloader.core.assist.ImageScaleType;
//import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViewPager();
    }

    private void setupViewPager() {
        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);

        pager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 1;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = View.inflate(MainActivity.this, R.layout.layout_page, null);
                view.findViewById(R.id.progress).setVisibility(View.VISIBLE);
                setupXPhotoView((XPhotoView) view.findViewById(R.id.xphoto_view), (ProgressBar) view.findViewById(R.id.progress), position);
                container.addView(view);

                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
    }

    private void setupXPhotoView(XPhotoView imageView, final ProgressBar progressBar, int pos) {
        try {
            switch (pos % 6) {
                case 0:
                    imageView.setImage(getAssets().open("map_of_china.jpg"));
                    break;

                case 1:imageView.setImage(getAssets().open("map_of_china.jpg"));
                    break;

                case 2:
                    imageView.setImage(getAssets().open("map_of_china.jpg"));
                    break;

                case 3:
                    imageView.setImage(getAssets().open("map_of_china.jpg"));
                    break;

                case 4:
//                    imageView.setDoubleTapScaleType(XPhotoView.TYPE_FIT.FIT_IMAGE);

                    imageView.setImage(getAssets().open("map_of_china.jpg"));
                    break;

                case 5:
                    imageView.setImage(getAssets().open("map_of_china.jpg"));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        progressBar.setVisibility(View.GONE);
    }

    private void toastShort(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}

