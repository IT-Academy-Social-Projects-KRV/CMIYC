## 1. General info 
### 1.1. Workflow:
**rest-server**, **soap-server** or **websocket-server** receives request from outer resource then send this request to **mock-repository** which finds data and return it back to one of servers which responses to outer source.

**rest-server** and **websocket-server** responses and requests is in JSON.

**soap-server** must receive request and response in xml.

### 1.2. How to run
### 1.2.1. Rest-server
**rest-server** and **websocket-server** are Servlet based. Tomcat server must be installed on PC.

You can install Tomcat here: https://tomcat.apache.org/download-90.cgi#9.0.62

To run apps in IDEA:
>Run -> Edit configuration... -> +(left top) -> Smart Tomcat <br>
> Name: rest-server<br>
> Tomcat Server: select your tomcat<br>
> Context path: /<br>
> Server port: 9001<br>
> Admin port: 8001

Save and run configuration

### 1.2.2. Websocket-server
>Run -> Edit configuration... -> +(left top) -> Smart Tomcat <br>
> Name: websocket-server<br>
> Tomcat Server: select your tomcat<br>
> Context path: /<br>
> Server port: 9002<br>
> Admin port: 8002

Save and run configuration

### 1.2.3. Soap-server
**soap-server** is Spring based application. Before running, jaxb plugin must be started.

Go to maven tab in IDEA, click _soap-server -> Plugins -> jaxb2 -> jaxb:xjc -> Reload all maven projects_. Then run SoapApp class.

### 1.2.3. Mock-repository 
**mock-repository** runs just as command line app. Find Main class in directory and run.

### 1.3. Order of running apps
First run **mock-repository** then run others

## 2.Testing in POSTMAN
### 2.1. rest-server:
_new -> HTTP Request -> GET -> http://localhost:9001/rest?firstName=Peter&lastName=Peterson -> send_

OR:

_new -> HTTP Request -> POST -> http://localhost:9001/rest -> Body -> raw -> paste and send request:_

    {"firstName":"John","lastName":"Johnson"}

### 2.2. soap-server:
_new -> HTTP Request -> POST -> http://localhost:9003/soap -> Headers -> Content-Type change to text/xml -> Body -> raw -> XML -> paste and send request:_

    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:gs="http://soap.api/xsd">
        <soapenv:Header/>
        <soapenv:Body>
            <gs:getPersonRequest>
                <gs:firstName>Steven</gs:firstName>
                <gs:lastName>Stevenson</gs:lastName>
                <gs:birthDayDate>01.01.1981</gs:birthDayDate>
            </gs:getPersonRequest>
        </soapenv:Body>
    </soapenv:Envelope>

### 2.3. websocket-server:
_new -> WebSocket Request -> ws://localhost:9002/ws -> Connect -> paste and send request:_

    {"firstName":"Amanda","lastName":"Armstrong","birthDayDate":"04.04.1984","gender":"FEMALE"}

## 3.DATA in repository

For **rest-server** needs: _firstName_ and _lastName_ fields

For **soap-server** needs: _firstName_, _lastName_ and _birthDayDate_ fields
 
For **websocket-server** needs: all fields.

    {"firstName":"John","lastName":"Johnson","birthDayDate":"01.01.1981","gender":"MALE"}
    {"firstName":"Peter","lastName":"Peterson","birthDayDate":"02.02.1982","gender":"MALE"} 
    {"firstName":"Steven","lastName":"Stevenson","birthDayDate":"03.03.1983","gender":"MALE"}
    {"firstName":"Amanda","lastName":"Armstrong","birthDayDate":"04.04.1984","gender":"FEMALE"}
