FROM maven:latest AS build
COPY Backend/external-apis/mock-repository/src /home/app/src
COPY Backend/external-apis/mock-repository/pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/lib/ /lib/
COPY --from=build /home/app/target/mock-repository.jar /mock-repository.jar
ENTRYPOINT ["java", "-jar", "mock-repository.jar"]
EXPOSE 9000