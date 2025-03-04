FROM eclipse-temurin:21.0.6_7-jre-alpine

ADD /build/libs/portal-backend-template-1.0.jar /opt/app.jar

ENTRYPOINT ["java", "-jar", "/opt/app.jar"]
