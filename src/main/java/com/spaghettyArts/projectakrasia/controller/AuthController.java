package com.spaghettyArts.projectakrasia.controller;

import com.spaghettyArts.projectakrasia.model.ResetModel;
import com.spaghettyArts.projectakrasia.model.UserModel;
import com.spaghettyArts.projectakrasia.services.NotificationService;
import com.spaghettyArts.projectakrasia.services.ResetService;
import com.spaghettyArts.projectakrasia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * O controller das rotas associadas a autenticação do utilizador (/auth)
 * @author Fabian Nunes
 * @version 1.0
 */
@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    @Autowired
    private UserService service;

    @Autowired
    private NotificationService mail;

    @Autowired
    private ResetService reset;

    /**
     * A função que irá receber o PUT Request para login na rota /auth/login
     * @param user O objeto usermodel que possui o email e a password que irá no body da requesição
     * @return Irá retornar um código HTTP dependendo do resultado do request, caso seja válido será enviado 201, caso exista um erro será enviado um código erro específico para o cliente
     * @author Fabian Nunes
     */
    @PutMapping(value = "/login")
    public ResponseEntity<UserModel> findPlayer(@RequestBody UserModel user) {
        return service.login(user.getEmail(), user.getPassword());
    }

    /**
     * A função que irá receber o POST Request para registar como user na rota /auth/resgister
     * @param user O objeto usermodel que possui o email, a password e o username
     * @return Irá retornar um código HTTP dependendo do resultado do request, caso seja válido será enviado 200 caso exista um erro será enviado um código erro específico para o cliente
     * @author Fabian Nunes
     */
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

    /**
     * A função que irá receber o POST Request para fazer reset da password« na rota /auth/reset/{email}
     * @param email A string email é um path parameter que corresponde ao email associado com a conta do qual pede o reset
     * @return Irá retornar um código HTTP dependendo do resultado do request, caso seja válido será enviado 201 caso exista um erro será enviado um código erro específico para o cliente
     * @author Fabian Nunes
     */
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
