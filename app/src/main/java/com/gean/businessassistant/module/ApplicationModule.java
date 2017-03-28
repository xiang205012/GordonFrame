package com.gean.businessassistant.module;

import com.gean.businessassistant.base.ActivityManager;
import com.gean.businessassistant.base.BAApplication;
import com.gean.businessassistant.http.HttpControl;
import com.gean.businessassistant.http.HttpControlManager;
import com.gean.businessassistant.ui.Navigator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by gordon on 2017/3/8
 */
@Module
public class ApplicationModule {

    private final BAApplication baApplication;

    public ApplicationModule(BAApplication baApplication) {
        this.baApplication = baApplication;
    }

    @Singleton
    @Provides
    BAApplication providesApplicationContext(){
        return this.baApplication;
    }

    @Singleton
    @Provides
    ActivityManager providesActivityManager(){
        return new ActivityManager();
    }

    @Singleton
    @Provides
    Navigator providesNavigator(){
        return new Navigator();
    }

    @Singleton
    @Provides
    HttpControl providesHttpControl(HttpControlManager httpControlManager) {
        return new HttpControl(httpControlManager);
    }

    @Singleton
    @Provides
    HttpControlManager providesHttpControlManager() {
        return new HttpControlManager(this.baApplication);
    }


}
