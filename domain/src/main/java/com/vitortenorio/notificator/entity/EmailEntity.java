package com.vitortenorio.notificator.entity;

public record EmailEntity (
        String recipient,
        String subject,
        String body
) {}
