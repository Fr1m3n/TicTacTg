FROM maven:3.6.0-jdk-11-slim AS build

WORKDIR /usr/src/app

COPY pom.xml .
COPY src src

RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:11-jre-slim
COPY --from=build /usr/src/app/target/TicTacTG-1.0-SNAPSHOT.jar /usr/local/lib/app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]