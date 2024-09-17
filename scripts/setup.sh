#!/bin/bash

# Update system packages
sudo dnf update -y

# Disable SELINUX policy
sudo sed -i 's/^SELINUX=enforcing/SELINUX=disabled/g' /etc/selinux/config
sudo sed -i 's/^SELINUX=permissive/SELINUX=disabled/g' /etc/selinux/config
sudo setenforce 0

## Download and install OpenJDK 21
curl -O https://download.oracle.com/java/21/latest/jdk-21_linux-x64_bin.rpm
sudo dnf install jdk-21_linux-x64_bin.rpm -y
sudo rm -rf jdk-21_linux-x64_bin.rpm
echo "JAVA_HOME=\"/usr/bin/java\"" | sudo tee -a /etc/environment > /dev/null
source /etc/environment
echo java --version && javac --version

JDK_SETUP=$?
if [ $JDK_SETUP -eq 0 ]; then
  echo "OpenJDK installed successfully"
else
  echo "OpenJDK installation failed"
fi

## Install PostgreSQL
#sudo dnf install -y postgresql-server postgresql-contrib
#sudo postgresql-setup --initdb
#
#sudo sed -ri 's/^(host\s+all\s+all\s+127.0.0.1\/32\s+)ident/\1md5/' /var/lib/pgsql/data/pg_hba.conf
#sudo sed -ri 's/^(host\s+all\s+all\s+::1\/128\s+)ident/\1md5/' /var/lib/pgsql/data/pg_hba.conf
#
#sudo systemctl enable postgresql
#sudo systemctl start postgresql
#sudo systemctl status postgresql
#
#PSQL_SERVICE=$?
#if [ $PSQL_SERVICE -eq 0 ]; then
#  echo "PostgreSQL service started"
#else
#  echo "PostgreSQL service failed to start"
#fi
#
## Configure PostgreSQL
#sudo -i -u postgres psql <<EOF
#CREATE DATABASE cloud_db;
#CREATE ROLE $DB_USERNAME WITH LOGIN PASSWORD '$DB_PASSWORD';
#GRANT ALL PRIVILEGES ON DATABASE cloud_db TO $DB_USERNAME;
#EOF

