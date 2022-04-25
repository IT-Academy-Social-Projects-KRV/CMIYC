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

### 1.2.3. Mock-repository

**mock-repository** runs just as command line app. Find Main class in directory and run.

### 1.3. Order of running apps

First run **mock-repository** then run others

## 2.Testing in POSTMAN

### 2.1. rest-server:

_new -> HTTP Request -> GET -> http://localhost:9001/rest?firstName=Peter -> send_

OR:

_new -> HTTP Request -> POST -> http://localhost:9001/rest -> Body -> raw -> paste and send request:_

```json
{"firstName":"John"}
```

### 2.2. soap-server:

_new -> HTTP Request -> POST -> http://localhost:9003/soap -> Headers -> Content-Type change to text/xml -> Body -> raw
-> XML -> paste and send request:_

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:gs="http://soap.api/xsd">
    <soapenv:Header/>
    <soapenv:Body>
        <gs:SearchRequest>
            <gs:firstName>Steven</gs:firstName>
            <gs:lastName>Stevenson</gs:lastName>
        </gs:SearchRequest>
    </soapenv:Body>
</soapenv:Envelope>
```

### 2.3. websocket-server:

_new -> WebSocket Request -> ws://localhost:9002/ws -> Connect -> paste and send request:_

```json
{"firstName":"Amanda","lastName":"Armstrong","birthDayDate":"04.04.1984","gender":"FEMALE"}
```

## 3.API

Every external service represents one API:

**rest-server** - **api1**
> **required field:** firstName<br>
> **returns:** country, city, and address

**soap-server** - **api2**
> **required fields:** firstName and lastName<br>
> **returns:** email, job, and phone number

**websocket-server** - **api3**
> **required fields:** firstName, lastName, gender and birthDayDate (format: dd.MM.yyyy)<br>
> **returns:** carModel and carVin

All person data can be found in mock-repository/src/main/resources/mock_data.json

Some of this data:

```json
[
  {
    "gender": "MALE",
    "firstName": "John",
    "lastName": "Johnson",
    "email": null,
    "phone": "704-570-9750",
    "birthDayDate": "01.01.1981",
    "carVin": "WAUMF98K69A080368",
    "carModel": "Tacoma",
    "country": "China",
    "city": "Guashe",
    "address": "1337 Kennedy Drive",
    "job": "VP Accounting"
  },
  {
    "gender": "MALE",
    "firstName": "Peter",
    "lastName": "Peterson",
    "email": "jpiniur1@over-blog.com",
    "phone": "947-793-9775",
    "birthDayDate": "02.02.1982",
    "carVin": "3C6TD5GT5CG918011",
    "carModel": "XK Series",
    "country": "Greece",
    "city": "Samothráki",
    "address": "72 Fordem Drive",
    "job": null
  },
  {
    "gender": "MALE",
    "firstName": "Steven",
    "lastName": "Stevenson",
    "email": null,
    "phone": "444-453-1779",
    "birthDayDate": "03.03.1983",
    "carVin": "2GKALMEKXF6505805",
    "carModel": "Fox",
    "country": "China",
    "city": "Zhenjiang",
    "address": "302 Jackson Avenue",
    "job": "Design Engineer"
  },
  {
    "gender": "FEMALE",
    "firstName": "Amanda",
    "lastName": "Armstrong",
    "email": "lfrearson3@soup.io",
    "phone": "529-544-9508",
    "birthDayDate": "04.04.1984",
    "carVin": "5FNRL5H23CB727110",
    "carModel": "Scirocco",
    "country": "Botswana",
    "city": "Mathambgwane",
    "address": "05741 Annamark Pass",
    "job": "VP Product Management"
  }
]
```
