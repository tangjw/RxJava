package com.tjw.rxjava.bean;

/**
 * ^-^
 * Created by tang-jw on 9/8.
 */
public class BaseResponse {
	
	private int errorCode;
	private String errorMsg;
	
	public int getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}
	
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
