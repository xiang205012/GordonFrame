package com.gean.businessassistant.views.loadingView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gean.businessassistant.R;
import com.gean.businessassistant.util.DensityUtil;
import com.gean.businessassistant.views.progress.CircularProgressBar;

/**
 * Created by gordon on 2017/3/10
 */

public class LoadingHelperController {

    private ILoadingView loadingHelper;

    public LoadingHelperController(View view){
        this(new LoadingViewHelper(view));
    }

    public LoadingHelperController(ILoadingView loadingHelper){
        this.loadingHelper = loadingHelper;
    }

    public void showNetworkError(View.OnClickListener onClickListener){
        View layout = loadingHelper.inflate(R.layout.loading_error_layout);
        TextView textView = (TextView) layout.findViewById(R.id.message_info);
        textView.setText(loadingHelper.getContext().getString(R.string.common_no_network_msg));
        ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
        imageView.setImageResource(R.mipmap.ic_exception);
        if(onClickListener != null){
            layout.setOnClickListener(onClickListener);
        }
        loadingHelper.showLayout(layout);
    }

    public void showError(String errorMsg,View.OnClickListener onClickListener){
        View layout = loadingHelper.inflate(R.layout.loading_error_layout);
        TextView textView = (TextView) layout.findViewById(R.id.message_info);
        textView.setText(loadingHelper.getContext().getString(R.string.common_error_msg));
        ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
        imageView.setImageResource(R.mipmap.loading_error);
        if(onClickListener != null){
            layout.setOnClickListener(onClickListener);
        }
        loadingHelper.showLayout(layout);
    }

    public void showEmpty(){
        View layout = loadingHelper.inflate(R.layout.loading_error_layout);
        TextView textView = (TextView) layout.findViewById(R.id.message_info);
        textView.setText(loadingHelper.getContext().getString(R.string.common_empty_msg));
        ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
        imageView.setVisibility(View.GONE);
        loadingHelper.showLayout(layout);
    }

    public void showLoading(){
        View layout = loadingHelper.inflate(R.layout.loading);
        layout.setBackgroundDrawable(null);
        CircularProgressBar circularProgressBar =
                (CircularProgressBar) layout.findViewById(R.id.loading_progress);
        layout.findViewById(R.id.loading_msg).setVisibility(View.GONE);
        ViewGroup.LayoutParams layoutParams = circularProgressBar.getLayoutParams();
        layoutParams.width = DensityUtil.dp2px(loadingHelper.getContext(),60);
        layoutParams.height = DensityUtil.dp2px(loadingHelper.getContext(),60);
        loadingHelper.showLayout(layout);
    }

    public void restore(){
        loadingHelper.restoreView();
    }

}
