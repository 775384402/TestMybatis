#!/bin/bash
chmod -R 777  /usr/cceng/test/TestMybatis-1.0-SNAPSHOT/myshell/start-test.sh
/usr/cceng/test/TestMybatis-1.0-SNAPSHOT/myshell/start-test.sh " -Dspring.jndi.ignore=true -Denv=uat -Dlog.dir=/var/log/app -Dserver.port=11011" TestCceng com.zwkj.ceng.App "-Xmx500m -Xms500m -Xmn400m"
