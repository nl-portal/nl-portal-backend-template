FROM openjdk:17.0.1-slim

ADD /build/libs/portal-backend-template-1.0.jar /opt/app.jar

ENTRYPOINT ["java", "-jar", "/opt/app.jar"]
