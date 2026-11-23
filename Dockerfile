FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /app
COPY FlightWeatherApp/pom.xml .
COPY FlightWeatherApp/src ./src
RUN mvn -B -ntp verify

FROM scratch AS artifacts
COPY --from=build /app/target/FlightWeatherApp-1.0-jar-with-dependencies.jar /FlightWeatherApp.jar
