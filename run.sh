#!/bin/bash/ -ex

## Run with a 1 to compile only
## Run with a 2 to run only
## Default is clean --> compile --> run

if [ "$1" = "1" ]; then
    mvn clean
    mvn compile
elif [ "$1" = "2" ]; then
    export MAVEN_OPTS=-Dprism.order=sw;
    mvn -e exec:java -q -Dexec.mainClass="cs1302.arcade.ArcadeDriver"
else
    mvn clean
    mvn compile
    export MAVEN_OPTS=-Dprism.order=sw;
    mvn -e exec:java -q -Dexec.mainClass="cs1302.arcade.ArcadeDriver"
fi

# mvn -e -Dprism.order=sw exec:java -q -Dexec.mainClass="cs1302.arcade.ArcadeDriver"

