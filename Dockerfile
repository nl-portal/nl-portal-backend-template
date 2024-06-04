FROM openjdk:21-slim

ADD libs/portal-backend-template-1.0.jar /opt/app.jar

ENTRYPOINT ["java", "-jar", "/opt/app.jar"]
