FROM maven:latest AS build
COPY Backend/external-apis/soap-server/src /home/app/src
COPY Backend/external-apis/soap-server/pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/soap-server.jar /soap-server.jar
ENTRYPOINT ["java", "-jar", "soap-server.jar"]
EXPOSE 9003