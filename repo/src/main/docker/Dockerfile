FROM adoptopenjdk/openjdk11
COPY ./demo-0.0.1-SNAPSHOT.jar /app/lib/
ENTRYPOINT ["java", "-jar", "/app/lib/demo-0.0.1-SNAPSHOT.jar"]
HEALTHCHECK --start-period=20s CMD curl -f http://localhost:8080/ || exit 1
EXPOSE 8080
