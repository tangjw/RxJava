package com.tjw.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.tjw.rxjava.HttpApi.PartyService;
import com.tjw.rxjava.bean.StudyMaterials;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForthActivity extends AppCompatActivity {
	
	private static final String TAG = "RxJava";
	private OkHttpClient mOkHttpClient;
	private Request mRequest;
	private TextView mTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mTextView = (TextView) findViewById(R.id.textView);
		
		HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
		loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
		mOkHttpClient = new OkHttpClient().newBuilder()
				.addInterceptor(loggingInterceptor)
				.addInterceptor(new CacheInterceptor())
				.connectTimeout(5000L, TimeUnit.MILLISECONDS)
				.readTimeout(5000L, TimeUnit.MILLISECONDS)
				.cache(new Cache(new File(getCacheDir().getAbsolutePath()), 10 * 1024 * 1024))
				.build();
	}
	
	public void click(View view) {
//		retrofit();
		retrofit1();
	}
	
	private void retrofit1() {
		//http://118.145.26.214:8086/lianyi/MtsNews/getNews.do
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("http://118.145.26.214:8086/")
				.addConverterFactory(GsonConverterFactory.create())
				.client(mOkHttpClient)
				.build();
		
		retrofit.create(PartyService.class)
				.getStudyMaterials()
				.enqueue(new Callback<StudyMaterials>() {
					@Override
					public void onResponse(Call<StudyMaterials> call, Response<StudyMaterials> response) {
						System.out.println(Thread.currentThread().getName());
						mTextView.setText(response.body().getData().get(0).getName());
					}
					
					@Override
					public void onFailure(Call<StudyMaterials> call, Throwable t) {
						
					}
				});
	}
	
	/**
	 * Retrofit
	 */
	private void retrofit() {
		//http://118.145.26.214:8086/lianyi/MtsNews/getNews.do
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("http://192.168.1.233:8080/")
				.addConverterFactory(GsonConverterFactory.create())
				.client(mOkHttpClient)
				.build();
		GitHubService service = retrofit.create(GitHubService.class);
		
		Call<NewsData> call = service.newsList();
		
		call.enqueue(new Callback<NewsData>() {
			@Override
			public void onResponse(Call<NewsData> call, Response<NewsData> response) {
				System.out.println("线程名:" + Thread.currentThread().getName() + "\n错误码:" + response.body().getErrorCode());
			}
			
			@Override
			public void onFailure(Call<NewsData> call, Throwable t) {
				System.out.println("error");
			}
		});
		
		//取消请求
//		call.cancel();
		
	}
	
	
}
