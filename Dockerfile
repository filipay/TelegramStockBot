ARG VERSION=11.0.10
FROM openjdk:${VERSION}-jdk-slim as BUILD
COPY . /src
WORKDIR /src
RUN ./gradlew --no-daemon shadowJar

FROM openjdk:${VERSION}-jdk-slim

COPY --from=BUILD /src/build/libs/shadow-1.0-SNAPSHOT-all.jar /bin/runner/run.jar
WORKDIR /bin/runner

CMD ["java","-jar","run.jar"]