package com.spaghettyArts.projectakrasia.services;

import com.spaghettyArts.projectakrasia.utils.RandomString;
import com.spaghettyArts.projectakrasia.model.ResetModel;
import com.spaghettyArts.projectakrasia.model.UserModel;
import com.spaghettyArts.projectakrasia.repository.ResetRepository;
import com.spaghettyArts.projectakrasia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static com.spaghettyArts.projectakrasia.utils.InputValidation.checkEmpty;

/**
 * O serviço com as funções relativas ao reset de password
 * @author Fabian Nunes
 * @version 0.1
 */
@Service
public class ResetService {

    @Autowired
    private ResetRepository repository;

    @Autowired
    private UserRepository userRpo;

    /**
     * A função relativa ao pedido de reset de password. Se for válido será criado um objeto ResetModel e mandado para a base de dados
     * @param email Email do utilizador que pretende dar reset a password
     * @return Dependo do resultado será retornado um resultado diferente, caso o email seja uma string vazia será retornada uma excepção com status 400,
     * se o email já tiver associado a um pedido será retornado um 409, se o email não pertencer a um user será retorando um 404,
     * se for válido será retornado o objeto ResetModel criado.
     * @author Fabian Nunes
     */
    public ResetModel resetRequest(String email) {
        if(checkEmpty(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        ResetModel objRes = repository.findByEmail(email);
        if(objRes != null) {
           throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        UserModel obj= userRpo.findUserModelByEmail(email);

        if(obj == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        String token = RandomString.randomString(60);
        ResetModel reset = new ResetModel(token, obj);
        return repository.save(reset);
    }

}
