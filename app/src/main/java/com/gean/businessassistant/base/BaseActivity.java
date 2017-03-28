package com.gean.businessassistant.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.gean.businessassistant.R;
import com.gean.businessassistant.component.ApplicationComponent;
import com.gean.businessassistant.contract.BaseView;
import com.gean.businessassistant.netstatus.NetChangeObserver;
import com.gean.businessassistant.netstatus.NetStateReceiver;
import com.gean.businessassistant.netstatus.NetUtils;
import com.gean.businessassistant.ui.Navigator;
import com.gean.businessassistant.util.DensityUtil;
import com.gean.businessassistant.util.ToastUtil;
import com.gean.businessassistant.views.DialogLoadingView;
import com.gean.businessassistant.views.loadingView.LoadingHelperController;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by gordon on 2017/3/7
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView{

    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Nullable
    @BindView(R.id.toolbar_title)
    TextView tvTitle;

    @Inject
    protected Navigator mNavigator;

    @Inject
    ActivityManager activityManager;

    protected NetChangeObserver mNetChangeObserver;
    protected LoadingHelperController loadingHelperController;

    private Unbinder mButterKnifeUnbinder;
    private DialogLoadingView loadingDialog;
    protected FragmentManager fragmentManager;
    protected FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initSystemBarTint();

        if(getContentViewLayoutID() != 0){
            setContentView(getContentViewLayoutID());
        }else{
            throw new IllegalArgumentException("You must return a right contentView layout resource id");
        }
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        getApplicationComponent().inject(this);
        activityManager.addActivity(this);

        //view初始化或事件处理
        initViewsAndEvents();

        mNetChangeObserver = new NetChangeObserver(){
            @Override
            public void netConnected(NetUtils.NetType type) {
                Log.d("NetStatus -- >>","网络连接");
                onNetConnected(type);
            }

            @Override
            public void netDisConnect() {
                Log.d("NetStatus -- >>","网络断开了");
                onNetDisConnect();
            }
        };
        NetStateReceiver.registerObserver(mNetChangeObserver);

    }

    private void initSystemBarTint() {
        Window window = getWindow();
        if(isTranslucentStatusBar()){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            return;
        }
        // 沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0以上使用原生方法
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(setStatusBarColor());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4-5.0使用三方工具类，有些4.4的手机有问题，这里为演示方便，不使用沉浸式
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            SystemBarTintManager tintManager = new SystemBarTintManager(this);
//            tintManager.setStatusBarTintEnabled(true);
//            tintManager.setStatusBarTintColor(setStatusBarColor());
        }

    }
    /** 子类可以重写改变状态栏颜色 */
    protected int setStatusBarColor() {
        return getColorPrimary();
    }

    /** 获取主题色 */
    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    /** 获取深主题色 */
    public int getDarkColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        return typedValue.data;
    }


    /**状态栏是否全透明*/
    protected boolean isTranslucentStatusBar() {
        return true;
    }

    private ApplicationComponent getApplicationComponent() {
        return BAApplication.getApplication().getApplicationComponent();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mButterKnifeUnbinder = ButterKnife.bind(this);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (getLoadingTargetView() != null) {
            loadingHelperController = new LoadingHelperController(getLoadingTargetView());
        }
    }

    protected abstract int getContentViewLayoutID();

    protected abstract View getLoadingTargetView();

    protected abstract void initViewsAndEvents();
    /**如果网络一开始时就是断开的，当在操作应用过程中又打开了网络，就在这个方法中重新加载*/
    protected abstract void onNetConnected(NetUtils.NetType netType);


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            destory();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        destory();
    }

    private void onNetDisConnect(){
        hideLoadingDialog();
//        if(!isClickLoading) { //如果不是点击弹出框式的加载就全屏显示无网络的界面
//            showNetError();
//        }else {点击弹出框式的加载就弹出无网络提示对话框
//            showSimpleTipDialog(getResources().getString(R.string.netWork_connecting_failed));
//        }
    }

    @Override
    public void showLoadingDialog(String msg) {
        loadingDialog = new DialogLoadingView(this);
        loadingDialog.setLoadingTip(msg);
    }

    @Override
    public void hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()){
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    @Override
    public void showSimpleTipDialog(String msg) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(getResources().getString(R.string.app_name));
        dialogBuilder.setMessage(msg);
        dialogBuilder.setPositiveButton(getResources().getString(R.string.ok),null);
//        dialogBuilder.show();
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = (int) (DensityUtil.getScreenWidth(this) * 0.85f);
        dialog.getWindow().setAttributes(layoutParams);

    }

    @Override
    public void showToast(boolean isLong, String msg) {
        hideLoadingDialog();
        if (isLong){
            ToastUtil.longShow(this,msg);
        }else {
            ToastUtil.show(this,msg);
        }
    }

    @Override
    public void toggleShowLoading(boolean toggle) {
        if (null == loadingHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            loadingHelperController.showLoading();
        } else {
            loadingHelperController.restore();
        }
    }

    @Override
    public void toggleShowEmpty(boolean toggle) {
        if (null == loadingHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            loadingHelperController.showEmpty();
        } else {
            loadingHelperController.restore();
        }
    }

    @Override
    public void toggleShowError(boolean toggle, String msg, View.OnClickListener onClickListener) {
        if (null == loadingHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            loadingHelperController.showError(msg, onClickListener);
        } else {
            loadingHelperController.restore();
        }
    }

    @Override
    public void toggleNetworkError(boolean toggle, View.OnClickListener onClickListener) {
        if (null == loadingHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            loadingHelperController.showNetworkError(onClickListener);
        } else {
            loadingHelperController.restore();
        }
    }

    @Override
    public void destory() {
        mButterKnifeUnbinder.unbind();
        NetStateReceiver.removeRegisterObserver(mNetChangeObserver);
        activityManager.finishActivity();
    }
}
