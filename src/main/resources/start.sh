#!/usr/bin/expect
# Author: liujsh
# Date: 2019-12-02
# 自动发布脚本

# 自动登录ssh
set timeout 30
spawn ssh -p 22 root@122.51.239.204
expect "password:"
#send "zwkj121212#\r"
send "Zhangzhicheng1\r"
interact

# 切换指定目录
# cd /data/app/cloud
# 查看打包文件是否存在
if [ -f "/usr/cceng/cloud" ];then
	echo "Cceng文件夹存在"
else
	echo "Cceng文件夹不存在"
fi
# 查看目前运行的pid

# 多实例执行out_of_service，判断action log 情况

# kill pid

# rm -rf 原解压的文件夹

# tar -xvf 打包文件

# sh sh_mock/mock-start-

# 提示查看启动日志