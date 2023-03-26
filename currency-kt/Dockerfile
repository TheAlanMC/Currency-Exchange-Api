#FROM eclipse-temurin:11-jdk
#COPY target/*.jar app.jar
#
#ENV USERNAME "postgres"
#ENV PASSWORD "mysecretpassword"
#ENV URL "jdbc:postgresql://localhost:5432/postgres"
#ENV API_KEY ""
#ENV PORT 8080
#
#ENTRYPOINT ["java","-jar","/app.jar"]

#FROM --platform=linux/x86_64 eclipse-temurin:11.0.18_10-jdk-alpine as build
FROM arm64v8/eclipse-temurin:11.0.18_10-jdk as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)


#FROM eclipse-temurin:11
FROM arm64v8/eclipse-temurin:11

VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENV USERNAME "postgres"
ENV PASSWORD "mysecretpassword"
ENV URL "jdbc:postgresql://localhost:5432/postgres"
ENV API_KEY ""
ENV PORT 8080

ENTRYPOINT ["java","-cp","app:app/lib/*","bo.edu.ucb.currencykt.CurrencyKtApplicationKt"]