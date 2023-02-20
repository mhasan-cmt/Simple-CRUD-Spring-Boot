FROM openjdk:17

COPY target/spring-react-demo-0.0.1-SNAPSHOT.jar spring-react-demo-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar","/spring-react-demo-0.0.1-SNAPSHOT.jar"]