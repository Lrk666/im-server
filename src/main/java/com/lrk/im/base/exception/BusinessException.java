package com.lrk.im.base.exception;

public class BusinessException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	private String errorCode;
	
	private String errorInfo;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	
	public BusinessException(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	
}
