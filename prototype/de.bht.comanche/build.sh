#!/bin/sh

if [ "$1" = "-h" -o "$1" = "--help" ]
then
    echo
    echo "Usage: build.sh [OPTION]"
    echo
    echo "Options:"
    echo "  -h, --help     display this help and exit"
    echo "  -n, --no-docs  build without documentation generation for the webapp"
    exit 0
fi


echo building plan the jam ...

###-- server build --###

mvn clean install

###-- webapp build --###
cd ./src/main/webapp/

npm install

## generate webapp docs ##
if [ "$1" = "-n" -o "$1" = "--no-docs" ]
then
    echo skipped documentation generation for the webapp ...
else
    yuidoc .
fi

## run the tests ##
node node_modules/karma/bin/karma start test/karma.conf.ci.js  --single-run

cd ../../..

echo build of plan the jam done.
