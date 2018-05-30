package xeno.imageloadingdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by xeno on 2018/5/30.
 */

public class TCPServerService extends Service {

    private final String TAG = this.getClass().getSimpleName();

    private boolean mIsServiceDisconnected;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "服务已 create");
        new Thread(new TCPServer()).start();    //新开一个线程开启 Socket
    }


    private class TCPServer implements Runnable {
        @Override
        public void run() {
            ServerSocket serverSocket;
            try {
                serverSocket = new ServerSocket(1234);
                Log.d(TAG, "TCP 服务已创建");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("TCP 服务端创建失败");
                return;
            }

            while (!mIsServiceDisconnected) {
                try {
                    Log.i("xeno0", "循环accept前");
                    Socket client = serverSocket.accept();  //接受客户端消息，阻塞直到收到消息
                    Log.i("xeno0", "循环accept后");
                    //我这里使用了线程池，也可以直接新建一个线程
                    new Thread(responseClient(client)).start();
//                    ThreadPoolManager.getInstance()
//                            .addTask(responseClient(client));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //在这里接受和回复客户端消息
    private Runnable responseClient(final Socket client) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    //接受消息
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    //回复消息
                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
                    out.println("服务端已连接 *****");

                    while (!mIsServiceDisconnected) {
                        String inputStr = in.readLine();
                        Log.i(TAG, "收到客户端的消息：" + inputStr);
                        if (TextUtils.isEmpty(inputStr)) {
                            Log.i(TAG, "收到消息为空，客户端断开连接 ***");
                            break;
                        }
                        out.println("你这句【" + inputStr + "】非常有道理啊！");
                    }
                    out.close();
                    in.close();
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    public void onDestroy() {
        mIsServiceDisconnected = true;
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind调用，但是没有返回Binder");
        return null;
    }
}
