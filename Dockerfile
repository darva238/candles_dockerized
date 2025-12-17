FROM tomcat:10.1-jdk11

COPY target/web1.war /usr/local/tomcat/webapps/web1.war

EXPOSE 8080
