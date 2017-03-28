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

import org.apache.commons.codec.binary.Hex;
import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ccj on 2017/3/16.
 */

public class SocketService {

    private static Socket socket;
    private static PrintWriter pw;
    private static BufferedReader br;
    private static ArrayList<String> strings = new ArrayList<>();
    static int index = 0;


    public void startThreadConected(final String address, final int port) {
        Log.e("socket", "startThreadConected host");

        new Thread(new Runnable() {
            @Override
            public void run() {
                socketConect(address, port);
            }
        }).start();
    }

    public void socketConect(String address, int port) {
        try {
            //socket=new Socket("192.168.2.211",5648);        //连接到tobacco5648.xicp.net的5648端口

            socket = new Socket(address, port);        //连接到tobacco5648.xicp.net的5648端口

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("socket", "unknown host");
            EventBus.getDefault().post(new EventUtils.ConnectedEvent("unknown host" + e.getMessage()));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.e("socket", "io execption");
            EventBus.getDefault().post(new EventUtils.ConnectedEvent("io execption" + e.getMessage()));

        }
        if (socket == null) {
            Log.e("socket", "null");
            EventBus.getDefault().post(new EventUtils.ConnectedEvent("socket==null"));

        } else
            try {
                pw = new PrintWriter(socket.getOutputStream());
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                if (pw != null && br != null) {
                    Log.e("socket", "pw!=null&&br!=null");

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            runSocket();
                        }
                    }).start();
                }

                Log.e("socket", "local:" + socket.getLocalAddress().getHostAddress() + ",port" + socket.getLocalPort());

                Log.e("socket", "accept:" + socket.getInetAddress().getHostAddress() + ",port" + socket.getPort());
                Log.e("socket", "socket connected success");

                String msg = "local:" + socket.getLocalAddress().getHostAddress() + ",port" + socket.getLocalPort() + "," +
                        "accept:" + socket.getInetAddress().getHostAddress() + ",port" + socket.getPort() + "," +
                        "socket connected success";

                EventBus.getDefault().post(new EventUtils.ConnectedEvent(msg));

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public void runSocket() {
        try {
            strings = new ArrayList<>();
            LinkedList<String> buffer = new LinkedList<>();
            int str1;
            index = 0;
            while ((str1 = br.read()) != -1) {
                Log.e("socket", "socket recivered str-" + index + "-->" + str1);

                if (index == 0) {
                    ++index;
                    continue;
                }
                Log.e("socket", "socket recivered str-" + index + "-->" + str1);
                //strings.set(index-1.st)
                strings.add(index,str1 + "");
                strings.set(index,str1+"");
                if (index >= 37) {
                   // Log.e("socket", "index==37-");
                    index = 0;
                   // strings.clear();
                   // strings.addAll(buffer);
                    //buffer.clear();
                    Log.e("socket", "socket recivered strings-" + index + "-->" + strings.toString());
                    HexTo10Utils.getData(strings);
                    EventBus.getDefault().post(new EventUtils.StringEvent(str1 + ""));

                }

                index++;




                /*HexTo10Utils.getData(s);
                EventBus.getDefault().post(new EventUtils.StringEvent(s));*/
            }


            /*while((str=br.readLine())!=null){
                Log.e("socket","socket recivered str--->"+str);

                final String s=str.trim();
                Log.e("socket","socket recivered s--->"+s);
                HexTo10Utils.getData(s);
                EventBus.getDefault().post(new EventUtils.StringEvent(s));

            }*/
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
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
