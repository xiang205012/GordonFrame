package com.gean.businessassistant.component;

import com.gean.businessassistant.base.BaseActivity;
import com.gean.businessassistant.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by gordon on 2017/3/8
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(BaseActivity baseActivity);

}
