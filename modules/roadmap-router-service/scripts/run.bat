@Echo Off

start javaw -Xms1024m -Xmx1024m -jar ../.build/bin/com.sensiblemetrics.api.roadmap.router.service/com.sensiblemetrics.api-roadmap-router-service-0.1.0-SNAPSHOT.jar -in src/main/resources/config.properties
