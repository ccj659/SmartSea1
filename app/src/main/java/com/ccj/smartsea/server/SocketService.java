package com.ccj.smartsea.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ccj.smartsea.TestActivity;
import com.ccj.smartsea.utils.EventUtils;
import com.ccj.smartsea.utils.HexTo10Utils;
import com.ccj.smartsea.utils.HexUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by ccj on 2017/3/16.
 */

public class SocketService {

    private static Socket socket;
    private static PrintWriter pw;
    private static BufferedReader br;



    private void startThreadConected(final String address, final int port) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                socketConect(address,port);
            }
        }).start();
    }

    public static void socketConect(String address,int port) {
        try {
            //socket=new Socket("192.168.2.211",5648);        //连接到tobacco5648.xicp.net的5648端口

            socket=new Socket(address,port);        //连接到tobacco5648.xicp.net的5648端口

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("socket","unknown host");
            EventBus.getDefault().post(new EventUtils.ConnectedEvent("unknown host"+e.getMessage()));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.e("socket","io execption");
            EventBus.getDefault().post(new EventUtils.ConnectedEvent("io execption"+e.getMessage()));

        }
        if(socket==null){
            Log.e("socket","null");
            EventBus.getDefault().post(new EventUtils.ConnectedEvent("socket==null"));

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

                String msg="local:"+socket.getLocalAddress().getHostAddress()+",port"+ socket.getLocalPort()+","+
                        "accept:"+socket.getInetAddress().getHostAddress()+",port"+ socket.getPort()+","+
                        "socket connected success";

                EventBus.getDefault().post(new EventUtils.ConnectedEvent(msg));

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static void runSocket() {
        try {
            String str;

            while((str=br.readLine())!=null){
                final String s=str;
                Log.e("socket","socket recivered mesg--->"+s);
                HexTo10Utils.getData(s);
                EventBus.getDefault().post(new EventUtils.StringEvent(s));

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
         *//*   @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu, menu);
            return true;
        }*//*

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

    }*/





}
