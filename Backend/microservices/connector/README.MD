## how to use Connector API:
* without Eureka server - http://localhost:8080/searcher
* with Eureka server - name = connector, url for requests = /searcher
* use method POST with valid token in header and body with json like this:

```json
{
 "firstName": "John",
 "lastName": "Johnson",
 "birthDayDate": "01.01.1981",
 "sex": "Male",
 "foreignDataSource": [
   "api1",
   "api2",
   "api3"
 ]
}
```

>### WARNING
>For tests only, you can send GET request without valid token and get mock response.





