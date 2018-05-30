package xeno.imageloadingdemo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
//import com.nostra13.universalimageloader.core.assist.ImageScaleType;
//import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import xeno.imageloadingdemo.R;
import xeno.imageloadingdemo.service.TCPServerService;

public class MainActivity extends AppCompatActivity {

    boolean stop = false;

    private Button mTestSocketBtn;
    private TextView mChatView;

    private String urlString = "http://image.tianjimedia.com/uploadImages/2012/067/N80N0GUA36N0.jpg";

//    private ImageLoader imageLoader = ImageLoader.getInstance();
//    private DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ImageView iv = (ImageView) findViewById(R.id.iv);
//
//        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
//        options = new DisplayImageOptions.Builder()
//                .resetViewBeforeLoading(true)//设置下载的图片下载前是否重置，复位
//                .cacheInMemory(true)//设置下载图片是否缓存到内存
//                .imageScaleType(ImageScaleType.EXACTLY)
//                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片解码类型
//                .displayer(new FadeInBitmapDisplayer(300))//设置用户加载图片的task(这里是渐现)
//                .build();
//
//        //imageLoader.displayImage(urlString, img_loadView,options);
//        ImageLoader.getInstance().displayImage(urlString, iv,options);

        mTestSocketBtn = (Button) findViewById(R.id.test_socket);
        final Button stopBtn = (Button) findViewById(R.id.stop);
        mChatView = (TextView) findViewById(R.id.chat);

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop = true;
            }
        });
        mTestSocketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop = false;
                sendMsgToSocketServer();
            }
        });

        bindSocketService();
    }

    /**
     * 处理 Socket 线程切换
     */
    @SuppressWarnings("HandlerLeak")
    public class SocketHandler extends Handler {
        public static final int CODE_SOCKET_CONNECT = 1;
        public static final int CODE_SOCKET_MSG = 2;

        @Override
        public void handleMessage(final Message msg) {
            switch (msg.what) {
                case CODE_SOCKET_CONNECT:
                    mTestSocketBtn.setEnabled(true);
                    break;
                case CODE_SOCKET_MSG:
                    mChatView.setText(mChatView.getText() + (String) msg.obj);
                    break;
            }
        }
    }

    private void bindSocketService() {
        //启动服务端
        Intent intent = new Intent(this, TCPServerService.class);
        startService(intent);
        Log.i("xeno", "start service");

        mSocketHandler = new SocketHandler();

        new Thread(new Runnable() {    //新开一个线程连接、接收数据
            @Override
            public void run() {
                try {
                    connectSocketServer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Socket mClientSocket;
    private PrintWriter mPrintWriter;
    private SocketHandler mSocketHandler;

    /**
     * 通过 Socket 连接服务端
     */
    private void connectSocketServer() throws IOException {
        Socket socket = null;
        while (socket == null && !stop) {    //选择在循环中连接是因为有时请求连接时服务端还没创建，需要重试
            try {
                Log.i("connect", "请求连接！");
                socket = new Socket("localhost", 1234);
                Log.i("connect", "new 出Socket");
                mClientSocket = socket;
                mPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                Log.i("connect", "getOutputStream");
            } catch (IOException e) {
                SystemClock.sleep(1_000);
            }
        }

        if(stop) {
            return;
        }

        //连接成功
        mSocketHandler.sendEmptyMessage(SocketHandler.CODE_SOCKET_CONNECT);

        //获取输入流
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        while (!isFinishing()) {    //死循环监听服务端发送的数据
            final String msg = in.readLine();
            if (!TextUtils.isEmpty(msg)) {
                //数据传到 Handler 中展示
                mSocketHandler.obtainMessage(SocketHandler.CODE_SOCKET_MSG,
                        "\n" + "\nserver : " + msg)
                        .sendToTarget();
            }
            SystemClock.sleep(1_000);
        }

        System.out.println("Client quit....");
        mPrintWriter.close();
        in.close();
        socket.close();
    }


    public void sendMsgToSocketServer() {
        final String msg = "客户端发送的消息";
        if (!TextUtils.isEmpty(msg) && mPrintWriter != null) {
            //发送数据，这里注意要在线程中发送，不能在主线程进行网络请求，不然就会报错
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mPrintWriter.println(msg);
                }
            }).start();
            mChatView.setText(mChatView.getText() + "\n" + "\nclient : " + msg);
        }
    }
}
