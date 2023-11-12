package com.vitortenorio.notificator.gateway;

import com.vitortenorio.notificator.entity.EmailEntity;

public interface EmailProviderGateway {
    public String sendEmailProvider(EmailEntity email);
}
