package com.ccj.smartsea;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ccj.smartsea.base.BaseActivity;
import com.ccj.smartsea.bean.User;
import com.ccj.smartsea.server.SocketService;
import com.ccj.smartsea.utils.EventUtils;
import com.ccj.smartsea.utils.SharedPreferenceUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chenchangjun on 17/3/24.
 */

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.et_ip)
    EditText etIp;
    @Bind(R.id.et_con)
    EditText etCon;
    @Bind(R.id.button2)
    Button button2;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initDialog();
        User user =SharedPreferenceUtil.getInstance().getUser();
        if ( user!=null){
            etIp.setText(user.ip);
            etCon.setText(user.conn);

        }
       ;


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( etIp.getText().toString().isEmpty()){

                    Toast.makeText(LoginActivity.this,"请完善信息",Toast.LENGTH_SHORT).show();
                    return;
                }

                if ( etCon.getText().toString().isEmpty()){

                    Toast.makeText(LoginActivity.this,"请完善信息",Toast.LENGTH_SHORT).show();
                    return;
                }
                showLoading();
                SharedPreferenceUtil.getInstance().putUser(new User(etIp.getText().toString().trim(),etCon.getText().toString().trim()));
                SocketService socketService =new SocketService();
                socketService.startThreadConected(etIp.getText().toString(),Integer.parseInt(etCon.getText().toString()));


            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


/*
    *//**
     * 无论发布者在那个线程,在主线程中订阅
     * @param event
     *//*
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventUtils.StringEvent event) {
        dismissLoading();

        Log.e("onEvent", "onEventMainThread getObjectEvent" + event.getMsg());

    }*/


    /**
     * 无论发布者在那个线程,在主线程中订阅
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventUtils.ConnectedEvent event) {
        dismissLoading();
        Log.e("onEvent", "onEventMainThread getObjectEvent" + event.getMsg());
        Toast.makeText(this,event.getMsg(),Toast.LENGTH_SHORT).show();
        if (event.getMsg().contains("success")){
            Toast.makeText(this,event.getMsg(),Toast.LENGTH_SHORT).show();
            Toast.makeText(this,"连接成功,开始接收数据！",Toast.LENGTH_SHORT).show();
            Intent intent =new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }


    }

    private void initDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在加载");
    }

    public void showLoading() {
        progressDialog.show();
    }

    public void dismissLoading() {
        progressDialog.dismiss();
    }


}
