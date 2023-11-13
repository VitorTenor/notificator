package com.vitortenorio.notificator.util;

import com.vitortenorio.notificator.api.v1.request.EmailRequest;
import com.vitortenorio.notificator.entity.EmailEntity;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AbstractContextTest {


    protected EmailEntity createEmailEntity(){
        return new EmailEntity(
                "vitortest@test.com",
                "test",
                "this is a test"
        );
    }

    protected EmailEntity createNullEmailEntity(){
        return new EmailEntity(null, null, null);
    }

    protected EmailRequest createEmailRequest(){
        return new EmailRequest(
                "vitortest@test.com",
                "test",
                "this is a test"
        );
    }
}
