package com.spaghettyArts.projectakrasia.controller;

import com.spaghettyArts.projectakrasia.model.UserModel;
import com.spaghettyArts.projectakrasia.services.HistoryService;
import com.spaghettyArts.projectakrasia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Retention;

/**
 * O controller das rotas associadas ao com o combate entre dois jogadores
 * @author Fabian Nunes
 * @version 0.1
 */
@RestController
@RequestMapping(path = "/pvp")
public class CombatController {

    @Autowired
    private UserService service;

    @Autowired
    private HistoryService historyService;

    /**
     * A função é o Get Request para obter a informação de um user para jogar
     * @param id ID do player com qual pretendemos jogar
     * @return Irá retornar ok e o objeto com as informações do user
     * @author Fabian Nunes
     */
    @GetMapping(value = "/play/{pID}/{id}")
    public ResponseEntity<UserModel> getUser(@RequestHeader("Authorization") String header, @PathVariable(name = "id") Integer id, @PathVariable(name = "pID") Integer pID) {
        String auth = header.substring(7);
        if (!service.validateUser(auth,pID)) {
            return ResponseEntity.status(401).build();
        }
        if (pID.equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        UserModel obj = service.getSUser(id);
        if (obj != null) {
            return ResponseEntity.ok().body(obj);
        }
        return ResponseEntity.notFound().build();
    }


    /**
     * A função é o Get Request para obter um user para matchmaking
     * @return Irá retornar ok e o objeto com as informações do user
     * @author Fabian Nunes
     */
    @GetMapping(value = "/play")
    public ResponseEntity<UserModel> getRandomUser(@RequestHeader("Authorization") String header, @RequestBody UserModel user) {
        String auth = header.substring(7);
        if (!service.validateUser(auth, user.getId())) {
            return ResponseEntity.status(401).build();
        }
        UserModel obj = service.findMatchMaking();
        if (obj != null) {
            return ResponseEntity.ok().body(obj);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * A função é o PUT que irá atualizar o user com o resultado da partida
     * @param header token de segurança
     * @param result resultado da partida 0 perda 1 vitoria
     * @param  user objeto usermodel
     * @return Irá retornar ok caso nao ocorra erro
     * @author Fabian Nunes
     */
    @PutMapping("/result/{result}")
    public ResponseEntity<Object> resultMatch(@RequestHeader("Authorization") String header, @PathVariable(name = "result") Integer result, @RequestBody UserModel user) {
        String auth = header.substring(7);
        if (!service.validateUser(auth, user.getId())) {
            return ResponseEntity.status(401).build();
        }
        return service.matchResult(user.getId(), result);
    }

    /**
     * A função é o POST que irá introduzir a batalha na tabela history
     * @param winner id do vencedor
     * @param loser id do perdedor
     * @return Irá retornar ok
     * @author Fabian Nunes
     */
    @PostMapping("end/{winner}/{loser}")
    public ResponseEntity<Object> endMatch(@PathVariable(name = "winner") Integer winner, @PathVariable(name = "loser") Integer loser) {
        historyService.endMatch(winner, loser);
        return ResponseEntity.status(201).build();
    }
}
