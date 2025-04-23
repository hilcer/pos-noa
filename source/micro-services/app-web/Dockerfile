FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

COPY /target/app-web-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "app-web-0.0.1-SNAPSHOT.jar"]