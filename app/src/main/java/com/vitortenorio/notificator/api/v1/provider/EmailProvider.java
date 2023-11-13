package com.vitortenorio.notificator.api.v1.provider;

import com.vitortenorio.notificator.core.util.MessageHelper;
import com.vitortenorio.notificator.entity.EmailEntity;
import com.vitortenorio.notificator.gateway.EmailProviderGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailProvider implements EmailProviderGateway {

    private final RabbitTemplate rabbitTemplate;
    private final MessageHelper messageHelper;

    @Value("${spring.rabbitmq.queue}")
    private String queue;

    @Override
    public String sendEmailProvider(EmailEntity email) {
        rabbitTemplate.convertAndSend(queue, email);
        return messageHelper.getMessage("email_sent");
    }

}
