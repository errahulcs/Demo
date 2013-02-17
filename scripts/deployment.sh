#!/bin/bash

########################################################
#Make sure all steps are taken care of from README.txt #
########################################################
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
