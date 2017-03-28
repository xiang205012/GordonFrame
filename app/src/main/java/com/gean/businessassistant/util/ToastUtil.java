package com.gean.businessassistant.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by gordon on 2016/7/9.
 */
public class ToastUtil {

    private static Toast mToast;

    public static void show(Context context, String msg){
        if(mToast == null){
            mToast = Toast.makeText(context,"", Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }

    public static void longShow(Context context, String msg){
        if(mToast == null){
            mToast = Toast.makeText(context,"", Toast.LENGTH_LONG);
        }
        mToast.setText(msg);
        mToast.show();
    }


}
