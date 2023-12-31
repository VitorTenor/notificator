package com.vitortenorio.notificator.api.v1.client;

import com.vitortenorio.notificator.core.factory.EmailFactory;
import com.vitortenorio.notificator.core.util.MessageHelper;
import com.vitortenorio.notificator.entity.EmailEntity;
import com.vitortenorio.notificator.exception.EmailException;
import com.vitortenorio.notificator.gateway.EmailGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class EmailClient implements EmailGateway {

    private final EmailFactory emailFactory;
    private final MessageHelper messageHelper;
    private final Logger LOGGER = Logger.getLogger(EmailClient.class.getName());
    @Override
    public void sendEmail(final EmailEntity email) {
        LOGGER.info("Sending email...");
        var javaMailSender = emailFactory.createJavaMailSender();
        var mimeMessage = javaMailSender.createMimeMessage();

        try {
            LOGGER.info("Configuring email...");

            emailFactory.configureMimeMessageHelper(mimeMessage, email);
            javaMailSender.send(mimeMessage);

            LOGGER.info("Email sent");
        } catch (Exception e) {
            LOGGER.severe("Error sending email");
            throw new EmailException(messageHelper.getMessage("error_sending_email"));
        }
    }
}
