# === Stage 1: Build the application ===
FROM gradle:8.5-jdk17 AS build
WORKDIR /app

# Copy only necessary files to cache dependencies
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle
RUN gradle build --no-daemon || return 0  # ignore failure (for caching)

# Copy the full source and build
COPY . .
RUN gradle clean build -x test --no-daemon

# === Stage 2: Run stage ===
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the fat jar from the previous build stage
COPY --from=build /app/build/libs/*.jar app.jar
COPY --from=build /app/build/libs/qmul-project-0.0.1-SNAPSHOT.jar /app/app.jar

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
