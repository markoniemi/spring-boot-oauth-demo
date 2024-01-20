#build
#FROM markhobson/maven-chrome:jdk-8
FROM maven:3-jdk-11-slim
WORKDIR .
COPY pom.xml .
COPY src src
RUN ["mvn","-B","clean","install","-DskipTests=true"]
#run
FROM openjdk:11-jdk-slim
ARG DEPENDENCY=target/dependency
VOLUME /tmp
#RUN apk --no-cache add curl
ENV VERSION=v1
ENV DB_HOST=postgresql
ENV DB_PASSWORD=mysecretpassword
ENV PORT=8082
USER ${USER}
#COPY ${DEPENDENCY}/BOOT-INF/lib /app/BOOT-INF/lib
#COPY ${DEPENDENCY}/META-INF /app/META-INF
EXPOSE ${PORT}
COPY target/spring-boot-test-0.0.1-SNAPSHOT.jar /app/app.jar
#ENTRYPOINT ["java","-XX:+UnlockExperimentalVMOptions","-XX:+UseCGroupMemoryLimitForHeap","-XX:MaxRAMFraction=1","-XshowSettings:vm","-jar","/app/app.jar"]
ENTRYPOINT ["java","-jar","/app/app.jar"]
#HEALTHCHECK --interval=5s --timeout=3s --start-period=40s --retries=5 \
#  CMD curl -f http://localhost:${PORT}/actuator/health/liveness || exit 1
