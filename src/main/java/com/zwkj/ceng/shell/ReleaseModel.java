package com.zwkj.ceng.shell;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by liujsh on 2019/12/5
 */
@Getter
@Setter
public class ReleaseModel {

    // ssh host
    private String host;
    // ssh port
    private int port;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 项目压缩文件和项目解压目录
    private String path;
    // 启动脚本路径
    private String shPath;
    // 项目名称
    private String projectName;
    // 压缩文件名称
    private String compressedName;
    // 启动脚本
    private String startSh;

}
