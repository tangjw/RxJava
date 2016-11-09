package com.tjw.rxjava.demo;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tjw.rxjava.R;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Android on 2016/11/9.
 */

public class RxJava1 extends AppCompatActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rxjava1);
	}
	
	public void click(View view) {
		Observable<String> observable1 = Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {
				subscriber.onNext("On");
				subscriber.onNext("Off");
				subscriber.onNext("On");
				subscriber.onNext("On");
				subscriber.onCompleted();
			}
		});
		
		Observable<String> observable2 = Observable.just("On", "Off", "On", "On");
		
		String [] kk={"On","Off","On","On"};
		Observable observable3=Observable.from(kk);
		
		
		Subscriber<String> subscriber = new Subscriber<String>() {
			@Override
			public void onCompleted() {
				System.out.println("onCompleted");
			}
			
			@Override
			public void onError(Throwable e) {
				System.out.println("onError");
			}
			
			@Override
			public void onNext(String s) {
				System.out.println("onNext");
				System.out.println(s);
			}
		};
		
		Action1<String> action1 = new Action1<String>() {
			@Override
			public void call(String s) {
				System.out.println("call");
				System.out.println(s);
			}
		};
		
//		observable2.subscribe(action1);
		
		// 流式API调用风格,
//		Observable.just("2", "1", "3", "4")
//				.filter(new Func1<String, Boolean>() {
//					@Override
//					public Boolean call(String s) {
//						return s != null;
//					}
//				})
//				.subscribe(subscriber);
		
//		rxChangeThread();
		
	}
	
	/**
	 * RxJava 切换线程 map操作符修改
	 */
	private void rxChangeThread() {
		Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {
				System.out.println("Observable Thread.name --> "+Thread.currentThread().getName());
				subscriber.onNext("2");
				subscriber.onNext("1");
				subscriber.onNext("3");
				subscriber.onNext("4");
				subscriber.onCompleted();
			}
		})
//				.subscribeOn(Schedulers.newThread())
				.observeOn(Schedulers.io())
				.map(new Func1<String, Boolean>() {
					@Override
					public Boolean call(String s) {
						SystemClock.sleep(2000L);
						System.out.println("map Thread.name --> "+Thread.currentThread().getName());
						return s.equals("1");
					}
				})
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<Boolean>() {
					@Override
					public void onCompleted() {
						
					}
					
					@Override
					public void onError(Throwable e) {
						
					}
					
					@Override
					public void onNext(Boolean aBoolean) {
						System.out.println("Subscriber Thread.name --> "+Thread.currentThread().getName());
						System.out.println(aBoolean);
					}
				});
	}
	
	private void getFilePath() {
		
	}
}
