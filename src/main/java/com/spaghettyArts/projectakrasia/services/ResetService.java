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

@Service
public class ResetService {

    @Autowired
    private ResetRepository repository;

    @Autowired
    private UserRepository userRpo;

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
