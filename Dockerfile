FROM maven

WORKDIR /usr/src/app

COPY pom.xml .
COPY src .

RUN mvn -B -e -o -T 1C verify

RUN mvn