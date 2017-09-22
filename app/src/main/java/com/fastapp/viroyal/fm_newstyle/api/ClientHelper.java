package com.fastapp.viroyal.fm_newstyle.api;

import com.fastapp.viroyal.fm_newstyle.util.NetWorkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hanjiaqi on 2017/6/29.
 */

public class ClientHelper {
    public Interceptor getAutoCacheInterceptor(){
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if(!NetWorkUtils.hasNet()){
                    request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                }

                Response response = chain.proceed(request);
                if(NetWorkUtils.hasNet()){//有网络情况下，根据请求接口的设置
                    String cacheControl = request.cacheControl().toString();
                    if(cacheControl != null){//服务端设置了缓存策略，则使用服务端缓存策略
                        return response.newBuilder().header("Cache-Control", cacheControl)
                                .removeHeader("Pragma")
                                .build();
                    } else {//服务端未设置缓存策略，则客户端自行设置
                        return response.newBuilder().header("Cache-Control" , "public, max-age=36000")
                                .removeHeader("Pragma")
                                .build();
                    }
                } else {//无网络
                    return response.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=3600000")
                            .removeHeader("Pragma")
                            .build();
                }
            }
        };
    }
}
