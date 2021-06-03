package com.spaghettyArts.projectakrasia.controller;

import com.spaghettyArts.projectakrasia.model.UserModel;
import com.spaghettyArts.projectakrasia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService service;

    @PutMapping(value = "/changeName")
    public ResponseEntity<Object> changeName(@RequestHeader("Authorization") String header, @RequestBody UserModel user) {
        String auth = header.substring(7);
        if (!service.validateUser(auth, user.getId())) {
            return ResponseEntity.status(401).build();
        }
        UserModel obj = service.changeName(user.getId(), user.getUsername());
        if (obj == null) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/updateArmor")
    public ResponseEntity<Object> changeStats(@RequestHeader("Authorization") String header, @RequestBody UserModel user) {

        String auth = header.substring(7);
        if (!service.validateUser(auth, user.getId())) {
            return ResponseEntity.status(401).build();
        }

        return service.changeStats(user.getId(), user.getLife(), user.getMoney());
    }

    @PutMapping(value = "/dailyReward")
    public ResponseEntity<Object> gotReward(@RequestHeader("Authorization") String header, @RequestBody UserModel user) {
        String auth = header.substring(7);
        if (!service.validateUser(auth, user.getId())) {
            return ResponseEntity.status(401).build();
        }
        return service.gotReward(user.getId(), user.getMoney());
    }

    @PutMapping(value = "/logout")
    public ResponseEntity<Object> logout(@RequestHeader("Authorization") String header, @RequestBody UserModel user) {
        String auth = header.substring(7);
        if (!service.validateUser(auth, user.getId())) {
            return ResponseEntity.status(401).build();
        }
        service.logout(user.getId());
        return ResponseEntity.ok().build();
    }

}
