package com.zwkj.ceng.apollo.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "redis")
public class RedissionConfigBean {

	
	private String url;
	
	private String password;
}
