#!/usr/bin/env bash

cd ./src/main/webapp/
npm install
./node_modules/.bin/gulp
gulp default
cd -
