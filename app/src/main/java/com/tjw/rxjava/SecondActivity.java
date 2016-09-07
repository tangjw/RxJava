package com.tjw.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class SecondActivity extends AppCompatActivity {
	
	private static final String TAG = "RxJava";
	private OkHttpClient mOkHttpClient;
	private Request mRequest;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initOkhttp();
	}
	
	private void initOkhttp() {
		
		mOkHttpClient = new OkHttpClient().newBuilder()
				.connectTimeout(5000L, TimeUnit.MILLISECONDS)
				.readTimeout(5000L, TimeUnit.MILLISECONDS)
				.cache(new Cache(new File(getCacheDir().getAbsolutePath()), 10 * 1024 * 1024))
				.build();
		
		mRequest = new Request.Builder().url("http://service.test.xgo.com.cn:8080/app/v1/demo/1/").build();
		
		final Call call = mOkHttpClient.newCall(mRequest);
		
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				
			}
			
			@Override
			public void onResponse(Call call, Response response) throws IOException {
				System.out.println("Response:-->\r\n" + response.body().string());
			}
		});
		
		/*new Thread(){
			@Override
			public void run() {
				try {
					Response response = call.execute();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();*/
		
		
	}
	
	public void click(View view) {
		rxJava();
	}
	
	/**
	 * 网络请求
	 */
	private void rxJava() {
		
		Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
			
			@Override
			public void call(Subscriber<? super String> subscriber) {
				Request request = new Request.Builder().url("http://service.test.xgo.com.cn:8080/app/v1/demo/1").build();
				Call call = mOkHttpClient.newCall(request);
				try {
					Response response = call.execute();
					subscriber.onNext(response.body().string()); 
					subscriber.onCompleted();
				} catch (IOException e) {
					subscriber.onError(new Throwable("not data"));
				}
				
			}
		}).subscribeOn(Schedulers.io());
		
		Subscriber<String> subscriber = new Subscriber<String>() {
			
			@Override
			public void onCompleted() {
				Log.i(TAG, "onCompleted");
			}
			
			@Override
			public void onError(Throwable e) {
				Log.i(TAG, "onError:"+e.toString());
			}
			
			@Override
			public void onNext(String s) {
				Log.d(TAG, "onNext:->\n"+s);
			}
		};
		
		observable.subscribe(subscriber);
		
	}
	
	
}
