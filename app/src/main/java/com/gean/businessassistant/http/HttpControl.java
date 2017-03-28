package com.gean.businessassistant.http;


import com.gean.businessassistant.api.RestApi;

import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
/**
 * Created by gordon on 2016/03/07.
 */
@Singleton
public class HttpControl {
    private HttpControlManager httpControlManager;
    private RestApi restApi;

    public HttpControl(HttpControlManager httpControlManager) {
        this.httpControlManager = httpControlManager;
        this.restApi = httpControlManager.getRestApi();
    }



    private <T> void toSubscribe(Observable<T> observable, Subscriber<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((Subscriber) subscriber);
    }

}
