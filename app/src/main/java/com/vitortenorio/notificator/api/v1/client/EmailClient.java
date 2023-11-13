package com.vitortenorio.notificator.api.v1.client;

import com.vitortenorio.notificator.core.factory.EmailFactory;
import com.vitortenorio.notificator.core.util.MessageHelper;
import com.vitortenorio.notificator.entity.EmailEntity;
import com.vitortenorio.notificator.exception.EmailException;
import com.vitortenorio.notificator.gateway.EmailGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailClient implements EmailGateway {

    private final EmailFactory emailFactory;
    private final MessageHelper messageHelper;
    @Override
    public void sendEmail(EmailEntity email) {
        JavaMailSenderImpl javaMailSender = emailFactory.createJavaMailSender();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            emailFactory.configureMimeMessageHelper(mimeMessage, email);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException(messageHelper.getMessage("error_sending_email"));
        }
    }
}
