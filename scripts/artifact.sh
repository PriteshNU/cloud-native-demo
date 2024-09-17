#!/bin/bash

# Create a system user
sudo adduser csye6225 --shell /usr/sbin/nologin

# Copy artifact to owner directory
sudo cp /tmp/webapp.jar /home/csye6225/webapp.jar
sudo rm -rf /tmp/webapp.jar

# Give user the permissions to the artifact
sudo chown csye6225:csye6225 /home/csye6225/webapp.jar
sudo chmod 500 /home/csye6225/webapp.jar
sudo ls -al /home/csye6225
