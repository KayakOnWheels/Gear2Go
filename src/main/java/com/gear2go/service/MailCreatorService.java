package com.gear2go.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailCreatorService {

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildForgotPasswordMail(String token) {
        Context context = new Context();
        context.setVariable("token", token);
        return templateEngine.process("mail/password-recovery-file.html", context);
    }
}