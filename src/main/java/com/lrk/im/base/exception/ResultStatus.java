package com.lrk.im.base.exception;

public enum ResultStatus {

	//系统请求成功
	SUCCESS(200),
	
	//服务器异常
	SERVER_ERROR(500),
	
	//自定义异常
	CUSTOM_ERROR(300),
	
	//参数异常
	NOT_EXTENDED(510);
	

	private ResultStatus(Integer code) {
		this.code=code;
	}
	
	private Integer code;
	
	public Integer getCode() {
		return code;
	}
	
	
}
