package com.spaghettyArts.projectakrasia.services;

import com.spaghettyArts.projectakrasia.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private JavaMailSender javaMailSender;

    @Autowired
    public NotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendNotification(UserModel user) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom("spaghettiarts.projectakrasia@gmail.com");
        mail.setSubject("Welcome to Epic Store BITCH!");
        mail.setText("You got Scammed");

        javaMailSender.send(mail);
    }

    public void resetNotification(String email, String link) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setFrom("spaghettiarts.projectakrasia@gmail.com");
        mail.setSubject("Reset Password");
        mail.setText("Foi pedido um reset de password " + link);

        javaMailSender.send(mail);
    }
}
