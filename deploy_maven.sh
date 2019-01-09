#!/usr/bin/env bash
set -e

git branch | grep "* master"
if [ $? -ne 0 ]; then
  echo Deployment can only be done from the master branch
  exit 1
fi

echo Building Jar from protobuf and deploying it to jfrog
if [ -z "$1" ]; then
  echo Press any key to continue
  read me
fi

mvn clean deploy -s /home/jenkins/.m2/settings.xml
echo "Maven finished with status code $?"