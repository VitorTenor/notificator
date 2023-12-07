package com.vitortenorio.notificator.gateway;

import com.vitortenorio.notificator.entity.EmailEntity;

public interface EmailProviderGateway {
    String sendEmailProvider(EmailEntity email);
}
