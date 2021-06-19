package com.lrk.im.base.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.alibaba.fastjson.JSON;
import com.lrk.im.base.vo.ResultMessage;

@ControllerAdvice
@RestControllerAdvice
public class BusiExceptionHandler extends ResponseEntityExceptionHandler implements ResponseBodyAdvice<Object>{

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		if (ex instanceof MethodArgumentNotValidException) {
			ResultMessage resultMessage=new  ResultMessage();
			resultMessage.setCode(ResultStatus.CUSTOM_ERROR.getCode());
			resultMessage.setMessage(((MethodArgumentNotValidException) ex).getBindingResult().getFieldError().getDefaultMessage());
			return new ResponseEntity<Object>(resultMessage,HttpStatus.OK);
		}
		ResultMessage resultMessage=new  ResultMessage();
		resultMessage.setCode(ResultStatus.NOT_EXTENDED.getCode());
		resultMessage.setMessage("参数校验异常");
		return new ResponseEntity<Object>(resultMessage,HttpStatus.OK);
	}
	
	@ExceptionHandler(value = BusinessException.class)
	@ResponseBody
	public ResultMessage businessExectionHandler(HttpServletRequest req,BusinessException e) {
		ResultMessage message=new ResultMessage();
		message.setCode(ResultStatus.CUSTOM_ERROR.getCode());
		message.setMessage(e.getErrorInfo());
		return message;
	}
	
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ResultMessage exectionHandler(HttpServletRequest req,Exception e) {
		ResultMessage message=new ResultMessage();
		message.setCode(ResultStatus.SERVER_ERROR.getCode());
		message.setMessage("服务器异常~");
		return message;
	}
	
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}
	
	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		if (body != null) {
			if (body instanceof String) {
				ResultMessage message=new ResultMessage();
				message.setCode(ResultStatus.SUCCESS.getCode());
				message.setMessage("操作成功");
				message.setData(body);
				return JSON.toJSONString(message);
			}
			if (body instanceof ResultMessage) {
				return body;
			}
		}
			ResultMessage message=new ResultMessage();
			message.setCode(ResultStatus.SUCCESS.getCode());
			message.setMessage("操作成功");
			message.setData(body);
		return message;
	}
	
}
