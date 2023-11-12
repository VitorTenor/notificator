package com.vitortenorio.notificator.core.factory;

import com.vitortenorio.notificator.entity.EmailEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class EmailFactory {
    @Value("${spring.mail.username}")
    private String sender;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private Integer port;

    @Value("${spring.mail.protocol}")
    private String protocol;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private Boolean auth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private Boolean starttls;

    public JavaMailSenderImpl createJavaMailSender() {


        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setJavaMailProperties(createProperties());
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(sender);
        javaMailSender.setPassword(password);
        javaMailSender.setProtocol(protocol);

        return javaMailSender;
    }

    public void configureMimeMessageHelper(MimeMessage mimeMessage, EmailEntity email) throws Exception {
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(sender);
        helper.setTo(email.recipient());
        helper.setSubject(email.subject());
        helper.setText(email.body(), true);
    }

    private Properties createProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", auth);
        properties.put("mail.smtp.starttls.enable", starttls);
        return properties;
    }
}
