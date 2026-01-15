# Paso 1: Usar una imagen de Maven para compilar el código
FROM maven:3.9.4-eclipse-temurin-21-alpine AS development
WORKDIR /app

COPY pom.xml .
RUN  mvn dependency:go-offline
COPY src ./src

RUN mvn clean package -DskipTests

# En esta etapa no necesitamos ENTRYPOINT porque lo define el docker-compose

# Etapa 2: Producción (Lo que ya tenías)
FROM maven:3.9.4-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine AS production
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]