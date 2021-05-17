package com.spaghettyArts.projectakrasia.services;

import com.spaghettyArts.projectakrasia.model.UserModel;
import com.spaghettyArts.projectakrasia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.spaghettyArts.projectakrasia.Utils.Encryption.checkPassword;
import static com.spaghettyArts.projectakrasia.Utils.Encryption.hashPassword;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<UserModel> findAll() {
        return repository.findAll();
    }

    public UserModel findByID (Integer id) {
        Optional<UserModel> obj =  repository.findById(id);
        return obj.get();
    }

    public UserModel login(String user, String password) {
        UserModel userE =  repository.findUserModelByUsername(user);
        if (userE != null) {
            String paswordH = userE.getPassword();
            if(checkPassword(password, paswordH)) {
                return userE;
            } else
                return null;
        } else {
            return null;
        }
    }

    public UserModel register(UserModel obj) {
        String username = obj.getUsername();
        UserModel userE =  repository.findUserModelByUsername(username);
        if (userE != null) {
            return null;
        } else {
            String pass = obj.getPassword();
            String hash = hashPassword(pass);
            UserModel newUser = new UserModel(username, hash, obj.getEmail());
            return repository.save(newUser);
        }
    }
}
