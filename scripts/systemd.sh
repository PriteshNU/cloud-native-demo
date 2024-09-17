#!/bin/bash

# Copy systemd service file
sudo cp /tmp/webapp.service /lib/systemd/system/webapp.service

sudo systemctl daemon-reload
sudo systemctl enable webapp.service
#sudo systemctl start webapp.service
#sudo systemctl status webapp.service

WEBAPP_SERVICE=$?
if [ $WEBAPP_SERVICE -eq 0 ]; then
  echo "Webapp service enabled to boot on startup"
else
  echo "Webapp service failed to enable for booting on startup"
fi
