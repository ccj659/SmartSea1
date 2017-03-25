package com.ccj.smartsea.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ccj.smartsea.R;
import com.ccj.smartsea.base.BaseFragment;
import com.ccj.smartsea.event.MessageEvent;
import com.ccj.smartsea.utils.EventUtils;
import com.ccj.smartsea.utils.HexTo10Utils;
import com.ccj.smartsea.utils.HexUtils;
import com.ccj.smartsea.utils.TLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ccj on 2017/3/15.
 */
public class ControlFragment extends BaseFragment  implements CompoundButton.OnCheckedChangeListener {

    @Bind(R.id.tb_elect)
    ToggleButton tbElect;
    @Bind(R.id.tb_light)
    ToggleButton tbLight;
    @Bind(R.id.tb_filter)
    ToggleButton tbFilter;
    @Bind(R.id.tb_food)
    ToggleButton tbFood;
    private View view;
    private static final String TAG = ControlFragment.class.getSimpleName();

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

        tbElect.setOnCheckedChangeListener(this);//添加监听事件

        tbLight.setOnCheckedChangeListener(this);//添加监听事件
        tbFilter.setOnCheckedChangeListener(this);//添加监听事件
        tbFood.setOnCheckedChangeListener(this);

        tbElect.setTextKeepState("电磁阀开关");
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
        tbFood.setTextOff("投食器已关闭");



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

        ToggleButton toggleButton= (ToggleButton) compoundButton;
        TLog.error("tbElect onclick"+toggleButton.getText()+"-->"+b);

        if(b){
            toggleButton.setTextColor(Color.BLUE);
        }else{
            toggleButton.setTextColor(Color.GRAY);
        }



        switch (compoundButton.getId()) {
            case R.id.tb_elect:


                break;
            case R.id.tb_light:

                if(b){
                    compoundButton.setText("景观灯已开启");
                }else{
                    compoundButton.setText("景观灯已关闭");
                }
                
                break;
            case R.id.tb_filter:
                if(b){
                    compoundButton.setText("过滤器已开启");
                }else{
                    compoundButton.setText("过滤器已关闭");
                }
                
                break;
            case R.id.tb_food:
                if(b){
                    compoundButton.setText("投食器已开启");
                }else{
                    compoundButton.setText("投食器已关闭");
                }
                
                break;
        }

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
    @Subscribe//(threadMode = ThreadMode.MAIN)
    public void onEvent(EventUtils.StringEvent event) {
        Log.e(TAG, "onEventMainThread getObjectEvent" + event.getMsg());
        tbElect.setChecked(HexTo10Utils.electSwitchBtn.state);

        tbLight.setChecked(HexTo10Utils.lightSwitchBtn.state);
        tbFilter.setChecked(HexTo10Utils.filterSwitchBtn.state);
        tbFood.setChecked(HexTo10Utils.foodSwitchBtn.state);



    }


}
