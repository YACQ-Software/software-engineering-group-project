# === Stage 1: Build the application ===
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml and download dependencies (for caching)
COPY pom.xml .
COPY src ./src

# Build the app
RUN mvn clean package -DskipTests

# === Stage 2: Create runtime image ===
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy built jar from previous stage
COPY --from=build /app/target/qmul_project-*.jar app.jar

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
