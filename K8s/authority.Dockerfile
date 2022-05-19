FROM maven:latest AS build
COPY Backend/custom-starter /home/app/custom-starter
COPY Backend/microservices/authority/src /home/app/src
COPY Backend/microservices/authority/pom.xml /home/app
RUN mvn -f /home/app/custom-starter/pom.xml clean package
RUN mvn install:install-file -Dfile="/home/app/custom-starter/target/custom-app-0.0.1-SNAPSHOT.jar" -DgroupId="com.teststarter" -DartifactId="custom-app" -Dversion="0.0.1-SNAPSHOT" -Dpackaging="jar"
RUN mvn -f /home/app/pom.xml clean package


FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/authority.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8090