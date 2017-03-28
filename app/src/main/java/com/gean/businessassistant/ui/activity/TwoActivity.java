package com.gean.businessassistant.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.gean.businessassistant.R;
import com.gean.businessassistant.base.BaseActivity;
import com.gean.businessassistant.contract.MainContract;
import com.gean.businessassistant.netstatus.NetUtils;
import com.gean.businessassistant.presenter.MainPresenter;

import butterknife.ButterKnife;

/**
 * Created by gordon on 2017/3/8
 */

public class TwoActivity extends BaseActivity implements MainContract.MainView{

    private MainPresenter presenter;
    private Button button;
    private Button btnRestoreView;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context,TwoActivity.class);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_two;
    }

    @Override
    protected View getLoadingTargetView() {
        return ButterKnife.findById(this,R.id.tv_clickToTwo);
    }

    @Override
    protected void initViewsAndEvents() {

        button = (Button) findViewById(R.id.btnShowEmpty);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.showEmpty();
            }
        });

        btnRestoreView = (Button) findViewById(R.id.btnRestoreView);
        btnRestoreView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.restoreMainView();
            }
        });

        presenter = new MainPresenter(this);

        presenter.getUserInfo();
    }

    @Override
    protected void onNetConnected(NetUtils.NetType netType) {

    }
}
