package com.lrk.im.util;

import javax.servlet.http.HttpSession;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lrk.im.base.exception.BusinessException;
import com.lrk.im.base.redis.RedisUtil;
import com.lrk.im.im.customer.entity.CustomerInfo;
import com.lrk.im.im.customer.service.ICustomerInfoService;




/**
 * @Disc 认证token工具类 
 * @author Administrator
 * @createDate 2020年8月9日
 */
@Component
public class RequestUtils {
	
	@Autowired
	private  RedisUtil redisService;
	
	@Autowired
	private MongoTemplate mongoTemplate;

	public HttpSession getSession() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request.getSession();
	}
	
	public String getHeader(String args) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		if(StringUtils.isBlank(args)) {
			throw new BusinessException("参数为空");
		}
		return request.getHeader(args);
	}
	
	public String getUserIdByToken() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		if(redisService==null) {
			throw new BusinessException("redis连接错误!");
		}
		String userId = "";
			userId = (String) redisService.get(request.getHeader("token"));
			if(StringUtils.isBlank(userId)) {
				throw new BusinessException("token is null!");
			}
		return userId;
	}
	
	public CustomerInfo getUserByToken() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		if(redisService==null) {
			throw new BusinessException("redis连接错误!");
		}
		String userId = (String) redisService.get(request.getHeader("token"));
		if(StringUtils.isBlank(userId)) {
			throw new BusinessException("请重新登录!");
		}
		Query query=new Query(Criteria.where("id").is(userId));
		CustomerInfo user = mongoTemplate.findOne(query, CustomerInfo.class);
		return user;
	}
	
	
	
}
