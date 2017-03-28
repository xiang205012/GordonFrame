package com.gean.businessassistant.presenter;

import com.gean.businessassistant.contract.MainContract;

/**
 * Created by gordon on 2017/3/10
 */

public class MainPresenter implements MainContract.MainPresenter {

    private MainContract.MainView mainView;

    public MainPresenter(MainContract.MainView mainView){
        this.mainView = mainView;
    }

    @Override
    public void getUserInfo() {
        mainView.toggleShowLoading(true);
    }

    public void showEmpty(){
        mainView.toggleShowEmpty(true);
    }

    public void restoreMainView(){
        mainView.toggleShowLoading(false);
    }

    @Override
    public void destory() {

    }
}
