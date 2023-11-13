package com.vitortenorio.notificator.core.factory;

import com.vitortenorio.notificator.entity.EmailEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Logger;

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
    private final Logger LOGGER = Logger.getLogger(EmailFactory.class.getName());


    public JavaMailSenderImpl createJavaMailSender() {
        LOGGER.info("Creating JavaMailSender...");

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setJavaMailProperties(createProperties());
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(sender);
        javaMailSender.setPassword(password);
        javaMailSender.setProtocol(protocol);

        LOGGER.info("JavaMailSender created");
        return javaMailSender;
    }

    public void configureMimeMessageHelper(MimeMessage mimeMessage, EmailEntity email) throws Exception {
        LOGGER.info("Configuring MimeMessageHelper...");

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(sender);
        helper.setTo(email.recipient());
        helper.setSubject(email.subject());
        helper.setText(email.body(), true);

        LOGGER.info("MimeMessageHelper configured");
    }

    private Properties createProperties() {
        LOGGER.info("Creating properties...");

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", auth);
        properties.put("mail.smtp.starttls.enable", starttls);

        LOGGER.info("Properties created");
        return properties;
    }
}
