FROM openjdk:21
COPY target/url-shortner-0.0.1-SNAPSHOT.jar url-shortner-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/url-shortner-0.0.1-SNAPSHOT.jar"]