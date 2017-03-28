package com.gean.businessassistant.views;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gean.businessassistant.R;
import com.gean.businessassistant.views.progress.CircularProgressBar;

/**
 * Created by gordon on 2017/3/9
 */

public class DialogLoadingView {

    private Dialog loadingDialog;
    private TextView tvLoadingDescription;

    public DialogLoadingView(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.loading,null);
        CircularProgressBar loadingProgressBar
                = (CircularProgressBar) view.findViewById(R.id.loading_progress);
        tvLoadingDescription = (TextView) view.findViewById(R.id.loading_msg);

        loadingDialog = new Dialog(context,R.style.LoadingDialog);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setContentView(view,new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));

    }

    public void setLoadingTip(String tip){
        tvLoadingDescription.setText(tip);
        loadingDialog.show();
    }

    public boolean isShowing(){
        return loadingDialog.isShowing();
    }

    public void dismiss(){
        loadingDialog.dismiss();
    }

}
