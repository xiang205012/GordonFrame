package com.gean.businessassistant.http;


import com.gean.businessassistant.bean.BaseBean;

import rx.functions.Func1;

/**
 * Created by gordon on 2017/03/07.
 */
public class HttpResultFunc<T> implements Func1<BaseBean<T>,T> {

    @Override
    public T call(BaseBean<T> tBaseBean) {
        if(!tBaseBean.status.equals("success")){
//            LogUtils.d("Api请求出错："+tBaseBean.status);
//            ToastUtil.longShow(SuuzeApplication.getApplication(),tBaseBean.status);
            return null;
        }
        return tBaseBean.data;
    }
}
