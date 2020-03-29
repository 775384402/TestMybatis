package com.zwkj.ceng.apollo;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.zwkj.ceng.apollo.bean.DataSourceConfigBean;
import com.zwkj.ceng.apollo.bean.RedissionConfigBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zwhd on 2020/3/27
 */

@Configuration
@EnableApolloConfig(order = 2)
public class ApolloConfig {

	@Bean("dataSourceConfigBean")
	public DataSourceConfigBean dataSourceConfigBean() {
		return new DataSourceConfigBean();
	}

	@Bean("redissionConfigBean")
	public RedissionConfigBean redissionConfigBean() {
		return new RedissionConfigBean();
	}

}
