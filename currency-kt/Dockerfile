FROM eclipse-temurin:11-jdk
COPY target/*.jar app.jar
ENV USERNAME ${POSTGRES_USER}
ENV PASSWORD ${POSTGRES_PASSWORD}
ENV URL ${POSTGRES_URL}
ENTRYPOINT ["java","-jar","/app.jar"]