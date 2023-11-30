package com.vitortenorio.notificator.api.v1.provider;

import static org.junit.jupiter.api.Assertions.*;

import com.vitortenorio.notificator.util.AbstractContextTest;
import org.junit.jupiter.api.*;
import org.junit.platform.commons.logging.Logger;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.vitortenorio.notificator.core.util.MessageHelper;
import com.vitortenorio.notificator.entity.EmailEntity;

import java.util.List;
import java.util.Stack;

import static org.mockito.Mockito.*;

@DisplayName("EmailProvider Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestEmailProvider extends AbstractContextTest {
    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private MessageHelper messageHelper;

    private EmailProvider emailProvider;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        emailProvider = new EmailProvider(messageHelper, rabbitTemplate);
    }
// TODO: Fix this test
//    @Test
//    @Order(1)
//    @DisplayName("001 - Send Email - Success")
//    public void sendEmailProvider_Success() {
//        EmailEntity emailEntity = createEmailEntity();
//        when(messageHelper.getMessage("email_sent")).thenReturn("Email sent successfully");
//
//        String result = emailProvider.sendEmailProvider(emailEntity);
//
//        verify(rabbitTemplate, times(1)).convertAndSend(anyString(), eq(emailEntity));
//        verify(messageHelper, times(1)).getMessage("email_sent");
//
//        assertEquals("Email sent successfully", result);
//    }

    @Test
    @Order(2)
    @DisplayName("002 - Send Email - Null EmailEntity")
    public void sendEmailProvider_MessageHelperFailure() {
        EmailEntity emailEntity = createNullEmailEntity();
        when(messageHelper.getMessage("email_sent")).thenThrow(new RuntimeException("Simulating a failure"));

        assertThrows(Exception.class, () -> emailProvider.sendEmailProvider(emailEntity));

        verify(rabbitTemplate, never()).convertAndSend(anyString(), any(EmailEntity.class));
    }
}
