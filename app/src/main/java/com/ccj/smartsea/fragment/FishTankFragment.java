package com.ccj.smartsea.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ccj.smartsea.R;
import com.ccj.smartsea.adapter.TestAdapter;
import com.ccj.smartsea.adapter.base.ItemClickListener;
import com.ccj.smartsea.base.BaseFragment;
import com.ccj.smartsea.bean.FishTank;
import com.ccj.smartsea.event.MessageEvent;
import com.ccj.smartsea.utils.EventUtils;
import com.ccj.smartsea.utils.HexTo10Utils;
import com.ccj.smartsea.utils.HexUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ccj on 2017/3/15.
 */
public class FishTankFragment extends BaseFragment {


    @Bind(R.id.recycleview)
    RecyclerView recycleview;
    @Bind(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    private View view;

    private static final String TAG = FishTankFragment.class.getSimpleName();
    private ArrayList<FishTank> mFishTanks;
    private TestAdapter constantAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_fish_tank, null);
        }
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        if (HexTo10Utils.fishTanks != null) {
            initData();
        }


    }

    @Override
    public void initView() {
        super.initView();


        swipeLayout.setColorSchemeResources(R.color.colorAccent,
                R.color.red,
                R.color.colorPrimary,
                R.color.colorPrimaryDark);
        //swipeLayout.setProgressBackgroundColor(R.color.swipe_background_color);
        //swipeLayout.setPadding(20, 20, 20, 20);
        //swipeLayout.setProgressViewOffset(true, 100, 200);
        //swipeLayout.setDistanceToTriggerSync(50);
        swipeLayout.setProgressViewEndTarget(true, 100);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

               // initData();
                swipeLayout.setRefreshing(false);

                // /constantAdapter.notifyDataSetChanged();
            }
        });


    }

    private void initData() {
  /*
        mFishTanks = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            FishTank resultBean = new FishTank();
            resultBean.id = "水缸" + i;
            resultBean.depth = "12";
            resultBean.temp = "21";
            resultBean.turbidness = "45";
            mFishTanks.add(resultBean);
        }*/
        constantAdapter = new TestAdapter(HexTo10Utils.fishTanks, getActivity());
        recycleview.setLayoutManager(new LinearLayoutManager(getActivity()));//这里用线性显示 类似于listview
        recycleview.setAdapter(constantAdapter);
        constantAdapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getActivity(), "onClick-->" + position, Toast.LENGTH_SHORT).show();
            }
        });
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
        Log.e(TAG, "onEventMainThread getObjectEvent" + HexTo10Utils.fishTanks.toString());

        Log.e(TAG, "state" + HexTo10Utils.electSwitchBtn.state + "--" +
                HexTo10Utils.lightSwitchBtn.state + "--" +
                HexTo10Utils.filterSwitchBtn.state + "--" +
                HexTo10Utils.foodSwitchBtn.state + "--"
        );

        initData();
    }


}
