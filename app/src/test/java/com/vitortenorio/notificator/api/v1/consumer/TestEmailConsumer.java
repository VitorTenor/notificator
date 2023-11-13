package com.vitortenorio.notificator.api.v1.consumer;

import com.vitortenorio.notificator.entity.EmailEntity;
import com.vitortenorio.notificator.usecase.email.SendEmailUseCase;
import com.vitortenorio.notificator.util.AbstractContextTest;
import org.junit.jupiter.api.*;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("EmailConsumer Tests")
public class TestEmailConsumer extends AbstractContextTest {

    @Mock
    private SendEmailUseCase sendEmailUseCase;

    private EmailConsumer emailConsumer;

    @BeforeEach
    public void setUp() {
        sendEmailUseCase = mock(SendEmailUseCase.class);
        emailConsumer = new EmailConsumer(sendEmailUseCase);
    }

    @Test
    @Order(1)
    @DisplayName("001 - Listen - Success")
    public void listen_ValidEmailEntity() {
        EmailEntity emailEntity = createEmailEntity();
        emailConsumer.listen(emailEntity);

        verify(sendEmailUseCase, times(1)).execute(emailEntity);
    }

    @Test
    @Order(2)
    @DisplayName("002 - Listen - Null EmailEntity")
    public void listen_NullEmailEntity() {
        EmailEntity emailEntity = null;

        emailConsumer.listen(emailEntity);

        verify(sendEmailUseCase, times(0)).execute(any(EmailEntity.class));
    }
}
