FROM maven:latest AS build
COPY Backend/microservices/authority/src /home/app/src
COPY Backend/microservices/authority/pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package


FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/authority-1.0-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]