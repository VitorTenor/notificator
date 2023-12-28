FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY app/target/notificator-app.jar /app/notificator-app.jar
COPY domain/target/notificator-domain.jar /app/notificator-domain.jar

ENV EMAIL_USERNAME=descomplicando
ENV EMAIL_PASSWORD=descomplicando
ENV RABBITMQ_CONNECTION=localhost

ENTRYPOINT ["java","-jar","/app/notificator-app.jar"]
