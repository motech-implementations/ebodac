#!/usr/bin/env bash

sudo apt-get update -qq

#Installation and configuration of ActiveMQ
sudo apt-get install -y activemq -qq
sudo ln -s /etc/activemq/instances-available/main /etc/activemq/instances-enabled/main
sudo sed -e 's/<broker /<broker schedulerSupport="true" /' -i /etc/activemq/instances-enabled/main/activemq.xml
sudo service activemq restart

#Instalation of staging certificate

HOSTNAME=ebodac.soldevelo.com
WORKING_DIR=/tmp/

if [ -z "$JAVA_HOME" ]; then
	echo "The JAVA_HOME environment variable is not set"
else
	if [ ! -d "$WORKING_DIR" ]; then
		echo "Invalid directory"
	else
		mkdir -p $WORKING_DIR/cert-installer
		cd $WORKING_DIR/cert-installer
		wget -O InstallCert.java https://confluence.atlassian.com/download/attachments/180292346/InstallCert.java
		javac InstallCert.java
		echo "" | java InstallCert $HOSTNAME
		sudo cp jssecacerts $JAVA_HOME/jre/lib/security
	fi
fi