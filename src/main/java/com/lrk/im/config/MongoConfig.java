package com.lrk.im.config;


import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
public class MongoConfig implements ApplicationListener<ContextRefreshedEvent>{
	
	@Autowired
	  MongoTemplate mongoTemplate;
	 
	  @Override
	  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
	    MongoConverter converter = mongoTemplate.getConverter();
	    if (converter.getTypeMapper().isTypeKey("_class")) {
	      ((MappingMongoConverter) converter).setTypeMapper(new DefaultMongoTypeMapper(null));
	    }
	  }
 
}