package com.vitortenorio.notificator.api.v1.client;

import com.vitortenorio.notificator.core.factory.EmailFactory;
import com.vitortenorio.notificator.entity.EmailEntity;
import com.vitortenorio.notificator.gateway.EmailGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class EmailClient implements EmailGateway {

    private final EmailFactory emailFactory;

    @Override
    public void sendEmail(EmailEntity email) {
        JavaMailSenderImpl javaMailSender = emailFactory.createJavaMailSender();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            emailFactory.configureMimeMessageHelper(mimeMessage, email);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
