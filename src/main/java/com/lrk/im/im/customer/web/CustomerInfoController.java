package com.lrk.im.im.customer.web;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.lrk.im.im.customer.entity.CustomerInfo;
import com.lrk.im.im.customer.service.ICustomerInfoService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cola
 * @since 2021-03-16
 */
@RestController
@RequestMapping("/customer")
public class CustomerInfoController {

	@Autowired
	private ICustomerInfoService customerService;
	
	@PostMapping(value = "/sendCode")
	public boolean sendCode(@RequestBody CustomerInfo customerInfo) {
		return customerService.sendCode(customerInfo.getEmail());
	}
	
	@PostMapping(value = "/reg")
	public boolean register(@RequestBody @Validated CustomerInfo customerInfo) {
		return customerService.addCustomer(customerInfo);
	}
	
	@PostMapping(value = "/login")
	public String login(@RequestBody CustomerInfo customerInfo) {
		return customerService.loginCustomer(customerInfo);
	}
	
	
}

