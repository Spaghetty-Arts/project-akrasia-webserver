package com.spaghettyArts.projectakrasia.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * O serviço para construir o HTML dos emails
 * @author Fabian Nunes
 * @version 1.0
 */
@Service
public class MailContentService {

    private TemplateEngine templateEngine;

    @Autowired
    public MailContentService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    /**
     * Função para construir o HTML do email do registo
     * @param message String contendo o nome do user registado
     * @return template html
     * @author Fabian Nunes
     */
    public String build(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        return templateEngine.process("welcomeMail", context);
    }

    /**
     * Função para construir o HTML do email de reset
     * @param name String contendo o nome do user registado
     * @param link String que contém o link para o reset da password
     * @return template html
     * @author Fabian Nunes
     */
    public String buildReset(String name, String link) {
        Context context = new Context();
        context.setVariable("message", name);
        context.setVariable("link", link);
        return templateEngine.process("resetMail", context);
    }

}
