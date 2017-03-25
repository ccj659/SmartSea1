package com.ccj.smartsea;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


import com.ccj.smartsea.event.MessageEvent;
import com.ccj.smartsea.fragment.ControlFragment;
import com.ccj.smartsea.fragment.EnvironmentFragment;
import com.ccj.smartsea.fragment.FishTankFragment;
import com.ccj.smartsea.fragment.PersonFragment;
import com.ccj.smartsea.utils.AppManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.realtabcontent)
    FrameLayout realtabcontent;
    @Bind(android.R.id.tabcontent)
    FrameLayout tabcontent;
    @Bind(android.R.id.tabhost)
    FragmentTabHost mTabHost;
    TabHost.TabSpec tabSpec0;
    private TextView tv_count;
    private Class fragmentArray[] = {FishTankFragment.class, EnvironmentFragment.class, ControlFragment.class, PersonFragment.class};
    private String tabHostTagArray[] = {"鱼缸检测", "周围环境", "控制面板", "网络设置"};
    private int mImageViewArray[] = {R.drawable.ic_contacts, R.drawable.ic_contacts,R.drawable.ic_contacts , R.drawable.ic_contacts};

    private TabHost.OnTabChangeListener tabChangeListener;
    private String currentTab=tabHostTagArray[0];
    private TextView toolbar_title;











    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolBar();
        initTabHost();
    }
    public void initToolBar() {
        toolbar.setBackgroundColor(getResources().getColor(R.color.blue));
        //toolbar.setNavigationIcon(R.mipmap.back);
        toolbar_title=(TextView)findViewById(R.id.toolbar_title);
        toolbar.setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initTabHost() {
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.getTabWidget().setDividerDrawable(null);
        int count = fragmentArray.length;
        for (int i = 0; i < count; i++) {
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(tabHostTagArray[i]).setIndicator(getTabItemView(i));
            if (i == 0) {
                this.tabSpec0 = tabSpec;
            }
            //将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
            //设置Tab按钮的背景
            mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.WHITE);
        }

        tabChangeListener = new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                toolbar_title.setText(tabId);
                currentTab = tabId;
            }
        };
        mTabHost.setOnTabChangedListener(tabChangeListener);
    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = View.inflate(MainActivity.this, R.layout.tabhost_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageViewArray[index]);
        TextView main_tab_unread_tv = (TextView) view.findViewById(R.id.main_tab_unread_tv);
        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(tabHostTagArray[index]);
        return view;
    }



    @Override
    public void onBackPressed() {
        // LxApplication.exit();
        exitApp();
    }
    long[] mHits = new long[2];



        //定义一个所需的数组
        private void exitApp() {
//         数组向左移位操作
            System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
            mHits[mHits.length - 1] = SystemClock.uptimeMillis();
            if (mHits[0] >= (SystemClock.uptimeMillis() - 2000)) {// 2000代表设定的间隔时间
                AppManager.getAppManager().AppExit(this);
            } else {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            }
    }


}
