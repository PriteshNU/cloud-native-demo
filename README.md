# Cloud Native Web App

## Overview 

This is a cloud-native web application built with Spring Boot and PostgreSQL

## Prerequisites 

To install and run the app locally, you need to have the following installed: 

- JDK 17 or later 
- Maven 3.2 or later
- PostgreSQL


## Installation

- Clone the repository to your local machine:

   ```sh
   git clone git@github.com:csye-6225-cloud-native/webapp.git
   ```

- Navigate to the project directory:
   
    ```sh
   cd webapp
   ```

- Build the project using Maven:

    ```sh
   mvn clean install -DskipTests
   ```

- Manually configure the following properties in the application.yml file under src/main/resources:
    ```yml
    spring:
        datasource:
            url: "Your jdbc url"
            username: "Your db username"
            password: "Your db password"
    ```

    OR

    Configure the following environment variables (recommended):
    ```sh
    export DB_USERNAME=test
    export DB_PASSWORD=test
    ```
    

- Run the application:
   ```sh
   mvn spring-boot:run
   ```
    > The application should be running on `http://localhost:8080`

## Testing

- To run the tests:

    ```sh
    mvn test
    ```

## Packer - custom GCE image

### Prerequisites

Ensure the following prerequisites are installed and configured:

- Packer

### Configuration with pkrvars.hcl

To configure infrastructure for different environments (eg dev, stage, prod):

- Create a env.pkrvars.hcl file (eg, dev.pkrvars.hcl, prod.pkrvars.hcl, etc) in `/packer` folder
- Sample env.pkrvars.hcl file:
```hcl
    environment         = "dev"
    project_id          = "gcp project id"
    zone                = "gcp zone"
    ssh_username        = "xxxxxxx"
    db_user             = "xxxxxxx"
    db_password         = "xxxxxxx"
    source_image_family = "centos-stream-8"
    subnet_id           = "xxxxxxx"
    disk_size           = 20
    disk_type           = "pd-standard"
    instance_name       = "webapp-vm"
    machine_type        = "e2-medium"
    image_name          = "webapp-v1"
    image_family_name   = "webapp-centos-8"

``` 

### Steps to build custom image

- Navigate to the packer directory:
    ```sh
   cd packer
   ```

- Initialize packer workspace:
    ```sh
   packer init .
   ```

- Format configuration files:
    ```sh
   packer fmt .
   ```

- Validate configuration files:
    ```sh
   packer validate .
   ```

- Build custom image:
    ```sh
   packer build -var-file="env.pkrvars.hcl" gcp.pkr.hcl
   ```
  
## Author

[Pritesh Nimje](mailto:nimje.p@northeastern.edu)
