FROM maven:3.9.12-amazoncorretto-21-alpine AS build

WORKDIR /build

COPY pom.xml .

# Baixa as dependências do projeto para o cache do Docker
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

FROM amazoncorretto:21-alpine

WORKDIR /app

COPY --from=build /build/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]