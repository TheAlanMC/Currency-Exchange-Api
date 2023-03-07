FROM eclipse-temurin:11-jdk
COPY target/*.jar app.jar

ENV USERNAME "postgres"
ENV PASSWORD "mysecretpassword"
ENV URL "jdbc:postgresql://localhost:5432/postgres"
ENV API_KEY ""
ENV PORT 8080

ENTRYPOINT ["java","-jar","/app.jar"]
#FROM eclipse-temurin:11-jdk
#VOLUME /tmp
#ARG DEPENDENCY=target/dependency
#COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
#COPY ${DEPENDENCY}/META-INF /app/META-INF
#COPY ${DEPENDENCY}/BOOT-INF/classes /app
#
#ENV USERNAME ${POSTGRES_USER}
#ENV PASSWORD ${POSTGRES_PASSWORD}
#ENV URL ${POSTGRES_URL}
#
#ENTRYPOINT ["java","-cp","app:app/lib/*","bo.edu.ucb.currencykt.CurrencyKtApplicationKt"]