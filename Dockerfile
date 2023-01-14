FROM amazoncorretto:11
COPY target/harmonic-pattern-service-*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]