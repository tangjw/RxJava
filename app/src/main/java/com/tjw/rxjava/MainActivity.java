package com.tjw.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {
	
	private static final String TAG = "RxJava";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void click(View view) {
		rxJava();
		rxJava1();
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
						Log.i(TAG, "call");
						subscriber.onNext("Hello, RxJava!");
						subscriber.onCompleted();
					}
				}
		);
		
		// 创建一个Subscriber(订阅者 用来观察事件源)来处理Observable(可观察的事件源)对象发出的字符串。
		Subscriber<String> stringSubscriber = new Subscriber<String>() {
			
			@Override
			public void onCompleted() {
				Log.i(TAG, "onCompleted");
			}
			
			@Override
			public void onError(Throwable e) {
				Log.i(TAG, "onError");
			}
			
			@Override
			public void onNext(String s) {
				Log.i(TAG, "onNext");
				Log.d(TAG, s);
			}
		};
		
		// 可观察者让订阅者订阅
		stringObservable.subscribe(stringSubscriber);
		
	}
	
	/**
	 * RxJava内置了很多简化创建Observable对象的函数，
	 * 比如Observable.just就是用来创建只发出一个事件就结束的Observable对象，
	 */
	private void rxJava1() {
		
		// 上面创建Observable对象的代码可以简化为一行
		Observable<String> stringObservable = Observable.just("Hello, RxJava!");
		
		// 简化Subscriber，上面的例子中，我们其实并不关心OnComplete和OnError，
		// 我们只需要在onNext的时候做一些处理，这时候就可以使用Action1类。
		Action1<String> onNextAction = new Action1<String>() {
			@Override
			public void call(String s) {
				Log.d(TAG, s);
			}
		};
		
		// subscribe方法有一个重载版本，接受三个Action1类型的参数，
		// 分别对应OnNext，OnComplete， OnError函数。
		// myObservable.subscribe(onNextAction, onErrorAction, onCompleteAction);
		// 这里我们并不关心onError和onComplete，所以只需要第一个参数就可以
		stringObservable.subscribe(onNextAction);
		
		//链式写法
		Observable.just("Hello, RxJava!")
				.subscribe(new Action1<String>() {
					@Override
					public void call(String s) {
						Log.d(TAG, s);
					}
				});
		
	}
}
