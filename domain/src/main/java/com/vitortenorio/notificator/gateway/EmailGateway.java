package com.vitortenorio.notificator.gateway;

import com.vitortenorio.notificator.entity.EmailEntity;

public interface EmailGateway {
    void sendEmail(EmailEntity email);
}
