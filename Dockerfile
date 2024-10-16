FROM openjdk:21-jdk-slim

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn

COPY pom.xml .

COPY src ./src

RUN ./mvnw package

CMD ["java", "-jar", "/app/target/greenway-app-0.0.1-SNAPSHOT.jar"]