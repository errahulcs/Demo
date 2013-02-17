#!/bin/bash

###############################
#1) Make sure java is installed and paths are set
#2) install maven3
#3) install apache tomcat 6
#4) create the ssh key and upload it on bitbucket
#5) make sure code is checked out and has ssh git pull setup
###############################
if [ $# -ne 2 ];
then
    echo "Error: Invalid number of arguments"
    echo "Usage: bash deployment.sh <codeDir> <apacheDir>"
    exit
fi

codeDir=$1
apacheDir=$2
cd $codeDir
git pull
mvn clean
mvn package -DskipTests
if $apacheDir/bin/shutdown.sh ; then
echo "Successfully shutdown"
else
echo "shutdown fail. Skipping and moving ahead"
fi
rm -rf $apacheDir/webapps/CookedSpecially*
cp target/CookedSpecially.war $apacheDir/webapps
$apacheDir/bin/startup.sh
