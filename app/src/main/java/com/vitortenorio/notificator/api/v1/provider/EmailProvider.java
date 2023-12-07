package com.vitortenorio.notificator.api.v1.provider;

import com.vitortenorio.notificator.core.util.MessageHelper;
import com.vitortenorio.notificator.entity.EmailEntity;
import com.vitortenorio.notificator.gateway.EmailProviderGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class EmailProvider implements EmailProviderGateway {

    private final MessageHelper messageHelper;
    private final RabbitTemplate rabbitTemplate;
    private final Logger LOGGER = Logger.getLogger(EmailProvider.class.getName());

    @Value("${spring.rabbitmq.queue}")
    private String queue;

    @Override
    public String sendEmailProvider(final EmailEntity email) {
        LOGGER.info("Sending email to rabbitmq queue - Queue: " + queue);

        rabbitTemplate.convertAndSend(queue, email);

        LOGGER.info("Email sent to rabbitmq queue");
        return messageHelper.getMessage("email_sent");
    }

}
