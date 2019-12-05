#!/usr/bin/expect
# Author: liujsh
# Date: 2019-12-02
# 自动发布脚本

set post [lindex $argv 0]
set host [lindex $argv 1]
set passwd [lindex $argv 2]
set workpath [lindex $argv 3]
set projectname [lindex $argv 4]

# 自动登录ssh
set timeout 10
spawn ssh -p $post root@$host
expect {
  -re "yes/no" {send "yes\n";exp_continue}
  -re "password" {send "$passwd\n";exp_continue}
}
expect "#"
send "cd $workpath \r"
expect "#"
send "sh ./start-stest.sh $projectname \r"
interact