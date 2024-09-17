#!/bin/bash

# Create log folder for webapp logs
sudo mkdir -p /var/log/webapp
sudo chown csye6225:csye6225 /var/log/webapp

# Install google cloud ops agent
curl -sSO https://dl.google.com/cloudagents/add-google-cloud-ops-agent-repo.sh
sudo bash add-google-cloud-ops-agent-repo.sh --also-install

# Configure ops agent
sudo bash -c 'cat <<EOF > /etc/google-cloud-ops-agent/config.yaml
logging:
  receivers:
    webapp-log-receiver:
      type: files
      include_paths:
        - /var/log/webapp/*.log
      record_log_file_path: true
  processors:
    webapp-log-processor:
      type: parse_json
      time_key: timestamp
      time_format: "%Y-%m-%dT%H:%M:%S.%LZ"
    move_severity:
      type: modify_fields
      fields:
        severity:
          move_from: jsonPayload.level
  service:
    pipelines:
      default_pipeline:
        receivers: [webapp-log-receiver]
        processors: [webapp-log-processor, move_severity]

global:
  default_self_log_file_collection: false
EOF'

sudo systemctl restart google-cloud-ops-agent
