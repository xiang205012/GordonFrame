package com.gean.businessassistant.subscribe;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.gean.businessassistant.R;
import com.gean.businessassistant.base.BAApplication;
import com.gean.businessassistant.netstatus.NetUtils;
import com.gean.businessassistant.util.ResourceUtils;

import rx.Subscriber;

public abstract class DefaultSubscrible<T> extends Subscriber<T> {

    public static final int TYPE_ERROR_NO_NETWORK = 0;
    public static final int TYPE_ERROR_TIME_OUT = 1;
    public static final int TYPE_ERROR_DISCONNECT = 2;
    public static final int TYPE_ERROR_REQUEST = 3;

    protected abstract void onError(int i);

    public void onStart() {
        super.onStart();
        if (!NetUtils.isNetworkAvailable(BAApplication.getApplication())) {
            onError(0);
            onCompleted();
            AlertDialog.Builder builder = new AlertDialog.Builder(BAApplication.getApplication());
            View view = LayoutInflater.from(BAApplication.getApplication()).inflate(R.layout.btn_no_net_dialog, null);
            builder.setView(view);
            final AlertDialog dialog = builder.create();
            Button button = (Button) view.findViewById(R.id.btn_ok);
            ((TextView) view.findViewById(R.id.tv_tip_info)).setText(ResourceUtils.getString(R.string.netWork_connecting_failed));
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            dialog.show();
        }
    }

    public void onCompleted() {
        if (!isUnsubscribed()) {
            unsubscribe();
        }
    }

    public void onError(Throwable e) {
        // TODO 如果调用了toggleShowLoading(true)一定要将view还原或显示空界面(baseView.toggleShowEmpty();)
        // TODO 当调用了toggleShowLoading(true)后可以直接调用其他三个方法做替换，
        // TODO 如需要还原到最初的view只需要调用toggleShowLoading(false),而不用担心调用了其他三个方法后无法还原到最初的view
        //baseView.toggleShowLoading(false);
//        if (e instanceof SocketTimeoutException) {
//            LogUtils.d("请求超时！");
//            Toast.makeText(SuuzeApplication.getApplication(), ResourceUtils.getString(R.string.netTimeOut), Toast.LENGTH_SHORT).show();
//            onError(1);
//        } else if (e instanceof ConnectException) {
//            LogUtils.d("网络连接中断！");
//            Toast.makeText(SuuzeApplication.getApplication(), ResourceUtils.getString(R.string.netDisconnect), Toast.LENGTH_SHORT).show();
//            onError(2);
//        } else {
//            LogUtils.d("Http ERROR : " + e.getMessage());
//            onError(3);
//        }
//        LogUtils.getExceptionStackTrace((Exception) e);
    }
}
