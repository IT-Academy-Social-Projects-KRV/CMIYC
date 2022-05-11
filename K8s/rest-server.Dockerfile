FROM maven:latest AS build
COPY Backend/external-apis/rest-server/src /home/app/src
COPY Backend/external-apis/rest-server/pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM tomcat:9.0.62
COPY --from=build /home/app/target/rest-server.war /usr/local/tomcat/webapps/rest-server.war
EXPOSE 8080
CMD ["catalina.sh", "run"]