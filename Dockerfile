FROM openjdk:8u111-jdk-alpine
VOLUME /tmp
ADD target/vibent-back-0.0.1-SNAPSHOT.jar /usr/share/vibent-back/vibent-back-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/vibent-back/vibent-back-0.0.1-SNAPSHOT.jar"]