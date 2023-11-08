FROM openjdk:17.0.2-slim

ADD /build/libs/portal-backend-template-1.0.jar /opt/app.jar

ENTRYPOINT ["java", "-jar", "/opt/app.jar"]
