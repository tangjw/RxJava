package com.tjw.rxjava;

import java.io.IOException;

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
		Response response = chain.proceed(request);
		return response.newBuilder()
				.removeHeader("Pragma")
				.removeHeader("Cache-Control")
				//cache for 30 days
				.header("Cache-Control", "max-age=" + 3600 * 24 * 30)
				.build();
	}
}
