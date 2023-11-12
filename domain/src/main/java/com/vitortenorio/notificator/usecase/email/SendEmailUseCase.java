package com.vitortenorio.notificator.usecase.email;

import com.vitortenorio.notificator.entity.EmailEntity;
import com.vitortenorio.notificator.gateway.EmailGateway;

import javax.inject.Named;

@Named
public class SendEmailUseCase {

    private final EmailGateway emailGateway;

    public SendEmailUseCase(EmailGateway emailGateway) {
        this.emailGateway = emailGateway;
    }

    public void execute(EmailEntity email) {
        emailGateway.sendEmail(email);
    }
}
