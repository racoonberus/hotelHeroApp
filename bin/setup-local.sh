#!/usr/bin/env bash

sudo apt -y install npm
cd ./src/main/webapp/
npm install
./node_modules/.bin/gulp default
cd -
