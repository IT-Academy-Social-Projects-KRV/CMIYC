FROM maven:latest AS build
COPY Backend/microservices/connector/src /home/app/src
COPY Backend/microservices/connector/pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests


FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/connector.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8080