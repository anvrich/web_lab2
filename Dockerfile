FROM openjdk:11-jdk-slim

WORKDIR /opt/jboss/

COPY wildfly-29.0.0.Final /opt/jboss/wildfly

COPY target/lab2-1.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/

EXPOSE 8080

RUN chmod +x /opt/jboss/wildfly/bin/standalone.sh

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]
