package com.gean.businessassistant.ui;

import android.content.Context;

import com.gean.businessassistant.ui.activity.TwoActivity;

import javax.inject.Singleton;

/**
 * Created by gordon on 2017/3/8
 */
@Singleton
public class Navigator {

    public static void navigatorToTwoActivity(Context context){
        if (context != null) {
            context.startActivity(TwoActivity.getCallingIntent(context));
        }
    }





}
