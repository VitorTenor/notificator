package com.vitortenorio.notificator.api.v1.request;

import com.vitortenorio.notificator.entity.EmailEntity;

import javax.validation.constraints.NotBlank;

public record EmailRequest(
        @NotBlank(message = "The field 'to' is required")
        String recipient,
        @NotBlank(message = "The field 'subject' is required")
        String subject,
        @NotBlank(message = "The field 'body' is required")
        String body
) {
    public EmailEntity toEntity() {
        return new EmailEntity(
                this.recipient,
                this.subject,
                this.body
        );
    }
}
