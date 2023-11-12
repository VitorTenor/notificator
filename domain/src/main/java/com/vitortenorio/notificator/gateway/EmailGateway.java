package com.vitortenorio.notificator.gateway;

import com.vitortenorio.notificator.entity.EmailEntity;

public interface EmailGateway {
    public void sendEmail(EmailEntity email);
}
