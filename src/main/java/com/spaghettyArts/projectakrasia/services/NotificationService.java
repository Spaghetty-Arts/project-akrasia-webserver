package com.spaghettyArts.projectakrasia.services;

import com.spaghettyArts.projectakrasia.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

/**
 * O serviço com as funções relativas ao envio de emails
 * @author Fabian Nunes
 * @version 0.1
 */
@Service
public class NotificationService {

    private JavaMailSender mailSender;
    private MailContentService mailContentBuilder;

    @Autowired
    public NotificationService(JavaMailSender mailSender, MailContentService mailContentBuilder) {
        this.mailSender = mailSender;
        this.mailContentBuilder = mailContentBuilder;
    }

    /**
     * Funçãp para enviar o email relativo ao registo
     * @param obj Objeto Usermodel que vem do registo e será utilizado para obter o email do user e o nome
     * @author Fabian Nunes
     * @version 0.1
     */
    public void prepareAndSend(UserModel obj) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("postmaster@sandbox5ab8aff87aea4848813be1678538ae1b.mailgun.org");
            messageHelper.setTo(obj.getEmail());
            messageHelper.setSubject("Registo no multiplayer");
            String content = mailContentBuilder.build(obj.getUsername());
            messageHelper.setText(content, true);
        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            System.out.println(e);
        }
    }

    /**
     * Funçãp para enviar o email relativo ao registo
     * @param obj Objeto Usermodel que vem do registo e será utilizado para obter o email do user e o nome
     * @param link String que contém o link para o reset da password
     * @author Fabian Nunes
     * @version 0.1
     */
    public void prepareAndSendReset(UserModel obj, String link) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("spaghettiarts.projectakrasia@gmail.com");
            messageHelper.setTo(obj.getEmail());
            messageHelper.setSubject("Reset da Password");
            String content = mailContentBuilder.buildReset(obj.getUsername(), link);
            messageHelper.setText(content, true);
        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            System.out.println(e);
        }
    }
}
