package com.zwkj.ceng.apollo.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by mark on 2017/11/6.
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "dataSource")
public class DataSourceConfigBean {

    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private Integer initialSize;
    private Integer minIdle;
    private Integer maxActive;
    private Integer maxWait;

}