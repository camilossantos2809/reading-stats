# Stage 1: Cache Gradle dependencies
FROM gradle:latest AS cache
RUN mkdir -p /home/gradle/cache_home
ENV GRADLE_USER_HOME=/home/gradle/cache_home
COPY build.gradle.* gradle.properties /home/gradle/app/
COPY gradle /home/gradle/app/gradle
WORKDIR /home/gradle/app
RUN gradle dependencies --no-daemon

FROM gradle:latest AS build
WORKDIR /app
COPY . .
RUN gradle buildFatJar --no-daemon

FROM amazoncorretto:25-alpine
WORKDIR /app
COPY --from=build /app/build/libs/readingstats-all.jar readingstats.jar
EXPOSE 8080
CMD ["java", "-jar", "readingstats.jar"]