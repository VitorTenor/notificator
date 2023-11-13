package com.vitortenorio.notificator.api.v1.request;

import com.vitortenorio.notificator.entity.EmailEntity;
import com.vitortenorio.notificator.util.AbstractContextTest;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("EmailRequest tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestEmailRequest extends AbstractContextTest {
    @Test
    @Order(1)
    @DisplayName("001 - toEntity - Success")
    public void toEntity_Success() {
        EmailRequest emailRequest = createEmailRequest();

        EmailEntity emailEntity = emailRequest.toEntity();

        assertEquals(emailRequest.recipient(), emailEntity.recipient());
        assertEquals(emailRequest.subject(), emailEntity.subject());
        assertEquals(emailRequest.body(), emailEntity.body());
    }

    @Test
    @Order(2)
    @DisplayName("002 - Getters - Success")
    public void getters_Success() {
        EmailRequest emailRequest = new EmailRequest("recipient@example.com", "Subject", "Body");

        assertEquals("recipient@example.com", emailRequest.recipient());
        assertEquals("Subject", emailRequest.subject());
        assertEquals("Body", emailRequest.body());
    }
}
