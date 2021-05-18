package com.spaghettyArts.projectakrasia.services;

import com.spaghettyArts.projectakrasia.Utils.RandomString;
import com.spaghettyArts.projectakrasia.model.ResetModel;
import com.spaghettyArts.projectakrasia.model.UserModel;
import com.spaghettyArts.projectakrasia.repository.ResetRepository;
import com.spaghettyArts.projectakrasia.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResetService {

    @Autowired
    private ResetRepository repository;

    @Autowired
    private UserRepository userRpo;

    public ResetModel resetRequest(String email) {
        UserModel obj= userRpo.findUserModelByEmail(email);
        String token = RandomString.randomString(60);
        ResetModel reset = new ResetModel(token, obj);
        return repository.save(reset);
    }

}
