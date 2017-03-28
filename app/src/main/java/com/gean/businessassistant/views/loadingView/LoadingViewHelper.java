package com.gean.businessassistant.views.loadingView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gordon on 2017/3/10
 */

public class LoadingViewHelper implements ILoadingView {
    /**Activity中getLoadingTargetView()返回的view*/
    private View targetView;
    private ViewGroup parentView;
    private int viewIndex;
    private ViewGroup.LayoutParams params;

    private View currentView;

    public LoadingViewHelper(View view){
        this.targetView = view;
    }

    @Override
    public View getCurrentLayout() {
        return currentView;
    }

    @Override
    public void restoreView() {
        showLayout(targetView);
    }

    /**
     * 用于targetView和传入的这个view之间替换
     * @param view
     */
    @Override
    public void showLayout(View view) {
        if(parentView == null){
            init();
        }
        currentView = view;
        if(parentView.getChildAt(viewIndex) != view){
            ViewGroup parent = (ViewGroup) view.getParent();
            if(parent != null){
                parent.removeView(view);
            }
            parentView.removeViewAt(viewIndex);
            parentView.addView(view,viewIndex,params);
        }
    }

    private void init() {
        params = targetView.getLayoutParams();
        if(targetView.getParent() != null){
            parentView = (ViewGroup) targetView.getParent();
        }else{
            parentView = (ViewGroup) targetView.getRootView().findViewById(android.R.id.content);
        }
        int count = parentView.getChildCount();
        for (int index = 0;index < count;index++){
            if(targetView == parentView.getChildAt(index)){
                viewIndex = index;
                break;
            }
        }
        currentView = targetView;
    }

    @Override
    public View inflate(int layoutId) {
        return LayoutInflater.from(getContext()).inflate(layoutId,null);
    }

    @Override
    public Context getContext() {
        return targetView.getContext();
    }

    @Override
    public View getView() {
        return targetView;
    }
}
