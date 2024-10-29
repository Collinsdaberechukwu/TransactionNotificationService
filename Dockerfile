FROM openjdk:17-jdk-slim as builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY ./target/tns.jar .

EXPOSE 8089

ENTRYPOINT ["java", "-jar", "tns.jar"]



