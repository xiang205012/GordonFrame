package com.gean.businessassistant.strategy;

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 仅仅请求缓存策略
 * okhttp的缓存由返回的header 来决定。如果服务器支持缓存的话返回的headers里面会有这一句
    ”Cache-Control”，“max-age=time”
    这里的time是一个单位为秒的时间长度。意思是缓存的时效，比如要设置这个API的缓存时效为一天
    返回的header就应该是
    ”Cache-Control”，“max-age=3600*24”
    不巧。公司的服务器不支持缓存的，怎么看出来的？查看服务器的返回的headers是否包含这些
 */
public class CacheStrategy implements BaseRequestStrategy {

    private static final float MAX_STALE = 60 * 60 * 24 * 30l;//过期时间为30天
    private float mMaxStale;//缓存过期时间

    public CacheStrategy(){
        mMaxStale = MAX_STALE;
    }

    public CacheStrategy(float maxStale){
        this.mMaxStale = maxStale;
    }

    /**
     * 请求策略
     * @param chain
     * @return
     */
    @Override
    public Response request(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        // {{ 第一种缓存策略：服务器返回的headers里面包含”Cache-Control”，“max-age=time”
        request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();//没有网络，直接读取缓存
        Response response = chain.proceed(request);
        response.newBuilder()// only-if-cached完全使用缓存，如果命中失败，则返回503错误
                .header("Cache-Control", "public, only-if-cached, max-stale=" + mMaxStale)
                .removeHeader("Pragma")
                .build();
        // }}

        // {{ 第二种缓存策略：服务器返回的headers里面不包含”Cache-Control”，“max-age=time”
        // 截获headers然后移除默认的Cache-Control
        // 但是我们知道有些API返回的数据适合缓存，而有些是不适合的，
        // 比如资讯列表，各种更新频率比较高的，是不可以缓存的，而像资讯详情这种数据是可以缓存的。
        // 所以我们不能直接统一写死。需要动态配置。同样的，我们也在header里面作文章，自定义一个header。
        // 注意这个header一定不能被其他地方使用，不然会被覆盖值。这里我们定义的header的key名字为：Cache-Time。
        // 我们在拦截器里去取这个header。如果取得了不为空的值，说明这个请求是要支持缓存的，
        // 缓存的时间就是Cache-Time对应的值。我们把他添加进去。
            //Response response = chain.proceed(request);
            //String cache = request.header("Cache-Time");
            //if (!TextUtils.isEmpty(cache)) {//缓存时间不为空
            //    Response response1 = response.newBuilder()
            //            .removeHeader("Pragma")
            //            .removeHeader("Cache-Control")
            //            //cache for cache seconds
            //            .header("Cache-Control", "max-age="+cache)
            //            .build();
            //    return response1;
            //} else {
            //    return response;
            //}
        // 好了，现在我们如果哪里需要缓存数据的话，只要在请求里添加header（“Cache-Time”，“3600*24”）就可以把当前数据缓存一天啦
        // 可以在TokenInterceptor里面通过一个isNeedCache判断动态添加,当请求完后注意将isNeedCache = !isNeedCache
        // }}
        return response;
    }
}
