package com.spaghettyArts.projectakrasia.services;

import com.spaghettyArts.projectakrasia.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private JavaMailSender mailSender;
    private MailContentService mailContentBuilder;

    @Autowired
    public NotificationService(JavaMailSender mailSender, MailContentService mailContentBuilder) {
        this.mailSender = mailSender;
        this.mailContentBuilder = mailContentBuilder;
    }

    public void prepareAndSend(UserModel obj) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("spaghettiarts.projectakrasia@gmail.com");
            messageHelper.setTo(obj.getEmail());
            messageHelper.setSubject("Registo no multiplayer");
            String content = mailContentBuilder.build(obj.getUsername());
            messageHelper.setText(content, true);
        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            // runtime exception; compiler will not force you to handle it
        }
    }

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
            // runtime exception; compiler will not force you to handle it
        }
    }
}
