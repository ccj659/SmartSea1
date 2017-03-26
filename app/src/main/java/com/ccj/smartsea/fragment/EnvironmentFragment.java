package com.ccj.smartsea.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ccj.smartsea.R;
import com.ccj.smartsea.base.BaseFragment;
import com.ccj.smartsea.event.MessageEvent;
import com.ccj.smartsea.utils.EventUtils;
import com.ccj.smartsea.utils.HexTo10Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ccj on 2017/3/15.
 */
public class EnvironmentFragment extends BaseFragment {


    @Bind(R.id.out_temp)
    TextView outTemp;
    @Bind(R.id.out_humidity)
    TextView outHumidity;
    @Bind(R.id.out_pm25)
    TextView outPm25;
    @Bind(R.id.out_pm10)
    TextView outPm10;
    @Bind(R.id.out_smoke)
    TextView outSmoke;
    @Bind(R.id.in_temp)
    TextView inTemp;
    @Bind(R.id.in_humidity)
    TextView inHumidity;
    @Bind(R.id.in_pm25)
    TextView inPm25;
    @Bind(R.id.in_pm10)
    TextView inPm10;
    private View view;
    private static final String TAG = EnvironmentFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_environment, null);
        }
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
if (HexTo10Utils.outEnvironment!=null){

    initdata();
}

    }

    private void initdata() {
        outTemp.setText(HexTo10Utils.inEnvironment.temp);
        outHumidity.setText(HexTo10Utils.inEnvironment.tempIn);
        outPm25.setText(HexTo10Utils.inEnvironment.pm25);
        outPm10.setText(HexTo10Utils.inEnvironment.pm10);
        outSmoke.setText(HexTo10Utils.inEnvironment.smoke);

        inTemp.setText(HexTo10Utils.outEnvironment.temp);
        inHumidity.setText(HexTo10Utils.outEnvironment.tempIn);
        inPm25.setText(HexTo10Utils.outEnvironment.pm25);
        inPm10.setText(HexTo10Utils.outEnvironment.pm10);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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

        Log.e(TAG, "inEnvironment" + HexTo10Utils.inEnvironment.toString() + "--" +
                HexTo10Utils.outEnvironment.toString() + "--"
        );


        initdata();
    }



}
