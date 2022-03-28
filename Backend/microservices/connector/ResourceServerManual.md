###Howto use and configure Resource Server in Microservices:
1. Add dependency to pom.xml:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
    <version>2.6.4</version>
</dependency>
```
2. Add property to application.jml:
```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          jwk-set-uri: http://localhost:8090/.well-known/jwks.json
```
3. Add configuration class, like this:

```java
@Configuration
public class JWTSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authz -> authz
                        .antMatchers(HttpMethod.GET, "/**").permitAll()
                        .antMatchers(HttpMethod.POST, "/**").hasAuthority("SCOPE_user")
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt());
    }
}
```
p.s.  Good and easy tutorial link:
https://www.baeldung.com/spring-security-oauth-resource-server