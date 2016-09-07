package com.tjw.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThirdActivity extends AppCompatActivity {
	
	private static final String TAG = "RxJava";
	private OkHttpClient mOkHttpClient;
	private Request mRequest;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mOkHttpClient = new OkHttpClient().newBuilder()
				.addInterceptor(new LoggerInterceptor("okHttp", true))
				.addNetworkInterceptor(new CacheInterceptor())
				.connectTimeout(5000L, TimeUnit.MILLISECONDS)
				.readTimeout(5000L, TimeUnit.MILLISECONDS)
				.cache(new Cache(new File(getCacheDir().getAbsolutePath()), 10 * 1024 * 1024))
				.build();
		
		
		CacheControl cacheControl = new CacheControl.Builder()
				.maxAge(0, TimeUnit.SECONDS)
				.maxStale(365, TimeUnit.DAYS)
				.build();
		
		
	}
	
	public void click(View view) {
//		retrofit();
		retrofit1();
		retrofit2();
	}
	
	private void retrofit2() {
		
		Retrofit retrofit = new Retrofit.Builder()
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())//新的配置
				.baseUrl("https://api.github.com/")
				.client(mOkHttpClient)
				.build();
		GitHubService service = retrofit.create(GitHubService.class);
		
//		service.contributor("square", "retrofit")
//				.subscribeOn(Sch)
		
		
				
	}
	
	/**
	 * Retrofit
	 */
	private void retrofit() {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("https://api.github.com/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		
		GitHubService service = retrofit.create(GitHubService.class);
		
		Call<List<Contributor>> call = service.contributors("square", "retrofit");
		
		call.enqueue(new Callback<List<Contributor>>() {
			@Override
			public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
				for (Contributor contributor : response.body()) {
					System.out.println(contributor.login + " (" + contributor.contributions + ")");
				}
			}
			
			@Override
			public void onFailure(Call<List<Contributor>> call, Throwable t) {
				
			}
		});
		
		//取消请求
//		call.cancel();
		
		
	}
	
	private void retrofit1() {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("https://api.github.com/")
				.addConverterFactory(GsonConverterFactory.create())
				.client(mOkHttpClient)
				.build();
		GitHubService service = retrofit.create(GitHubService.class);

//		Observable.create()
		Call<List<Contributor>> call = service.contributors("square", "retrofit");
		
		call.enqueue(new Callback<List<Contributor>>() {
			@Override
			public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
				for (Contributor contributor : response.body()) {
					System.out.println(contributor.login + " (" + contributor.contributions + ")");
				}
			}
			
			@Override
			public void onFailure(Call<List<Contributor>> call, Throwable t) {
				
			}
		});
		
	}
	
}
