## how to use Connector API:
* without Eureka server - http://localhost:8080/searcher
* with Eureka server - name = connector, url for requests = /searcher
* use method POST with valid token in header and body with json like this:

```json
{
  "apis": ["NCIC", "FRED", "DataGOV"],
  "data": [{
    "name.first": "Donna",
    "name.middle": "Jan",
    "name.last": "Davis",
    "birthDate.day": 17,
    "birthDate.month": 10,
    "birthDate.year": 1999,
    "raceCode": "L",
    "operatorLicenseNumber": "SCBCU8ZA2AC586280",
    "state": "KS",
    "imageIndicator": "Y",
    "sexCode": "F"
  }]
}
```