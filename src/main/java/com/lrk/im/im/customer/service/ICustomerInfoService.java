package com.lrk.im.im.customer.service;

import com.lrk.im.im.customer.entity.CustomerInfo;

import org.springframework.context.annotation.Primary;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cola
 * @since 2021-03-16
 */
public interface ICustomerInfoService{

	//注册用户
	boolean addCustomer(CustomerInfo customerInfo);
	
	//用户登录
	String loginCustomer(CustomerInfo customerInfo);
	
	//发送邮箱验证码
	boolean sendCode(String email);
	
	
}
