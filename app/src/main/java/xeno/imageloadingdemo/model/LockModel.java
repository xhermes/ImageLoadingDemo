package xeno.imageloadingdemo.model;

import android.util.Log;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * Created by xeno on 2018/6/21.
 */

public class LockModel {
    final Resource res = new Resource();
    final Object obj = new Object();

    public void play() {
        createThreads();
    }

    public void createThreads() {
        for (int i = 0; i < res.ticketCount; i++) {
            new Thread() {
                @Override
                public void run() {
                    if (res.ticketCount > 0) {
                        try {
                            sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        buy();
                    }

                }
            }.start();
        }

    }

    public void buy() {
        synchronized (res) {
            res.ticketCount--;
            Log.w("xeno", Thread.currentThread().getName() + "买票，剩余" + res.ticketCount);
        }

    }

    public static class Resource {
        int ticketCount = 20;
        public void setCount(int count) {
            ticketCount = count;
        }
    }

}
