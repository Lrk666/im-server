package com.lrk.im.base.vo;

import com.lrk.im.base.exception.ResultStatus;

import lombok.Data;

@Data
public class ResultMessage {

	/**
	 * 返回码
	 * 0 成功 其他为出现异常
	 */
	private Integer code;
	
	/**
	 * 返回信息
	 */
	private String message;
	
	/**
	 * 返回数据
	 */
	private Object data;
	
}
