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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ccj on 2017/3/16.
 */

public class SocketService {

    public static Socket socket;
    public static PrintWriter pw;
    public static BufferedReader br;
    public static ArrayList<String> strings = new ArrayList<>();
    public static int index = 0;
    public boolean isNext;
    public boolean isRun;
    private static boolean isClient;
    private InputStream in;
    private String str;
    private boolean isNull;


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
            isClient=true;
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("socket", "unknown host");
            EventBus.getDefault().post(new EventUtils.ConnectedEvent("unknown host" + e.getMessage()));
            isClient=false;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.e("socket", "io execption");
            EventBus.getDefault().post(new EventUtils.ConnectedEvent("io execption" + e.getMessage()));
            isClient=false;

        }
        if (socket == null) {
            Log.e("socket", "null");
            EventBus.getDefault().post(new EventUtils.ConnectedEvent("socket==null"));
            isClient=false;

        } else
            try {
                pw = new PrintWriter(socket.getOutputStream());
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                if (pw != null && br != null) {
                    Log.e("socket", "pw!=null&&br!=null");

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                           // isRun=true;
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
                isClient=true;

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                isClient=false;

            }
    }

    public void runSocket() {
            strings = new ArrayList<>();
            int str1;
            index = 0;
            int currentIndex = 0;


            while (isClient) {

                try {
                    strings.clear();
                    in = socket.getInputStream();
                    Log.e("aaa", in.toString());
                    byte[] bt = new byte[37];
                    str = new String(bt, "UTF-8");
                    in.read(bt);
                    Log.d("soket1", str);
                    for (int i = 1; i <bt.length ; i++) {
                        Log.e("socket", "socket recivered str-" + i + "-->"+"--currentIndex--"+bt[i]);
                        strings.add(currentIndex,bt[i] + "");
                        if ((bt[i]+"").equals("0")){
                            isNull=true;
                        }else {
                            isNull=false;
                        }
                    }
                    if (isNull){
                        System.out.println( "数据为空");
                        return;
                    }

                    Collections.reverse(strings);
                    Log.e("socket", "socket strings str-"+strings.toString());

                    HexTo10Utils.getData(strings);
                    EventBus.getDefault().post(new EventUtils.StringEvent(strings + ""));

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

//                while ((str1 = br.read()) != -1) {
//
//                if (index == 0) {
//                    currentIndex=0;
//                    ++index;
//                    continue;
//                }
//                Log.e("socket", "socket recivered str-" + index + "-->" + str1+"--currentIndex--"+currentIndex);
//                strings.add(currentIndex,str1 + "");
//                /*//strings.set(index-1.st)
//                if(strings.isEmpty()||strings.get(currentIndex).isEmpty()){
//                    strings.add(currentIndex,str1 + "");
//
//                }else {
//                    strings.set(currentIndex,str1+"");
//
//                }*/
//                if (index >= 37) {
//                    Log.e("socket", "index>=37");
//                    isNext=true;
//                    index = 0;
//                    ArrayList<String> stringsIndex =new ArrayList<>();
//                    for (int i = 1; i <strings.size() ; i++) {
//                        stringsIndex.add(new String(strings.get(i)));
//                    }
//
//                   strings.clear();
//                    Log.e("socket", "socket ArrayList strings--->" + stringsIndex.toString());
//                    HexTo10Utils.getData(stringsIndex);
//                    EventBus.getDefault().post(new EventUtils.StringEvent(str1 + ""));
//                }else {
//                    index++;
//                    Log.e("socket", "index<37---->"+index);
//                    currentIndex++;
//                }
//
//
//                /*HexTo10Utils.getData(s);
//                EventBus.getDefault().post(new EventUtils.StringEvent(s));*/
//            }
//
//
//            /*while((str=br.readLine())!=null){
//                Log.e("socket","socket recivered str--->"+str);
//
//                final String s=str.trim();
//                Log.e("socket","socket recivered s--->"+s);
//                HexTo10Utils.getData(s);
//                EventBus.getDefault().post(new EventUtils.StringEvent(s));
//
//            }*/
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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





}
