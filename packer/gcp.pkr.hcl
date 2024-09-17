packer {
  required_plugins {
    googlecompute = {
      source  = "github.com/hashicorp/googlecompute"
      version = "~> 1"
    }
  }
}

variable "environment" {
  type        = string
  description = "Environment for infra"
}

variable "project_id" {
  type        = string
  description = "Default GCP project id"
}

variable "zone" {
  type        = string
  description = "GCP zone"
}

variable "subnet_id" {
  type        = string
  description = "Subnet to attach to the instance"
}

variable "source_image_family" {
  type        = string
  description = "Base OS family version"
}

variable "ssh_username" {
  type        = string
  description = "Username to ssh into the instance"
}

variable "db_username" {
  type        = string
  description = "Username for the database user"
}

variable "db_password" {
  type        = string
  description = "Password for the database user"
}

variable "disk_size" {
  type        = number
  description = "Disk size to attach to the instance"
}

variable "disk_type" {
  type        = string
  description = "Disk type to attach to the instance"
}

variable "instance_name" {
  type        = string
  description = "Name for the instance"
}

variable "machine_type" {
  type        = string
  description = "Preset GCE machine type"
}

variable "image_name" {
  type        = string
  description = "Name for the resulting image build"
}

variable "image_family_name" {
  type        = string
  description = "Image family to which the resulting image belongs"
}

source "googlecompute" "gce" {
  project_id          = var.project_id
  source_image_family = var.source_image_family
  zone                = var.zone
  subnetwork          = var.subnet_id
  ssh_username        = var.ssh_username
  disk_size           = var.disk_size
  disk_type           = var.disk_type
  machine_type        = var.machine_type
  instance_name       = "${var.environment}-${var.instance_name}"
  image_name          = "${var.environment}-${var.image_name}-{{timestamp}}"
  image_family        = "${var.environment}-${var.image_family_name}"
  labels = {
    environment  = var.environment
    organization = "csye-6225",
    service      = "cloud-native-webapp"
  }
}

build {
  sources = ["source.googlecompute.gce"]

  provisioner "file" {
    source      = "../target/webapi.jar"
    destination = "/tmp/webapp.jar"
  }

  provisioner "file" {
    source      = "../scripts/webapp.service"
    destination = "/tmp/webapp.service"
  }

  provisioner "shell" {
    environment_vars = [
      "DB_USERNAME=${var.db_username}",
      "DB_PASSWORD=${var.db_password}"
    ]
    scripts = [
      "../scripts/setup.sh",
      "../scripts/artifact.sh",
      "../scripts/systemd.sh",
      "../scripts/monitoring.sh"
    ]
  }

  post-processor "manifest" {
    output     = "packer-manifest.json"
    strip_path = true
  }
}
