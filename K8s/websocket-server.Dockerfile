FROM maven:latest AS build
COPY Backend/external-apis/websocket-server/src /home/app/src
COPY Backend/external-apis/websocket-server/pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM tomcat:9.0.62
COPY --from=build /home/app/target/websocket-server.war /usr/local/tomcat/webapps/websocket-server.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
