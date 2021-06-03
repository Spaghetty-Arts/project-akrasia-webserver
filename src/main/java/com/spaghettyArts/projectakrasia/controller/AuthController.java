package com.spaghettyArts.projectakrasia.controller;

import com.spaghettyArts.projectakrasia.model.ResetModel;
import com.spaghettyArts.projectakrasia.model.UserModel;
import com.spaghettyArts.projectakrasia.services.NotificationService;
import com.spaghettyArts.projectakrasia.services.ResetService;
import com.spaghettyArts.projectakrasia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    @Autowired
    private UserService service;

    @Autowired
    private NotificationService mail;

    @Autowired
    private ResetService reset;


    @PutMapping(value = "/login")
    public ResponseEntity<UserModel> findPlayer(@RequestBody UserModel user) {
        return service.login(user.getEmail(), user.getPassword());
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Object> insert(@RequestBody UserModel user) {
        UserModel obj = service.register(user);
        if (obj == null) {
            return ResponseEntity.status(409).build();
        } else {
            mail.prepareAndSend(obj);
            return ResponseEntity.status(201).build();
        }
    }

    @PostMapping(value = "/reset/{email}")
    public ResponseEntity<ResetModel> sendMail(@PathVariable("email") String email) {
        ResetModel obj = reset.resetRequest(email);
        if (obj == null) {
            return ResponseEntity.status(409).build();
        } else {
            UserModel user = service.findByMail(email);
            String link = "http://localhost:8080/resetPassword?token=" + obj.getToken();
            mail.prepareAndSendReset(user, link);
            return ResponseEntity.status(201).build();
        }
    }

}
