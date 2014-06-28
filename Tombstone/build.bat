@ECHO OFF

CD ..\Common\common

CALL mvn clean install

CD ..\..\Tombstone\server

CALL mvn clean install

COPY .\target\tombstone.war C:\Dev\Tools\Tomcat\apache-tomcat-8.0.8\webapps /Y

RMDIR C:\Dev\Tools\Tomcat\apache-tomcat-8.0.8\webapps\tombstone /q /s
RMDIR C:\Dev\Tools\Tomcat\apache-tomcat-8.0.8\work\Catalina\localhost\tombstone /q /s

COPY .\tomcat\conf\*.* C:\Dev\Tools\Tomcat\apache-tomcat-8.0.8\conf /Y
COPY .\tomcat\lib\*.* C:\Dev\Tools\Tomcat\apache-tomcat-8.0.8\lib /Y

CD ..
