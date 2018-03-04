#!/usr/bin/env bash

cd /opt/
if [ ! -f ./wildfly-10.0.0.Final.tar.gz ]; then
    sudo wget http://download.jboss.org/wildfly/10.0.0.Final/wildfly-10.0.0.Final.tar.gz
fi
sudo tar -xvzf wildfly-10.0.0.Final.tar.gz
sudo mv wildfly-10.0.0.Final wildfly
sudo chmod -R 777 wildfly
