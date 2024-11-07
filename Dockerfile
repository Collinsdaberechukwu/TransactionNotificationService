#FROM openjdk:17-jdk-slim as builder
#
#WORKDIR /app
#
#COPY mvnw .
#COPY .mvn .mvn
#COPY pom.xml .
#
#RUN ./mvnw dependency:go-offline -B
#
#COPY src ./src
#
#RUN ./mvnw clean package -DskipTests
#
#FROM openjdk:17-jdk-slim
#
#WORKDIR /app
#
#COPY --from=builder /app/target/*.jar /app/tns.jar
#
#EXPOSE 16221
#
#ENTRYPOINT ["java", "-jar", "/app/tns.jar"]


FROM openjdk:17-jdk-slim as builder

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

COPY src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/target/*.jar /app/tns.jar

EXPOSE 9000

ENTRYPOINT ["java", "-jar", "/app/tns.jar"]