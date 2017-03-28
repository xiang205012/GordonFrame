package com.gean.businessassistant.http;

import android.content.Context;

import com.gean.businessassistant.api.Api;
import com.gean.businessassistant.api.RestApi;
import com.gean.businessassistant.api.testJson.ScalarsConverterFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.internal.cache.*;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by gordon on 2017/03/07.
 */
@Singleton
public class HttpControlManager {

    private RestApi restApi;
    private OkHttpClient mClient;
    private Retrofit mRetrofit;

    private static final int TIMEOUT = 15;//链接超时时间,秒

    public RestApi getRestApi() {
        return restApi;
    }

    public OkHttpClient getmClient() {
        return mClient;
    }

    public Retrofit getmRetrofit() {
        return mRetrofit;
    }

    public static <T> T createRestApi(Class<T> restApi){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(Api.SERVICE_URL)
                .build();

        return retrofit.create(restApi);
    }

    public HttpControlManager(Context context){

        File cacheDirectory = new File(
                context.getCacheDir().getAbsolutePath(), "NetCache");
        Cache cache = new Cache(cacheDirectory, 10 * 1024 * 1024);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        mClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(new TokenInterceptor())
                .addNetworkInterceptor(new CacheInterceptor())
                .cache(cache)
                .build();

        mRetrofit = new Retrofit.Builder()
                .client(mClient)
                .baseUrl(Api.SERVICE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 添加Rx适配器
//                .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                .addConverterFactory(ScalarsConverterFactory.create()) // 添加自定义转换器，此时可以返回查看json
                .build();

        restApi = mRetrofit.create(RestApi.class);

    }

}
