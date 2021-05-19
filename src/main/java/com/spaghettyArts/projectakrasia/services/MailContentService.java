package com.spaghettyArts.projectakrasia.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailContentService {

    private TemplateEngine templateEngine;

    @Autowired
    public MailContentService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String build(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        return templateEngine.process("welcomeMail", context);
    }

    public String buildReset(String name, String link) {
        Context context = new Context();
        context.setVariable("message", name);
        context.setVariable("link", link);
        return templateEngine.process("resetMail", context);
    }

}
