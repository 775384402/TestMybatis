package com.zwkj.ceng.shell;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "shell")
@Data
public class ShellConfig {

    String host;
    int port;
    String name;
    String password;
    String tarName;
    String projectName;
    String path;

}
