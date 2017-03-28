package com.gean.businessassistant.views.loadingView;

import android.content.Context;
import android.view.View;

/**
 * Created by gordon on 2017/3/10
 */

public interface ILoadingView {

    View getCurrentLayout();

    void restoreView();

    void showLayout(View view);

    View inflate(int layoutId);

    Context getContext();

    View getView();


}
