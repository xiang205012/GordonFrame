package com.gean.businessassistant.contract;

import android.view.View;

public interface BaseView {

    void showLoadingDialog(String str);

    void hideLoadingDialog();

    void showSimpleTipDialog(String str);

    void showToast(boolean z, String str);

    void toggleShowLoading(boolean toggle);

    void toggleShowEmpty(boolean toggle);

    void toggleShowError(boolean toggle,String msg, View.OnClickListener onClickListener);

    void toggleNetworkError(boolean toggle, View.OnClickListener onClickListener);

    void destory();
}
