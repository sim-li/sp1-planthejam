echo building plan the jam ...

##-- server build --##

mvn clean install

##-- webapp build --##
cd ./src/main/webapp/

npm install

## generate javascript docs if build script was called with option -d ##
if [ "$1" != "-n" -o "$1" != "--no-docs" ]
then
    yuidoc .
fi

## run the tests ##
node node_modules/karma/bin/karma start test/karma.conf.js  --single-run

cd ../../..

echo build of plan the jam done.
