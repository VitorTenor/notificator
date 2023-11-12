package com.vitortenorio.notificator.usecase.emailprovider;

import com.vitortenorio.notificator.entity.EmailEntity;
import com.vitortenorio.notificator.gateway.EmailProviderGateway;

import javax.inject.Named;

@Named
public class SendEmailProviderUseCase {
    private final EmailProviderGateway emailProviderGateway;

    public SendEmailProviderUseCase(final EmailProviderGateway emailProviderGateway) {
        this.emailProviderGateway = emailProviderGateway;
    }

    public String execute(final EmailEntity email) {
        return this.emailProviderGateway.sendEmailProvider(email);
    }
}
