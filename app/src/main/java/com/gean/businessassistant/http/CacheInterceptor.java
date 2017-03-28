package com.gean.businessassistant.http;

import com.gean.businessassistant.strategy.NetworkCacheStrategy;
import com.gean.businessassistant.strategy.RequestStrategy;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 缓存数据拦截器
 */
public class CacheInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        RequestStrategy requestStrategy = new RequestStrategy();
        requestStrategy.setBaseRequestStrategy(new NetworkCacheStrategy());
        return requestStrategy.request(chain);
    }
}
