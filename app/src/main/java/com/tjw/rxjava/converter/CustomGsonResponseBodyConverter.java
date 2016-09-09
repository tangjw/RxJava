package com.tjw.rxjava.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.tjw.rxjava.bean.BaseResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * CopyRight
 * Created by tang-jw on 2016/9/9.
 */
final class CustomGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
	
	private final Gson gson;
	private final TypeAdapter<T> adapter;
	
	CustomGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
		this.gson = gson;
		this.adapter = adapter;
	}
	
	@Override
	public T convert(ResponseBody value) throws IOException {
		String response = value.string();
		BaseResponse baseResponse = gson.fromJson(response, BaseResponse.class);
		/*if (baseResponse.getErrorCode() < 0) {
			value.close();
			throw new ApiException(httpStatus.getCode(), httpStatus.getMessage());
		}*/
		
		MediaType contentType = value.contentType();
		Charset charset = contentType != null ? contentType.charset(Charset.defaultCharset()) : Charset.defaultCharset();
		InputStream inputStream = new ByteArrayInputStream(response.getBytes());
		Reader reader = new InputStreamReader(inputStream, charset);
		JsonReader jsonReader = gson.newJsonReader(reader);
		
		try {
			return adapter.read(jsonReader);
		} finally {
			value.close();
		}
	}
	
}
