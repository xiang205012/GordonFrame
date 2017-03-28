package com.gean.businessassistant.contract;

/**
 * Created by gordon on 2017/3/10
 */

public interface MainContract {

    interface MainView extends BaseView{

    }

    interface MainPresenter extends BasePresenter{
        void getUserInfo();
    }



}
