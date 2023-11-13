package com.vitortenorio.notificator.api.v1.client;

import com.vitortenorio.notificator.core.factory.EmailFactory;
import com.vitortenorio.notificator.core.util.MessageHelper;
import com.vitortenorio.notificator.exception.EmailException;
import com.vitortenorio.notificator.util.AbstractContextTest;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("EmailClient Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestEmailClient extends AbstractContextTest {

    @Mock
    private EmailFactory emailFactory;

    @Mock
    private JavaMailSenderImpl javaMailSender;
    @Mock
    private MessageHelper messageHelper;

    @Mock
    private MimeMessageHelper mimeMessageHelper;

    private EmailClient emailClient;

    @BeforeEach
    public void setUp() {
        emailFactory = mock(EmailFactory.class);
        javaMailSender = mock(JavaMailSenderImpl.class);
        mimeMessageHelper = mock(MimeMessageHelper.class);
        emailClient = new EmailClient(emailFactory, messageHelper);
    }

    @Test
    @Order(1)
    @DisplayName("001 - Send Email - Success")
    public void sendEmail_Success() throws Exception {
        when(emailFactory.createJavaMailSender()).thenReturn(javaMailSender);
        when(javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((javax.mail.Session) null));
        doNothing().when(emailFactory).configureMimeMessageHelper(any(MimeMessage.class), any());

        emailClient.sendEmail(createEmailEntity());

        verify(emailFactory, times(1)).createJavaMailSender();
        verify(javaMailSender, times(1)).createMimeMessage();
        verify(emailFactory, times(1)).configureMimeMessageHelper(any(MimeMessage.class), any());
        verify(javaMailSender, times(1)).send(any(MimeMessage.class));
    }

    @Test
    @Order(2)
    @DisplayName("002 - Send Email - Failure")
    public void sendEmail_Failure() throws Exception {
        when(emailFactory.createJavaMailSender()).thenReturn(javaMailSender);
        when(javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((javax.mail.Session) null));
        doThrow(new RuntimeException("Simulating an exception")).when(emailFactory)
                .configureMimeMessageHelper(any(MimeMessage.class), any());

        assertThrows(EmailException.class, () -> emailClient.sendEmail(createEmailEntity()));

        verify(emailFactory, times(1)).createJavaMailSender();
        verify(javaMailSender, times(1)).createMimeMessage();
        verify(emailFactory, times(1)).configureMimeMessageHelper(any(MimeMessage.class), any());
        verify(javaMailSender, times(0)).send(any(MimeMessage.class));
    }
}
