FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY app/target/notificator-app.jar /app/notificator-app.jar
COPY domain/target/notificator-domain.jar /app/notificator-domain.jar

ARG EMAIL_USERNAME
ARG EMAIL_PASSWORD
ARG RABBITMQ_CONNECTION

ENV EMAIL_USERNAME=${EMAIL_USERNAME}
ENV EMAIL_PASSWORD=${EMAIL_PASSWORD}
ENV RABBITMQ_CONNECTION=${RABBITMQ_CONNECTION}

ENTRYPOINT ["java","-jar","/app/notificator-app.jar"]
