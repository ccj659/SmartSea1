package com.ccj.smartsea;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ccj.smartsea.base.BaseActivity;
import com.ccj.smartsea.server.SocketService;
import com.ccj.smartsea.utils.EventUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chenchangjun on 17/3/24.
 */

public class LoginActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et_ip)
    EditText etIp;
    @Bind(R.id.et_con)
    EditText etCon;
    @Bind(R.id.button2)
    Button button2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getToolbar().setTitle("登录");


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
                SocketService.socketConect(etIp.getText().toString(),Integer.parseInt(etCon.getText().toString()));


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



    /**
     * 无论发布者在那个线程,在主线程中订阅
     * @param event
     */
    @Subscribe//(threadMode = ThreadMode.MAIN)
    public void onEvent(EventUtils.StringEvent event) {
        dismissLoading();

        Log.e("onEvent", "onEventMainThread getObjectEvent" + event.getMsg());
        Toast.makeText(this,event.getMsg(),Toast.LENGTH_SHORT).show();
        Toast.makeText(this,"连接成功！",Toast.LENGTH_SHORT).show();

    }


    /**
     * 无论发布者在那个线程,在主线程中订阅
     * @param event
     */
    @Subscribe//(threadMode = ThreadMode.MAIN)
    public void onEvent(EventUtils.ConnectedEvent event) {
        dismissLoading();
        Log.e("onEvent", "onEventMainThread getObjectEvent" + event.getMsg());
        Toast.makeText(this,event.getMsg(),Toast.LENGTH_SHORT).show();



    }


}
