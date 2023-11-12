package com.vitortenorio.notificator.api.v1.controller;

import com.vitortenorio.notificator.api.v1.request.EmailRequest;
import com.vitortenorio.notificator.usecase.emailprovider.SendEmailProviderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/email")
public class EmailController {

    private final SendEmailProviderUseCase sendEmailProviderUseCase;

    @PostMapping("/send")
    public String sendEmailProvider(@RequestBody EmailRequest email) {
        return sendEmailProviderUseCase.execute(email.toEntity());
    }
}
