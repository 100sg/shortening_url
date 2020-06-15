#!/bin/bash

echo "##### h2 DB Download and Unzip and Run ####"
cd ..
mv shortening_url/h2.tar .
tar -zxvf h2.tar
.h2/bin/h2.sh
echo "##### h2 DB success ####"

echo "##### github source Download and Unzip and Build and Run ####"
cd shortening_url
./gradlew clean build -x test
java -jar build/libs/shorteningurl-v0.0.1.jar
echo "##### github source success ####"





