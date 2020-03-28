package com.zwkj.ceng.apollo;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zwhd on 2020/3/27
 */

@Configuration
@EnableApolloConfig(order = 2)
public class ApolloConfig {
}
