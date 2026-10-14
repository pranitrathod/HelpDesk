# Build with: docker compose build
FROM maven:3.9-eclipse-temurin-21 AS build
ARG MODULE
WORKDIR /workspace
COPY pom.xml .
COPY contracts/pom.xml contracts/pom.xml
COPY core-service/pom.xml core-service/pom.xml
COPY api-bff/pom.xml api-bff/pom.xml
COPY notification-worker/pom.xml notification-worker/pom.xml
COPY contracts/src contracts/src
COPY core-service/src core-service/src
COPY api-bff/src api-bff/src
COPY notification-worker/src notification-worker/src
RUN mvn -B -pl ${MODULE} -am -DskipTests package

FROM eclipse-temurin:21-jre
ARG MODULE
RUN useradd --system --uid 10001 --create-home appuser
WORKDIR /app
COPY --from=build /workspace/${MODULE}/target/${MODULE}.jar /app/app.jar
USER 10001
EXPOSE 8080
ENTRYPOINT ["java","-XX:MaxRAMPercentage=75.0","-XX:+ExitOnOutOfMemoryError","-jar","/app/app.jar"]
