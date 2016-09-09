package com.tjw.rxjava;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * CopyRight
 * Created by tang-jw on 2016/9/7.
 */
public class CacheInterceptor implements Interceptor {
	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();
		
		if (request.method().equals("GET")) {
			if (!NetWorkUtils.isNetWorkConnected(App.mApp)) {
				request = request.newBuilder()
						.cacheControl(CacheControl.FORCE_CACHE)
						.build();
				System.out.println("网络不可用1");
			}
			
			Response response = chain.proceed(request);
			
			if (NetWorkUtils.isNetWorkConnected(App.mApp)) {
				System.out.println("网络可用");
				//有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
				String cacheControl = request.cacheControl().toString();
				return response.newBuilder()
						.removeHeader("Pragma")
						.header("Cache-Control", cacheControl)
						.build();
				
			} else {
				System.out.println("网络不可用2");
				return response.newBuilder()
						.removeHeader("Pragma")
						.header("Cache-Control", "public, only-if-cached, max-stale=2419200")
						.build();
			}
			
		} 
			
		return chain.proceed(request);
		
	}
}
