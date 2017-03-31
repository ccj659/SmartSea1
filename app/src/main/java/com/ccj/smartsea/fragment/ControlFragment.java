package com.ccj.smartsea.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ccj.smartsea.R;
import com.ccj.smartsea.TestActivity;
import com.ccj.smartsea.base.BaseFragment;
import com.ccj.smartsea.server.SocketService;
import com.ccj.smartsea.utils.EventUtils;
import com.ccj.smartsea.utils.HexTo10Utils;
import com.ccj.smartsea.utils.TLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.ccj.smartsea.server.SocketService.socket;

/**
 * Created by ccj on 2017/3/15.
 */
public class ControlFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener {


    @Bind(R.id.tb_elect)
    CheckBox tbElect;
    @Bind(R.id.tb_light)
    CheckBox tbLight;
    @Bind(R.id.tb_filter)
    CheckBox tbFilter;
    @Bind(R.id.tb_food)
    CheckBox tbFood;
    private View view;
    private static final String TAG = ControlFragment.class.getSimpleName();
    private String str;
    private byte[] cmd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_control_1, null);
        }
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (HexTo10Utils.lightSwitchBtn != null) {
            initdata();
        }
        tbElect.setOnCheckedChangeListener(this);//添加监听事件

        tbLight.setOnCheckedChangeListener(this);//添加监听事件
        tbFilter.setOnCheckedChangeListener(this);//添加监听事件
        tbFood.setOnCheckedChangeListener(this);

      /*  tbElect.setTextKeepState("电磁阀开关");
        tbElect.setTextOn("电磁阀已开启");
        tbElect.setTextOff("电磁阀已关闭");


        tbLight.setTextKeepState("景观灯开关");
        tbLight.setTextOn("景观灯已开启");
        tbLight.setTextOff("景观灯已关闭");

        tbFilter.setTextKeepState("过滤器开关");
        tbFilter.setTextOn("过滤器已开启");
        tbFilter.setTextOff("过滤器已关闭");


        tbFood.setTextKeepState("投食器开关");
        tbFood.setTextOn("投食器已开启");
        tbFood.setTextOff("投食器已关闭");*/



        /*tbElect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                TLog.error("tbElect onclick"+b);
                if(b){
                    compoundButton.setText("电磁阀已开启");
                }else{
                    compoundButton.setText("电磁阀已关闭");
                }
            }
        });
*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

 /*   @OnClick({R.id.tb_elect, R.id.tb_light, R.id.tb_filter, R.id.tb_food})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tb_elect:
                break;
            case R.id.tb_light:
                break;
            case R.id.tb_filter:
                break;
            case R.id.tb_food:
                break;
        }
    }*/

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        CheckBox toggleButton = (CheckBox) compoundButton;
        TLog.error("tbElect onclick" + toggleButton.getText() + "-->" + b);

        if (b) {
            toggleButton.setTextColor(Color.BLUE);
        } else {
            toggleButton.setTextColor(Color.GRAY);
        }


        switch (compoundButton.getId()) {
            case R.id.tb_elect:

                if (b) {
                    compoundButton.setText("电磁阀已开启");
                    cmd = new byte[]{0x00, 0x00, 0x00, 0x01, (byte) 0xFA};
                } else {
                    compoundButton.setText("电磁阀已关闭");
                    cmd = new byte[]{0x00, 0x00, 0x00, 0x00, (byte) 0xFA};

                }

                break;
            case R.id.tb_light:

                if (b) {
                    compoundButton.setText("景观灯已开启");
                    cmd = new byte[]{0x00, 0x00, 0x00, 0x01, (byte) 0xAF};

                } else {
                    compoundButton.setText("景观灯已关闭");
                    cmd = new byte[]{0x00, 0x00, 0x00, 0x00, (byte) 0xAF};


                }

                break;
            case R.id.tb_filter:
                if (b) {
                    compoundButton.setText("过滤器已开启");
                    cmd = new byte[]{0x00, 0x00, 0x00, 0x01, (byte) 0xFB};

                } else {
                    compoundButton.setText("过滤器已关闭");
                    cmd = new byte[]{0x00, 0x00, 0x00, 0x00, (byte) 0xFB};

                }

                break;
            case R.id.tb_food:
                if (b) {
                    compoundButton.setText("投食器已开启");
                    cmd = new byte[]{0x00, 0x00, 0x00, 0x01, (byte) 0xBF};

                } else {
                    compoundButton.setText("投食器已关闭");
                    cmd = new byte[]{0x00, 0x00, 0x00, 0x00, (byte) 0xBF};
                }

                break;
        }
        onClickSend(cmd);

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
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventUtils.StringEvent event) {
        Log.e(TAG, "onEventMainThread getObjectEvent" + event.getMsg());

        Log.e(TAG, "state" + HexTo10Utils.electSwitchBtn.state + "--" +
                HexTo10Utils.lightSwitchBtn.state + "--" +
                HexTo10Utils.filterSwitchBtn.state + "--" +
                HexTo10Utils.foodSwitchBtn.state + "--"
        );

     /*   tbElect.setChecked(HexTo10Utils.electSwitchBtn.state);
        tbLight.setChecked(HexTo10Utils.lightSwitchBtn.state);
        tbFilter.setChecked(HexTo10Utils.filterSwitchBtn.state);

        tbFood.setChecked(HexTo10Utils.foodSwitchBtn.state);*/

        initdata();

    }

    private void initdata() {
        tbElect.setClickable(true);
        tbLight.setClickable(true);

        tbFilter.setClickable(true);

        tbFood.setClickable(true);


        setState(tbElect, HexTo10Utils.electSwitchBtn.state);
        setState(tbLight, HexTo10Utils.lightSwitchBtn.state);

        setState(tbFilter, HexTo10Utils.filterSwitchBtn.state);

        setState(tbFood, HexTo10Utils.foodSwitchBtn.state);
    }


    public void setState(CompoundButton compoundButton, boolean b) {


        if (b) {
            compoundButton.setTextColor(Color.BLUE);
            compoundButton.setChecked(b);
        } else {
            compoundButton.setTextColor(Color.GRAY);
            compoundButton.setChecked(b);
        }

        switch (compoundButton.getId()) {
            case R.id.tb_elect:

                if (b) {
                    compoundButton.setText("电磁阀已开启");

                } else {
                    compoundButton.setText("电磁阀已关闭");
                }

                break;
            case R.id.tb_light:

                if (b) {
                    compoundButton.setText("景观灯已开启");
                } else {
                    compoundButton.setText("景观灯已关闭");
                }

                break;
            case R.id.tb_filter:
                if (b) {
                    compoundButton.setText("过滤器已开启");
                } else {
                    compoundButton.setText("过滤器已关闭");
                }

                break;
            case R.id.tb_food:
                if (b) {
                    compoundButton.setText("投食器已开启");
                } else {
                    compoundButton.setText("投食器已关闭");
                }

                break;
        }

    }


    //00000001FA
    public void onClickSend(byte[] cmd) {


        byte[] b = new byte[5];
        b[0] = (byte) 0x00;
        b[1] = (byte) 0x00;
        b[2] = (byte) 0x00;
        b[3] = (byte) 0x00;
        b[4] = (byte) 0xAF;


        //   int[] cmd1=new int[]{00,00,00,01,175};


        try {
            if (socket == null) {
                // SocketService.startThreadConected();
                Log.e("socket", "socket is null--->");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (SocketService.pw == null) {
            Toast.makeText(getActivity(), "请进行重启app,进行socket连接", Toast.LENGTH_SHORT).show();
            return;
        }
 /*       SocketService.pw.write(cmd1[0]);
        SocketService.pw.write(cmd1[1]);
        SocketService.pw.write(cmd1[2]);
        SocketService.pw.write(cmd1[3]);
        SocketService.pw.write(cmd1[4]);
        */


//        SocketService.pw.write(cmd[0]);
//        SocketService.pw.write(cmd[1]);
//        SocketService.pw.write(cmd[2]);
//        SocketService.pw.write(cmd[3]);
//        SocketService.pw.write(cmd[4]);
        //SocketService.pw.println(b);
        OutputStream out = null;
        try {
            out = socket.getOutputStream();
            if (out == null) return;
            out.write(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // SocketService.pw.flush();
        Log.e("socket", "socket send mesg to pc--->" + cmd);
    }


}
