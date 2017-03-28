package com.gean.businessassistant.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.gean.businessassistant.R;
import com.gean.businessassistant.base.BaseActivity;
import com.gean.businessassistant.contract.MainContract;
import com.gean.businessassistant.netstatus.NetUtils;

public class MainActivity extends BaseActivity implements MainContract.MainView{

//    private MainPresenter presenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        TextView textView = (TextView) findViewById(R.id.tv_clickToTwo);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showSimpleTipDialog("测试一下Application context Dialog");
//                showLoadingDialog("正在加载...");
                mNavigator.navigatorToTwoActivity(MainActivity.this);
            }
        });

//        presenter = new MainPresenter(this);
//
//        presenter.getUserInfo();
    }

    @Override
    protected void onNetConnected(NetUtils.NetType netType) {

    }
}
