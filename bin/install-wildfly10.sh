#!/usr/bin/env bash

cd /opt/
if [ ! -f ./wildfly-10.0.0.Final.tar.gz ]; then
    wget http://download.jboss.org/wildfly/10.0.0.Final/wildfly-10.0.0.Final.tar.gz
fi
tar -xvzf wildfly-10.0.0.Final.tar.gz
mv wildfly-10.0.0.Final wildfly
chmod -R 777 wildfly
