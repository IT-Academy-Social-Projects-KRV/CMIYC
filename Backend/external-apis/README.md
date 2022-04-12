1. General info 
    1.1. Workflow:
         rest-server, soap-server or websocket-server receives request from outer resource than send this request to 
         mock-repository which finds data and return it back to one of servers which responses to outer source.
         rest-server and websocket-server responses and requests is in JSON.
         soap-server must receive request and response in xml.
    1.2. How to run
         1.2.1. soap-server is Spring based application. Before running jaxb plugin must be started. Go to maven tab in
               IDEA -> soap-server -> Plugins -> jaxb2 -> jaxb:xjc -> Reload all maven projects. Then run SoapApp class.
         1.2.2. rest-server and websocket-server are Servlet based. Tomcat server must be installed on PC. To run apps 
               in IDEA: go to:  Run -> Edit configuration... -> +(left top) -> Tomcat Server -> Local -> HTTP Port 
               (change if necessary) -> Deployment tab -> + -> Artifact.. -> chose rest-server:war, websocket-server:war
                run tomcat
         1.2.3. mock-repository runs just as command line app. Find Main class in directory and run.
    1.3. Order of running apps
          First run mock-repository then run others
2.Testing in POSTMAN
    2.1. rest-server:
               new -> HTTP Request -> POST -> http://localhost:[port you chose in tomcat settings]/rest_server_war/rest ->
               Body -> raw -> paste request: {"firstName":"Peter","lastName":"Peterson"} -> SEND
    2.2. soap-server:
               new -> HTTP Request -> POST -> http://localhost:9003/soap -> Headers -> Content-Type change to text/xml ->
               Body -> raw -> XML -> paste request: 
               <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
               xmlns:gs="http://soap.api/xsd">
               <soapenv:Header/>
               <soapenv:Body>
               <gs:getPersonRequest>
               <gs:firstName>Steven</gs:firstName>
               <gs:lastName>Stevenson</gs:lastName>
               <gs:birthDayDate>03.03.1983</gs:birthDayDate>
               </gs:getPersonRequest>
               </soapenv:Body>
               </soapenv:Envelope> -> SEND
    2.3. websocket-server:
            New -> WebSocket Request -> ws://localhost:[port you chose in tomcat settings]/websocket_server_war/ws ->
            Connect -> paste request: 
            {"firstName":"Amanda","lastName":"Armstrong","birthDayDate":"04.04.1984","gender":"FEMALE"} -> SEND
3.DATA in repository
    For rest-server needs: firstName and lastName fields,
    For soap-server needs: firstName and lastName fields and birthDayDate fields,
    For websocket-server needs: all fields.
{"firstName":"John","lastName":"Johnson","birthDayDate":"01.01.1981","gender":"MALE"}
{"firstName":"Peter","lastName":"Peterson","birthDayDate":"02.02.1982","gender":"MALE"} 
{"firstName":"Steven","lastName":"Stevenson","birthDayDate":"03.03.1983","gender":"MALE"}
{"firstName":"Amanda","lastName":"Armstrong","birthDayDate":"04.04.1984","gender":"FEMALE"}
