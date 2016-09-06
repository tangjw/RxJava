package com.tjw.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import rx.Observable;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		rxJava();
	}
	
	/**
	 * Hello World 创建一个Observable对象很简单，直接调用Observable.create即可
	 */
	private void rxJava() {
		
		//String 类型的 可观察者 被观察者 事件源
		Observable<String> stringObservable = Observable.create(
				new Observable.OnSubscribe<String>() {
					
					@Override
					public void call(Subscriber<? super String> subscriber) {
						subscriber.onNext("Hello, RxJava!");
						subscriber.onCompleted();
					}
				}
		);
		
		// 创建一个Subscriber(订阅者 用来观察事件源)来处理Observable(可观察的事件源)对象发出的字符串。
		Subscriber<String> stringSubscriber = new Subscriber<String>() {
			
			@Override
			public void onCompleted() {
				
			}
			
			@Override
			public void onError(Throwable e) {
				
			}
			
			@Override
			public void onNext(String s) {
				System.out.println(s);
			}
		};
		
		// 可观察者让订阅者订阅
		stringObservable.subscribe(stringSubscriber);
		
	}
}
