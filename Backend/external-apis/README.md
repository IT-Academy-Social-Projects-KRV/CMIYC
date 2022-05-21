## 1. General info

### 1.1. Workflow:

**rest-server**, **soap-server** or **websocket-server** receives request from outer resource then send this request
to **mock-repository** which finds data and return it back to one of servers which responses to outer source.

**rest-server** and **websocket-server** responses and requests is in JSON.

**soap-server** must receive request and response in xml.

### 1.2. How to run

### 1.2.1. Rest-server

**rest-server** and **websocket-server** are Servlet based. Tomcat server must be installed on PC.

You can install Tomcat here: https://tomcat.apache.org/download-90.cgi#9.0.62

To run apps in IDEA:
> Run -> Edit configuration... -> +(left top) -> Smart Tomcat <br>
> Name: rest-server<br>
> Tomcat Server: select your tomcat<br>
> Context path: /<br>
> Server port: 9001<br>
> Admin port: 8001

Save and run configuration

### 1.2.2. Websocket-server

> Run -> Edit configuration... -> +(left top) -> Smart Tomcat <br>
> Name: websocket-server<br>
> Tomcat Server: select your tomcat<br>
> Context path: /<br>
> Server port: 9002<br>
> Admin port: 8002

Save and run configuration

### 1.2.3. Soap-server

**soap-server** is Spring based application. Before running, jaxb plugin must be started.

Go to maven tab in IDEA, click _soap-server -> Plugins -> jaxb2 -> jaxb:xjc -> Reload all maven projects_. Then run
SoapApp class.

### 1.2.4. Mock-repository

**mock-repository** runs just as command line app. Find Main class in directory and run.

### 1.3. Order of running apps

First run **mock-repository** then run others

## 2.Testing in POSTMAN

### 2.1. rest-server:

_new -> HTTP Request -> GET -> http://localhost:9001/rest?firstName=Peter -> send_

OR:

_new -> HTTP Request -> POST -> http://localhost:9001/rest -> Body -> raw -> paste and send request:_

```json
{
  "name": {
    "first": "Donna"
  },
  "birthDate": {
    "day": 17,
    "month": 10,
    "year": 1999
  },
  "sexCode": "F"
}
```

### 2.2. soap-server:

_new -> HTTP Request -> POST -> http://localhost:9003/soap -> Headers -> Content-Type change to text/xml -> Body -> raw
-> XML -> paste and send request:_

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:gs="http://soap.api/xsd">
    <soapenv:Header/>
    <soapenv:Body>
        <gs:Payload>
            <gs:state>KS</gs:state>
            <gs:operatorLicenseNumber>SCBCU8ZA2AC586280</gs:operatorLicenseNumber>
        </gs:Payload>
    </soapenv:Body>
</soapenv:Envelope>
```

### 2.3. websocket-server:

_new -> WebSocket Request -> ws://localhost:9002/ws -> Connect -> paste and send request:_

```json
{
  "name": {
    "first": "Donna"
  },
  "birthDate": {
    "day": 17,
    "month": 10,
    "year": 1999
  },
  "sexCode": "F",
  "state": "KS",
  "imageIndicator": true
}
```

## 3.API

Every external service represents one API:

**rest-server** - **api1**
> **required field:** birthDate(day,month,year), name(first,middle,last,suffix) sexCode <br>
> **returns:** city , address, name, isMarried, numberOfChildren, raceCode

**soap-server** - **api2**
> **required fields:** operatorLicenseNumber, state, imageIndicator<br>
> **returns:** carModel,operatorLicenseExpirationDate, carModelYear, carVin,carNumber, image, phone, name

**websocket-server** - **api3**
> **required fields:** birthDate(day,month,year), name(first,middle,last,suffix), state, imageIndicator<br>
> **returns:** сity, address, job,companyName, phone, email, image, name

All person data can be found in mock-repository/src/main/resources/mock_data.json

Some of this data:

```json
[
  {
    "state": "KS",
    "city": "Topeka",
    "address": "09 Mesta Alley",
    "email": "jdensumbe0@dagondesign.com",
    "job": "Staff Accountant III",
    "companyName": "McLaughlin, Schimmel and Raynor",
    "phone": "785-263-8114",
    "gender": "FEMALE",
    "name": {
      "first": "Donna",
      "middle": "Jan",
      "last": "Davis",
      "suffix": ""
    },
    "birthDate": {
      "day": 17,
      "month": 10,
      "year": 1999
    },
    "raceCode": "L",
    "image": "https://avatars.dicebear.com/api/female/4269.png",
    "operatorLicenseNumber": "SCBCU8ZA2AC586280",
    "operatorLicenseExpirationDate": "09.08.2025",
    "carModel": "Navajo",
    "carModelYear": 1994,
    "carVin": "2G4GU5GV9C9958996",
    "carNumber": "5L3•EXO",
    "isMarried": false,
    "numberOfChildren": 0
  },
  {
    "state": "NY",
    "city": "New York City",
    "address": "20 Mayfield Center",
    "email": "dmumbey1@gizmodo.com",
    "job": "Marketing Manager",
    "companyName": "Brakus, Botsford and Conroy",
    "phone": "917-729-1066",
    "gender": "MALE",
    "name": {
      "first": "James",
      "middle": "Delainey",
      "last": "Stevenson",
      "suffix": ""
    },
    "birthDate": {
      "day": 8,
      "month": 11,
      "year": 1977
    },
    "raceCode": "L",
    "image": "https://avatars.dicebear.com/api/male/7637.png",
    "operatorLicenseNumber": "3GYFNDE33ES767101",
    "operatorLicenseExpirationDate": "27.05.2025",
    "carModel": "Alcyone SVX",
    "carModelYear": 1993,
    "carVin": "WAUBG78E96A669395",
    "carNumber": "02X•IQE",
    "isMarried": true,
    "numberOfChildren": 3
  },
  {
    "state": "CA",
    "city": "Glendale",
    "address": "8879 Crowley Circle",
    "email": "vwhiten2@businesswire.com",
    "job": "Accounting Assistant I",
    "companyName": "Flatley and Sons",
    "phone": "818-746-8212",
    "gender": "FEMALE",
    "name": {
      "first": "Donna",
      "middle": "Vaughan",
      "last": "Williams",
      "suffix": ""
    },
    "birthDate": {
      "day": 8,
      "month": 5,
      "year": 1963
    },
    "raceCode": "O",
    "image": "https://avatars.dicebear.com/api/female/1832.png",
    "operatorLicenseNumber": "WVWAA7AJ7BW094055",
    "operatorLicenseExpirationDate": "26.03.2027",
    "carModel": "Fiero",
    "carModelYear": 1984,
    "carVin": "KMHDB8AE4BU092399",
    "carNumber": "H1H•5NI",
    "isMarried": true,
    "numberOfChildren": 3
  }
]
```
