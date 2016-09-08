package com.tjw.rxjava.Http;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ^-^
 * Created by tang-jw on 9/8.
 */
public interface BaseCallback<T> extends Callback<T> {
	
	void onResponse(Call<T> call, Response<T> response);
	
	/**
	 * Invoked when a network exception occurred talking to the server or when an unexpected
	 * exception occurred creating the request or processing the response.
	 */
	void onFailure(Call<T> call, Throwable t);
	
}
