#!/usr/bin/sh
echo " -start setup"
pidlist=`ps -ef | grep 'netty' | grep -v grep |  awk '{print $2}'`

if [ "$pidlist" = "" ]
then
    echo " -No program is running"
else
    echo " -There is a program running in pid $pidlist."
    echo " -There will stop the pid $pidlist !"
    kill -9 $pidlist
    echo " -The program is stopped!"
fi
echo " -There will be startup a new program!"
setsid java -jar netty_study-1.0-SNAPSHOT.jar > log/log.log 2>&1 &
echo " -netty_study-1.0-SNAPSHOT startup success"


