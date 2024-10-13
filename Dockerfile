## Stage 1: Build the application
#FROM maven:3.9.4-eclipse-temurin-17 AS build
#
#WORKDIR /app/transactionnotificationservice
#
## Copy the pom.xml file and download dependencies
#COPY pom.xml .
#
#RUN mvn dependency:go-offline -B
#
## Copy the source code
#COPY src ./src
#
#RUN mvn clean package -DskipTests
#
#FROM eclipse-temurin:17-jre-focal
#
#WORKDIR /app
#
#COPY --from=build /app/transactionnotificationservice/target/TransactionNotificationService-0.0.1-SNAPSHOT.jar app.jar
#
#ENTRYPOINT ["java", "-jar", "app.jar"]


FROM openjdk:17-jdk

COPY target/tns.jar .

EXPOSE  8080

 ENTRYPOINT ["java", "-jar","tns.jar "]

