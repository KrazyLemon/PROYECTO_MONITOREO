

# ==== Build inicial
FROM maven:3.9.4-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Cache de dependencias
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Código fuente
COPY src ./src
RUN mvn -B package -DskipTests

# ===== Etapa 2: Runtime =====
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
# Antiguo comando
# ENTRYPOINT ["java", "-jar", "app.jar"]
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

ENTRYPOINT ["java","-XX:+UseContainerSupport","-XX:MaxRAMPercentage=75.0","-jar","app.jar"]
