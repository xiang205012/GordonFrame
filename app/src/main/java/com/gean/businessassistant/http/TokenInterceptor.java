package com.gean.businessassistant.http;


import com.gean.businessassistant.base.Constants;
import com.gean.businessassistant.util.SPUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by gordon on 2017/03/07
 */

public class TokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String token = (String) SPUtils.get(Constants.USER_TOKEN,null);

        if(token == null || alreadyHasAuthorizationHeader(request)){
//            if(isTokenExpired(chain.proceed(request))){
//                String newToken = getNewToken();
//                Request newRequest = chain.request()
//                        .newBuilder()
//                        .header("Authorization","Token" + " " + newToken)
//                        .build();
//                // 重新请求
//                Log.d("TAG","  refresh token");
//                return chain.proceed(newRequest);
//            }
            return chain.proceed(request);
        }
        Request authorised = request.newBuilder()
                .header("Authorization", "Token" + " " + token)
                .build();
        return chain.proceed(authorised);
    }

    /**获取新token*/
//    private String getNewToken() throws IOException {
//        String name = (String) SPUtils.getsMakeInvoice(Constants.userName);
//        String psw = (String) SPUtils.getsMakeInvoice(Constants.userPsw);
//        RestApi restApi = HttpControlManager.createRestApi(RestApi.class);
//        Call<String> result = restApi.callLogin(name,psw);
//        // 需用同步请求
//        String newToken = result.execute().body();
//        SPUtils.saveMakeInvoice(Constants.userToken,newToken);
//        return newToken;
//    }

    /**判断Token是否过期*/
    private boolean isTokenExpired(Response response) {
        if(response.code() == 404){// 与服务器约定的过期码
            return true;
        }
        return false;
    }


    private boolean alreadyHasAuthorizationHeader(Request request) {
        if(request != null){
            if(request.header("Authorization") != null){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

}
