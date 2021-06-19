package com.lrk.im.im.customer.mapper;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lrk.im.im.customer.entity.CustomerInfo;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cola
 * @since 2021-03-16
 */
public interface CustomerInfoMapper extends MongoRepository<CustomerInfo, String>{

	CustomerInfo findByAccountAndPassword(String account,String password);
	
	CustomerInfo findByEmail(String email);
	
	CustomerInfo findByAccount(String account);
}
