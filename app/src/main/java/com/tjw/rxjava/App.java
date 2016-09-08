package com.tjw.rxjava;

import android.app.Application;

/**
 * CopyRight
 * Created by tang-jw on 2016/9/8.
 */
public class App extends Application {
	
	public static App mApp;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mApp = this;
	}
}
