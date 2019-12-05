#!/bin/bash
# Author: liujsh
# Date: 2019-12-02
# 自动发布脚本

PROJECTNAME=$1
TARNAME=$1".tar.gz"
BIN_DIR=`pwd`

echo "BIN_DIR = $BIN_DIR"

# 查看打包文件是否存在
if [ -f "$TARNAME" ];then
	echo "$TARNAME 存在"
else
	echo "$TARNAME 不存在"
fi

# 查看目前运行的pid
PIDS=`ps -ef | grep java |grep "$PROJECTNAME" |awk '{print $2}'`
if [ -n "$PIDS" ]; then
    echo "ERROR: The $PROJECTNAME already started!"
    echo "PID: $PIDS"
    exit 1
fi

# -- 多实例执行out_of_service，判断action log 情况

# kill pid

if [ -n "$PIDS" ]; then
  for pid in $PIDS
    do
      kill -9 pid
    done
fi


# rm -rf 删除原解压的文件夹
rm -rf $PROJECTNAME

# tar -xvf 解压文件
tar -xvf $TARNAME

# -- sh sh_mock/mock-start-

# 提示查看启动日志
chmod -R 777  $BIN_DIR/start-test.sh
$BIN_DIR/start-test.sh " -Dspring.jndi.ignore=true  -Dlog.dir=/var/log/app -Dserver.port=11011" $PROJECTNAME com.zwkj.ceng.App "-Xmx500m -Xms500m -Xmn400m"
