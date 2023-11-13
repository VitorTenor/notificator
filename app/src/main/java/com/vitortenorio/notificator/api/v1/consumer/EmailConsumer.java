package com.vitortenorio.notificator.api.v1.consumer;

import com.vitortenorio.notificator.entity.EmailEntity;
import com.vitortenorio.notificator.usecase.email.SendEmailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class EmailConsumer {

    private final SendEmailUseCase sendEmailUseCase;
    private final Logger LOGGER = Logger.getLogger(EmailConsumer.class.getName());
    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void listen(@Payload EmailEntity message) {
        LOGGER.info("Sending email...");
        sendEmailUseCase.execute(message);
        LOGGER.info("Email sent");
    }
}
