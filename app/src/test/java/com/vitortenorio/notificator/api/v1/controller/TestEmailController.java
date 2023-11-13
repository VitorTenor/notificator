package com.vitortenorio.notificator.api.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitortenorio.notificator.api.v1.request.EmailRequest;
import com.vitortenorio.notificator.entity.EmailEntity;
import com.vitortenorio.notificator.usecase.emailprovider.SendEmailProviderUseCase;
import com.vitortenorio.notificator.util.AbstractContextTest;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("EmailController Tests")
public class TestEmailController extends AbstractContextTest {

    private MockMvc mockMvc;

    @Mock
    private SendEmailProviderUseCase sendEmailProviderUseCase;

    private EmailController emailController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        emailController = new EmailController(sendEmailProviderUseCase);
        mockMvc = MockMvcBuilders.standaloneSetup(emailController).build();
    }

    @Test
    @Order(1)
    @DisplayName("001 - Send Email - Success")
    public void sendEmailProvider_Success() throws Exception {
        EmailRequest emailRequest = createEmailRequest();
        EmailEntity emailEntity = createEmailEntity();
        when(sendEmailProviderUseCase.execute(any(EmailEntity.class))).thenReturn("Email sent successfully");

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/email/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(emailRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Email sent successfully"));

        verify(sendEmailProviderUseCase, times(1)).execute(emailEntity);
    }

    @Test
    @Order(2)
    @DisplayName("002 - Send Email - Validation Failure - No body")
    public void sendEmailProvider_ValidationFailure() throws Exception {
        String emailRequest = "{\"recipient\": \"vitortest@test.com\", \"subject\": \"test\"}";

        when(sendEmailProviderUseCase.execute(any(EmailEntity.class))).thenReturn("Email sent successfully");

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/email/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(emailRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        verify(sendEmailProviderUseCase, never()).execute(any(EmailEntity.class));
    }

    @Test
    @Order(3)
    @DisplayName("003 - Send Email - Validation Failure - No subject")
    public void sendEmailProvider_ValidationFailure_NoSubject() throws Exception {
        String emailRequest = "{\"recipient\": \"vitortest@test.com\", \"body\": \"this is a test\"}";
        when(sendEmailProviderUseCase.execute(any(EmailEntity.class))).thenReturn("Email sent successfully");

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/email/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(emailRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        verify(sendEmailProviderUseCase, never()).execute(any(EmailEntity.class));
    }

    @Test
    @Order(4)
    @DisplayName("004 - Send Email - Validation Failure - No recipient")
    public void sendEmailProvider_ValidationFailure_NoRecipient() throws Exception {
        String emailRequest = "{\"subject\": \"test\", \"body\": \"this is a test\"}";
        when(sendEmailProviderUseCase.execute(any(EmailEntity.class))).thenReturn("Email sent successfully");

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/email/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(emailRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        verify(sendEmailProviderUseCase, never()).execute(any(EmailEntity.class));
    }

    @Test
    @Order(5)
    @DisplayName("005 - Send Email - Validation Failure - All fields empty")
    public void sendEmailProvider_ValidationFailure_AllFieldsEmpty() throws Exception {
        String emailRequest = "{\"recipient\": \"\", \"subject\": \"\", \"body\": \"\"}";
        when(sendEmailProviderUseCase.execute(any(EmailEntity.class))).thenReturn("Email sent successfully");

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/email/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(emailRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        verify(sendEmailProviderUseCase, never()).execute(any(EmailEntity.class));
    }

    @Test
    @Order(6)
    @DisplayName("006 - Send Email - Validation Failure - No content")
    public void sendEmailProvider_ValidationFailure_AllFieldsNull() throws Exception {
        when(sendEmailProviderUseCase.execute(any(EmailEntity.class))).thenReturn("Email sent successfully");

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/email/send")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        verify(sendEmailProviderUseCase, never()).execute(any(EmailEntity.class));
    }
}
