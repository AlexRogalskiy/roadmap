#!/usr/bin/env bash

docker run -d --name sonarqube -p 9000:9000 sonarqube
mvn sonar:sonar -Dsonar.host.url=http://localhost:9000
