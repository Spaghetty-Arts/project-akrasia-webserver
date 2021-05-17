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

    public UserModel login(String email, String password) {
        UserModel userE =  repository.findUserModelByEmail(email);
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
        UserModel objU =  repository.findUserModelByUsername(obj.getUsername());
        UserModel objE =  repository.findUserModelByEmail(obj.getEmail());
        if (objE!= null || objU != null) {
            return null;
        } else {
            String pass = obj.getPassword();
            String hash = hashPassword(pass);
            UserModel newUser = new UserModel(obj.getUsername(), hash, obj.getEmail());
            return repository.save(newUser);
        }
    }

    public UserModel reset(String email, String password) {
        UserModel objE =  repository.findUserModelByEmail(email);
        if (objE == null) {
            return null;
        } else {
            String pass = password;
            String hash = hashPassword(pass);
            objE.setPassword(hash);
            return repository.save(objE);
        }
    }

}
