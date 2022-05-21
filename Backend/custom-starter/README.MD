This starter enables work of cors filter in three modules:authority, data and search

To build the project with our starter in jar file you need to write next commands:
In terminal: PS [your_pass]\CMIYC\Backend\custom-starter>
 - mvn clean install
 - mvn install:install-file -Dfile="target\custom-app-0.0.1-SNAPSHOT.jar" -DgroupId="com.teststarter" -DartifactId="custom-app" -Dversion="0.0.1-SNAPSHOT" -Dpackaging="jar"
