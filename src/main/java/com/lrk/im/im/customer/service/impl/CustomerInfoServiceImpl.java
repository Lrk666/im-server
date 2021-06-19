package com.lrk.im.im.customer.service.impl;

import com.lrk.im.base.exception.BusinessException;
import com.lrk.im.base.redis.RedisUtil;
import com.lrk.im.im.customer.entity.CustomerInfo;
import com.lrk.im.im.customer.mapper.CustomerInfoMapper;
import com.lrk.im.im.customer.service.ICustomerInfoService;
import com.lrk.im.util.ApiUtil;
import com.lrk.im.util.StringRandomUtil;
import com.lrk.im.util.email.EmailUtil;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cola
 * @since 2021-03-16
 */
@Service
@Primary
@Transactional
public class CustomerInfoServiceImpl implements ICustomerInfoService {

	@Autowired
	private RedisUtil redis;
	
	@Autowired
	private CustomerInfoMapper customerMapper;
	
	
	@Override
	public boolean addCustomer(CustomerInfo customerInfo) {
		String code=redis.get("code-"+customerInfo.getEmail()).toString();
		CustomerInfo entity=customerMapper.findByAccount(customerInfo.getAccount());
		CustomerInfo entity2=customerMapper.findByEmail(customerInfo.getEmail());
		if (entity != null) {
			throw new BusinessException("账号已存在");
		}
		if (entity2 != null) {
			throw new BusinessException("该邮箱已被使用");
		}
		entity=null;
		entity2=null;
		if (!customerInfo.getCode().equals(code)) {
			throw new BusinessException("验证码错误");
		}
		if (StringUtils.isBlank(customerInfo.getDesc())) {
			customerInfo.setDesc("没有介绍");
		}
		customerInfo.setNickName(ApiUtil.getName());
		customerInfo.setPassword(StringRandomUtil.passwordToMd5Util(customerInfo.getPassword()));
		customerInfo.setCreateDate(System.currentTimeMillis()+"");
		customerInfo.setStatus(0);
		CustomerInfo c= customerMapper.save(customerInfo);
		System.out.println(c);
		return true;
	}

	@Override
	public String loginCustomer(CustomerInfo customerInfo) {
		customerInfo.setPassword(StringRandomUtil.passwordToMd5Util(customerInfo.getPassword()));
		CustomerInfo entity=customerMapper.findByAccountAndPassword(customerInfo.getAccount(),customerInfo.getPassword());
		if (entity == null) {
			throw new BusinessException("用户名或密码输入错误");
		}
		if (entity.getStatus()==1) {
			throw new BusinessException("账号暂时不可用");
		}
		String str=entity.getId().substring(entity.getId().length()-4);
		String token=StringRandomUtil.getUUID()+str;
		//token保留三天
		redis.removePattern("*"+str);
		redis.set(token, entity.getId(), 3600*24*3L);
		return token;
	}

	@Override
	public boolean sendCode(String email) {
		try {
			CustomerInfo c=customerMapper.findByEmail(email);
			if (c != null) {
				throw new BusinessException("邮箱已被使用");
			}
			String code=StringRandomUtil.get6Num();
			redis.del("code-"+email);
			redis.set("code-"+email, code);
			EmailUtil.sendEmail(email,code);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new BusinessException("邮件发送失败😶");
		}
		return true;
	}


}
