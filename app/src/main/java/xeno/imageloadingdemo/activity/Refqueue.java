package xeno.imageloadingdemo.activity;

import android.util.Log;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * Created by xeno on 2018/9/5.
 */


public class Refqueue {
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void main() {
        //创建软引用
        ReferenceQueue<SoftReference<G>> rq = new ReferenceQueue<SoftReference<G>>();
        SoftReference[] srArr = new SoftReference[1000];

        //循环1000次，new一个很占内存的对象，在申请新内存的时候，就会回收前面new出的对象
        for (int i = 0; i < srArr.length; i++) {
            srArr[i] = new SoftReference(new G(), rq);
        }
        //获取被清除部分
        int n = 0;
        for (int i = 0; i < srArr.length; i++) {
            if (srArr[i].isEnqueued()) {
                srArr[i] = null;
                n++;
            }
        }
        Log.i("xeno","first GC,removed" + n + "个");

        //尝试请求一次GC
        System.gc();

        //获取第二次被清除部分
        for (int i = 0; i < 10000; i++) {
            G g = new G();
        }
        int m = 0;
        for (int i = 0; i < srArr.length; i++) {
            if (srArr[i] != null && srArr[i].isEnqueued()) {
                srArr[i] = null;
                m++;
            }
        }
        Log.i("xeno","second GC,remove" + m + "个");
    }
    //为了占据内存
    static class G {
        private int[] big = new int[10000];
    }
}
