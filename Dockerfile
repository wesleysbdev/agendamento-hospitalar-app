FROM maven:3.9.11-eclipse-temurin-25 AS builder

WORKDIR /app

COPY . .

RUN mvn clean install -DskipTests

FROM eclipse-temurin:25-jre

WORKDIR /app

COPY --from=builder /app/agendamento-bootstrap/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]