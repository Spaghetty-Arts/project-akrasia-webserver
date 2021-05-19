package com.spaghettyArts.projectakrasia.services;

import com.spaghettyArts.projectakrasia.model.ResetModel;
import com.spaghettyArts.projectakrasia.model.UserModel;
import com.spaghettyArts.projectakrasia.repository.ResetRepository;
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

    @Autowired
    private ResetRepository resetRepository;

    public List<UserModel> findAll() {
        return repository.findAll();
    }

    public UserModel findByID (Integer id) {
        Optional<UserModel> obj =  repository.findById(id);
        return obj.get();
    }

    public UserModel findByMail (String mail) {
        return repository.findUserModelByEmail(mail);
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

    public UserModel reset(String token, String email, String password) {
        ResetModel obj = resetRepository.findByTokenAndEmail(token, email);
        if (obj == null) {
            return null;
        } else {
            resetRepository.delete(obj);
            UserModel userObj = repository.findUserModelByEmail(email);
            String pass = password;
            String hash = hashPassword(pass);
            userObj.setPassword(hash);
            return repository.save(userObj);
        }
    }
}
