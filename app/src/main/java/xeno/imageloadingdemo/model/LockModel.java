package xeno.imageloadingdemo.model;

import android.util.Log;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * Created by xeno on 2018/6/21.
 */

public class LockModel {

    public void play() {
        TestThread b = new TestThread("线程B");
        b.start();
        try {
            b.joinX(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("x","主线程继续执行");
    }

//    public void getLock() {
//        synchronized (obj) {
//            //主线程先拿到锁
//            Log.i("x", Thread.currentThread().getName() + "执行中");
//            try {
//                Log.i("x", "调用wait()");
//                obj.wait();
//                Log.i("x", Thread.currentThread().getName() + "执行中");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public void buy() {

    }

    public class TestThread extends Thread {
        final Object lock = new Object();

        public TestThread(String name) {
            super(name);
        }

        public final void joinX(long millis) throws InterruptedException {
            synchronized(lock) {
                long base = System.currentTimeMillis();
                long now = 0;

                if (millis < 0) {
                    throw new IllegalArgumentException("timeout value is negative");
                }

                if (millis == 0) {
                    while (isAlive()) {
                        Log.i("x", Thread.currentThread().getName());
                        lock.wait(0);
                    }
                } else {
                    while (isAlive()) {
                        long delay = millis - now;
                        if (delay <= 0) {
                            break;
                        }
                        lock.wait(delay);
                        now = System.currentTimeMillis() - base;
                    }
                }
            }
        }

        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                try {
                    sleep(100);
                    Log.i("x", Thread.currentThread().getName() + i);
                    if(i==15) {
                        synchronized (lock) {
                            lock.notifyAll();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


    }

}
