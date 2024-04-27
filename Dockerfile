FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /telegram-notification
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean install

FROM eclipse-temurin:17-jre-alpine
WORKDIR /telegram-notification
COPY --from=builder /telegram-notification/target/*.jar /telegram-notification/telegram-notification-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/telegram-notification/telegram-notification-service.jar"]
