# ===== Etapa 1: Build =====
FROM maven:3.9.4-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Cache de dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Código fuente
COPY src ./src

# Build del JAR
RUN mvn clean package -DskipTests

# ===== Etapa 2: Runtime =====
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
