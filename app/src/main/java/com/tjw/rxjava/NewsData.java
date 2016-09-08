package com.tjw.rxjava;

/**
 * CopyRight
 * Created by tang-jw on 2016/9/8.
 */
public class NewsData {
	
	/**
	 * msgCount : 0
	 */
	
	private DataBean data;
	/**
	 * data : {"msgCount":0}
	 * errorCode : 0
	 */
	
	private int errorCode;
	
	public DataBean getData() {
		return data;
	}
	
	public void setData(DataBean data) {
		this.data = data;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public static class DataBean {
		private int msgCount;
		
		public int getMsgCount() {
			return msgCount;
		}
		
		public void setMsgCount(int msgCount) {
			this.msgCount = msgCount;
		}
	}
}
