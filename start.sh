#!/bin/bash

echo "##### h2 DB Run start ####"
./../h2/bin/h2.sh &
echo "##### h2 DB Run end ####"

echo "##### source Build and Run ####"
./gradlew clean build -x test
java -jar build/libs/shorteningurl-v0.0.1.jar
echo "##### github source success ####"





