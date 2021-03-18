ARG VERSION=11-jdk-slim
ARG ARCH=amd64
FROM ${ARCH}/openjdk:${VERSION} as BUILD
COPY . /src
WORKDIR /src
RUN ./gradlew --no-daemon shadowJar

FROM ${ARCH}/openjdk:${VERSION}

COPY --from=BUILD /src/build/libs/shadow-1.0-SNAPSHOT-all.jar /bin/runner/run.jar
WORKDIR /bin/runner

CMD ["java","-jar","run.jar"]