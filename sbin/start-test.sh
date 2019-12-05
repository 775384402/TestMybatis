#!/bin/bash
source /etc/profile
#source ~/.bash_profile

cd `dirname $0`
cd ..
BIN_DIR=`pwd`
cd ..
DEPLOY_DIR=$BIN_DIR
CONF_DIR=$DEPLOY_DIR/conf
LOGS_DIR=$DEPLOY_DIR/logs

SERVER_NAME=$2
STDOUT_FILE=$LOGS_DIR/cstdout.log

if [ -z "$SERVER_NAME" ]; then
    SERVER_NAME=`hostname`
fi

if [ ! -d $LOGS_DIR ]; then
    mkdir $LOGS_DIR
fi

PIDS=`ps -ef | grep java | grep "$CONF_DIR" |awk '{print $2}'`
if [ -n "$PIDS" ]; then
    echo "ERROR: The $SERVER_NAME already started!"
    echo "PID: $PIDS"
    exit 1
fi

LIB_DIR=$DEPLOY_DIR/lib
LIB_JARS=`ls $LIB_DIR|grep .jar|awk '{print "'$LIB_DIR'/"$0}'|tr "\n" ":"`


JAVA_MEM_OPTS=""
BITS=`java -version 2>&1 | grep -i 64-bit`
if [ -n "$BITS" ]; then
    JAVA_MEM_OPTS=" -server $4 -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "
else
    JAVA_MEM_OPTS=" -server $4 -XX:SurvivorRatio=2 -XX:+UseParallelGC "
fi
echo "Conf_dir -- " $CONF_DIR 
#echo "LIB_JARS --" $LIB_JARS
echo $1
echo -e "Starting the $SERVER_NAME ...\c"
nohup java $1 $JAVA_MEM_OPTS $JAVA_DEBUG_OPTS -classpath $CONF_DIR:$LIB_JARS $3 javaconfig > $STDOUT_FILE 2>&1 &

echo "OK!"
PIDS=`ps -f | grep java | grep "$DEPLOY_DIR" | awk '{print $2}'`
echo "PID: $PIDS"
echo "STDOUT: $STDOUT_FILE"
