package com.spaghettyArts.projectakrasia.controller;

import com.spaghettyArts.projectakrasia.model.UserModel;
import com.spaghettyArts.projectakrasia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService service;


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

    @PutMapping(value = "/changeName")
    public ResponseEntity<UserModel> changeName(@RequestBody UserModel user) {
        if (!service.validateUser(user.getUser_token(), user.getId())) {
            return ResponseEntity.status(401).build();
        }
        UserModel obj = service.changeName(user.getId(), user.getUsername());
        if (obj == null) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok().body(obj);
    }

    @PutMapping(value = "/updateArmor")
    public ResponseEntity<Object> changeStats(@RequestBody UserModel user) {
        if (!service.validateUser(user.getUser_token(), user.getId())) {
            return ResponseEntity.status(401).build();
        }

        return service.changeStats(user.getId(), user.getLife(), user.getMoney());
    }

    //reviewed and good
    @PutMapping(value = "/dailyReward")
    public ResponseEntity<Object> gotReward(@RequestBody UserModel user) {
        if (!service.validateUser(user.getUser_token(), user.getId())) {
            return ResponseEntity.status(401).build();
        }
        return service.gotReward(user.getId(), user.getMoney());
    }

    @PutMapping(value = "/logout")
    public ResponseEntity<Object> logout(@RequestBody UserModel user) {
        if (!service.validateUser(user.getUser_token(), user.getId())) {
            return ResponseEntity.status(401).build();
        }
        service.logout(user.getId());
        return ResponseEntity.ok().build();
    }

}
