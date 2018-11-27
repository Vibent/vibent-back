FROM openjdk:8u111-jdk-alpine
VOLUME /tmp
ADD target/vibent-back.jar /usr/share/vibent-back/vibent-back.jar
ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/vibent-back/vibent-back.jar"]