package com.gean.businessassistant.util;

import com.gean.businessassistant.base.BAApplication;

/**
 * Created by gordon on 2016/03/08
 */

public class ResourceUtils {

    public static int getColor(int resId){
        return BAApplication.getApplication().getResources().getColor(resId);
    }

    public static String getString(int resId){
        return BAApplication.getApplication().getResources().getString(resId);
    }






}
