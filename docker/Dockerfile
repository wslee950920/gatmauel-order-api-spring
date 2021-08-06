FROM openjdk:11-jre-slim

ADD app.jar /opt/gatmauel/app.jar
ADD application-docker.properties /config/application-docker.properties

EXPOSE 8080 9090

ENTRYPOINT ["java", "-jar", "/opt/gatmauel/app.jar"]