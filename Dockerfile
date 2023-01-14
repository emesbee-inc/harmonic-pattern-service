FROM amazoncorretto:11
COPY target/harmonic-pattern-*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]