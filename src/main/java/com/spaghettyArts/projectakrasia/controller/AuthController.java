package com.spaghettyArts.projectakrasia.controller;

import com.spaghettyArts.projectakrasia.model.ResetModel;
import com.spaghettyArts.projectakrasia.model.UserModel;
import com.spaghettyArts.projectakrasia.services.NotificationService;
import com.spaghettyArts.projectakrasia.services.ResetService;
import com.spaghettyArts.projectakrasia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    @Autowired
    private UserService service;

    @Autowired
    private NotificationService mail;

    @Autowired
    private ResetService reset;

    @GetMapping(path = "/list")
    public ResponseEntity<List<UserModel>> findAll() {
        List<UserModel> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserModel> findById(@PathVariable Integer id) {
        UserModel obj = service.findByID(id);
        return ResponseEntity.ok().body(obj);
    }

    //good
    @PutMapping(value = "/login")
    public ResponseEntity<UserModel> findPlayer(@RequestBody UserModel user) {
        return service.login(user.getEmail(), user.getPassword());
    }

    @PostMapping(value = "/register")
    public ResponseEntity<UserModel> insert(@RequestBody UserModel user) {
        UserModel obj = service.register(user);
        if (obj == null) {
            return ResponseEntity.status(409).build();
        } else {
            mail.prepareAndSend(obj);
            return ResponseEntity.ok().body(obj);
        }
    }

    @PostMapping(value = "/reset")
    public ResponseEntity<ResetModel> sendMail(@RequestParam(value = "email") String email) {
        ResetModel obj = reset.resetRequest(email);
        if (obj == null) {
            return ResponseEntity.status(409).build();
        } else {
            UserModel user = service.findByMail(email);
            String link = "http://localhost:8080/resetPassword?token=" + obj.getToken();
            mail.prepareAndSendReset(user, link);
            return ResponseEntity.ok().body(obj);
        }
    }

}
