package com.gean.businessassistant.base;

import android.app.Application;

import com.gean.businessassistant.component.ApplicationComponent;
import com.gean.businessassistant.component.DaggerApplicationComponent;
import com.gean.businessassistant.module.ApplicationModule;
import com.gean.businessassistant.util.SPUtils;

/**
 * Created by gordon on 2017/3/8
 */

public class BAApplication extends Application {

    private static BAApplication application;
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        SPUtils.spInit(this);
        initComponent();
    }

    private void initComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent(){
        return this.applicationComponent;
    }

    public static BAApplication getApplication(){
        return application;
    }

}
