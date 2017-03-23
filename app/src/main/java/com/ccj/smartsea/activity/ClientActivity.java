package com.ccj.smartsea.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ccj.smartsea.R;
import com.ccj.smartsea.base.BaseActivity;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by ccj on 2017/3/16.
 */

public class ClientActivity extends BaseActivity {

/*
        private Button btnConnect;

        private Button btnSend;

        private EditText editSend;

        private Socket socket;

        private PrintStream output;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_client);

//      Toast.makeText(ClientActivity.this, this.getIntent().getStringExtra("message"), Toast.LENGTH_LONG).show();

            initView();

            btnConnect.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    initClientSocket();
                }

            });

            btnSend.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    sendMessage(editSend.getText().toString());
                }
            });
        }

    private void initView() {
        btnConnect = (Button) findViewById(R.id.btnConnect);
        btnSend = (Button) findViewById(R.id.btnSend);
        editSend = (EditText) findViewById(R.id.editSend);

        btnSend.setEnabled(false);
        editSend.setEnabled(false);
    }

    private void sendMessage(String str) {
        output.println(str);
    }

    public void closeSocket() {
        try {
            output.close();
            socket.close();
        } catch (IOException e) {
            handleException(e, "close Exception: ");
        }
    }

    private void initClientSocket() {
        try {
            //new
            EditText edit_ip = (EditText) ClientActivity.this.findViewById(R.id.edit_hostIp);
            EditText edit_port = (EditText) ClientActivity.this.findViewById(R.id.edit_hostPort);
            String ip = edit_ip.getText().toString().trim();
            String port = edit_port.getText().toString().trim();

            socket = new Socket(ip,Integer.parseInt(port));

            //get output Stream
            output = new PrintStream(socket.getOutputStream(),true,"gbk");
//          output = new PrintStream(socket.getOutputStream(),true);

            btnConnect.setEnabled(false);
            editSend.setEnabled(true);
            btnSend.setEnabled(true);

        } catch (UnknownHostException e) {
            Toast.makeText(ClientActivity.this, "请检查端口号是否为服务器IP", Toast.LENGTH_LONG).show();
//          handleException(e, "UnknownHostException: " + e);
        } catch (IOException e) {
            Toast.makeText(ClientActivity.this, "服务器未开启", Toast.LENGTH_LONG).show();
//          handleException(e, "IOException" + e);
        }

    }*/
}
