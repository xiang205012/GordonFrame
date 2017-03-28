package com.gean.businessassistant.subscribe;

/**
 * Created by gordon on 2017/3/10
 */

public class LoginSubscrible extends DefaultSubscrible<String> {


    @Override
    protected void onError(int i) {
        // TODO 如果调用了toggleShowLoading(true)一定要将view还原或显示空界面(baseView.toggleShowEmpty();)
        //baseView.toggleShowLoading(false);
    }

    @Override
    public void onNext(String s) {

    }
}
