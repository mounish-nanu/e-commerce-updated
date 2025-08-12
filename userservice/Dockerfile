# ===== build stage =====
FROM maven:3.9-eclipse-temurin-22 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -q -DskipTests package

# ===== run stage =====
FROM eclipse-temurin:22-jre
WORKDIR /app
# non-root user
RUN useradd -ms /bin/bash appuser
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
USER appuser
ENTRYPOINT ["java","-XX:MaxRAMPercentage=75.0","-jar","/app/app.jar"]
