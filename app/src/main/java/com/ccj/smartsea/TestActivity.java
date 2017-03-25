package com.ccj.smartsea;

import android.app.Activity;
import android.os.Looper;
import android.os.Message;
import android.view.View;

/**
 * Created by ccj on 2017/3/22.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ccj.smartsea.event.MessageEvent;
import com.ccj.smartsea.utils.EventUtils;

import org.greenrobot.eventbus.EventBus;

public class TestActivity extends Activity implements View.OnClickListener{

        public static String msg;

        private Button button;
        private EditText editText;
        private static Socket socket;
        private static PrintWriter pw;
        private static BufferedReader br;
        private Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);


            }
        };

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_test);
            handler=new Handler();
            button=(Button)findViewById(R.id.button);
            button.setOnClickListener(this);
            editText=(EditText)findViewById(R.id.edittext);

            startThreadConected();

        }


    private void startThreadConected() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                socketConect();
            }
        }).start();
    }

    public static void socketConect() {
        try {
            socket=new Socket("192.168.2.211",5648);        //连接到tobacco5648.xicp.net的5648端口

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("socket","unknown host");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("socket","io execption");
        }
        if(socket==null){
            Log.e("socket","null");
        }
        else
            try {
                pw=new PrintWriter(socket.getOutputStream());
                br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                if(pw!=null&&br!=null){
                    Log.e("socket","pw!=null&&br!=null");

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            runSocket();
                        }
                    }).start();
                }

                Log.e("socket","local:"+socket.getLocalAddress().getHostAddress()+",port"+ socket.getLocalPort());

                Log.e("socket","accept:"+socket.getInetAddress().getHostAddress()+",port"+ socket.getPort());
                Log.e("socket","socket connected success");


            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

     /*   @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu, menu);
            return true;
        }*/

    public void onClick(View view) {
        if(view==button){
            try {
                if (socket.getKeepAlive()==false){
                    startThreadConected();
                    return;

                }
            } catch (SocketException e) {
                e.printStackTrace();
            }

            String str;
            str=editText.getText().toString();
           // str="-----------mesgss------";
            if (pw==null){
                Toast.makeText(TestActivity.this, "请进行重启app,进行socket连接", Toast.LENGTH_SHORT).show();

                return;
            }
            pw.println(str);
            pw.flush();
            Log.e("socket","socket send mesg to pc--->"+str);

        }

    }

    public static void runSocket() {
        System.out.println("accept:"+socket.getInetAddress().getHostAddress());
        try {
            String str;

            while((str=br.readLine())!=null){
                final String s=str;
                msg=s;
                Log.e("socket","socket recivered mesg--->"+s);
                EventBus.getDefault().post(new EventUtils.StringEvent("Hello everyone!"));

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}
