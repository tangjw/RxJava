package com.tjw.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {
	
	private static final String TAG = "RxJava";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void click(View view) {
//		rxJava();
//		rxJava1();
		rxJava2();
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
	
	/**
	 * 变换
	 * 操作符（Operators）
	 * 操作符就是为了解决对Observable对象的变换的问题，操作符用于在Observable和最终的Subscriber
	 * 之间修改Observable发出的事件。RxJava提供了很多很有用的操作符。比如map操作符，就是用来把
	 * 把一个事件转换为另一个事件的。
	 */
	private void rxJava2() {
		// map()操作符就是用于变换 Observable对象的，map操作符返回一个 Observable 对象，这样就可以
		// 实现链式调用，在一个 Observable对象上多次使用 map操作符，最终将最简洁的数据传递给Subscriber对象。
		Observable.just("Hello, RxJava!")
				.map(new Func1<String, String>() {
					@Override
					public String call(String s) {
						return s + "--Tjw";
					}
				})
				.subscribe(new Action1<String>() {
					@Override
					public void call(String s) {
						Log.d(TAG, s);
					}
				});
		
		// map操作符进阶
		// map操作符更有趣的一点是它不必返回Observable对象返回的类型，你可以使用map操作符返回
		// 一个发出新的数据类型的observable对象。
		Observable.just("Hello, RxJava!")
				.map(new Func1<String, Integer>() {
					@Override
					public Integer call(String s) {
						return s.hashCode();
					}
				})
				.subscribe(new Action1<Integer>() {
					@Override
					public void call(Integer integer) {
						Log.d(TAG, "HashCode:-> " + integer);
					}
				});
		
		
		// Subscriber做的事情越少越好 比如再增加一个 map
		Observable.just("Hello, RxJava!")
				.map(new Func1<String, Integer>() {
					@Override
					public Integer call(String s) {
						return s.hashCode();
					}
				})
				.map(new Func1<Integer, String>() {
					@Override
					public String call(Integer integer) {
						return Integer.toString(integer);
					}
				})
				.subscribe(new Action1<String>() {
					@Override
					public void call(String s) {
						Log.d(TAG, "String HashCode:-> " + s);
					}
				});
		
	}
	
	/**
	 两点:
	 1.Observable和Subscriber可以做任何事情
	 Observable可以是一个数据库查询，Subscriber用来显示查询结果；Observable可以是屏幕上的点击事件，
	 Subscriber用来响应点击事件；Observable可以是一个网络请求，Subscriber用来显示请求结果。
	 
	 2.Observable和Subscriber是独立于中间的变换过程的。
	 在Observable和Subscriber中间可以增减任何数量的map。整个系统是高度可组合的，操作数据是一个很简单的过程。
	 */
	
	
}
