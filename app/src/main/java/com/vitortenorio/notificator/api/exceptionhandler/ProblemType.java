package com.vitortenorio.notificator.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
    INVALID_FIELD("/invalid-field", "Invalid field"),
    SEND_EMAIL_ERROR("/send-email-error", "Error sending email"),
    ;

    private final String title;
    private final String uri;

    ProblemType(String path, String title) {
        this.uri = path;
        this.title = title;
    }
}
