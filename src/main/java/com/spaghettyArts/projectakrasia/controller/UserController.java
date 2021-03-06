package com.spaghettyArts.projectakrasia.controller;

import com.spaghettyArts.projectakrasia.model.UserModel;
import com.spaghettyArts.projectakrasia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * O controller das rotas associadas ao com a alteração de informação do user (/user)
 * @author Fabian Nunes
 * @version 1.0
 */
@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService service;

    /**
     * A função que irá receber o PUT Request para alterar o username na rota /user/changeName
     * @param header Esta rota está protegida com um header authrorization que contém uma token, caso essa token não seja igual a que u user tem na base de dados será enviado forbiden 401
     * @param user O objeto usermodel que possui o id e o username novo do user
     * @return Irá retornar um código HTTP dependendo do resultado do request, caso seja válido será enviado 200 e o objeto, caso exista um erro será enviado um código erro específico para o cliente
     * @author Fabian Nunes
     */
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
        return ResponseEntity.ok().body(obj);
    }

    /**
     * A função que irá receber o PUT Request para alterar o nível da armadura do user na rota /user/updateArmor
     * @param header Esta rota está protegida com um header authrorization que contém uma token, caso essa token não seja igual a que u user tem na base de dados será enviado forbiden 401
     * @param user O objeto usermodel que possui o id, o nível da armadura e o dinheiro do user
     * @return Irá retornar um código HTTP dependendo do resultado do request, caso seja válido será enviado 200 e o objeto user, caso exista um erro será enviado um código erro específico para o cliente
     * @author Fabian Nunes
     */
    @PutMapping(value = "/updateArmor")
    public ResponseEntity<UserModel> changeStats(@RequestHeader("Authorization") String header, @RequestBody UserModel user) {

        String auth = header.substring(7);
        if (!service.validateUser(auth, user.getId())) {
            return ResponseEntity.status(401).build();
        }

        return service.changeStats(user.getId());
    }


    /**
     * A função que irá receber o PUT Request para fazer logout na rota /user/logout
     * @param header Esta rota está protegida com um header authrorization que contém uma token, caso essa token não seja igual a que u user tem na base de dados será enviado forbiden 401
     * @param user O objeto usermodel que possui o id do user
     * @return Irá retornar um código HTTP dependendo do resultado do request, caso seja válido será enviado 201, caso exista um erro será enviado um código erro específico para o cliente
     * @author Fabian Nunes
     */
    @PutMapping(value = "/logout")
    public ResponseEntity<Object> logout(@RequestHeader("Authorization") String header, @RequestBody UserModel user) {
        String auth = header.substring(7);
        if (!service.validateUser(auth, user.getId())) {
            return ResponseEntity.status(401).build();
        }
        service.logout(user.getId());
        return ResponseEntity.ok().build();
    }

    /**
     * A função que irá receber o PUT Request para mudar o estado do user
     * @param header Esta rota está protegida com um header authrorization que contém uma token, caso essa token não seja igual a que u user tem na base de dados será enviado forbiden 401
     * @param user O objeto usermodel que possui o id do user
     * @return Irá retornar um código HTTP dependendo do resultado do request, caso seja válido será enviado 201, caso exista um erro será enviado um código erro específico para o cliente
     * @author Fabian Nunes
     */
    @PutMapping(value = "/state")
    public ResponseEntity<Object> changeState(@RequestHeader("Authorization") String header, @RequestBody UserModel user) {
        String auth = header.substring(7);
        if (!service.validateUser(auth, user.getId())) {
            return ResponseEntity.status(401).build();
        }
        return service.changeState(user.getId(), user.getUserOnline());
    }

}
