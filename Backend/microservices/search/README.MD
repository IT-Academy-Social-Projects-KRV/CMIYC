#Search Api
##How to connect services with Search API
1) In POM add
   * before `</project>`: ` <dependencyManagement>
   <dependencies>
   <dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-dependencies</artifactId>
   <version>2021.0.1</version>
   <type>pom</type>
   <scope>import</scope>
   </dependency>
   </dependencies>
   </dependencyManagement>`

   * in dependencies:`<dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
   </dependency>`
2) In application yml add:
   * `eureka:
      client:
      service-url:
      default-zone: http://localhost:8761/eureka`

3) In application starter, after `@SpringBootApplication` add `@EnableEurekaClient`

##Start Microservices
1) start eurekaServer
2) start connector
3) start data
4) start search 