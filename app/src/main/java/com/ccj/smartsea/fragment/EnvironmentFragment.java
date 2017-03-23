package com.ccj.smartsea.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ccj.smartsea.R;
import com.ccj.smartsea.base.BaseFragment;

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


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
