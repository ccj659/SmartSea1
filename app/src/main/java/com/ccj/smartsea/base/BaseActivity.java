package com.ccj.smartsea.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ccj.smartsea.R;
import com.ccj.smartsea.utils.AppManager;

/**
 * Created by ccj on 2017/3/15.
 */

public class BaseActivity extends AppCompatActivity {

    protected ProgressDialog progressDialog;
    protected Toolbar toolbar;
    protected Context mContext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        mContext = this;
        initToolBar();
        initDialog();
    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.tool_bar_white));
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    /**
     * 资源释放
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
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
