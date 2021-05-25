package com.spaghettyArts.projectakrasia.services;

import com.spaghettyArts.projectakrasia.model.ResetModel;
import com.spaghettyArts.projectakrasia.model.UserModel;
import com.spaghettyArts.projectakrasia.repository.ResetRepository;
import com.spaghettyArts.projectakrasia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.spaghettyArts.projectakrasia.Utils.DateValidation.updateLogin;
import static com.spaghettyArts.projectakrasia.Utils.Encryption.checkPassword;
import static com.spaghettyArts.projectakrasia.Utils.Encryption.hashPassword;
import static com.spaghettyArts.projectakrasia.Utils.InputValidation.checkEmpty;

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
        if(checkEmpty(email) || checkEmpty(password)) {
            return null;
        }

        UserModel userE =  repository.findUserModelByEmail(email);
        if (userE != null) {
            String paswordH = userE.getPassword();
            if(checkPassword(password, paswordH)) {
                UserModel obj = updateLogin(userE);
                return repository.save(obj);
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
            String defaultTime = "1970-01-01";
            try {
                Date defDate = new SimpleDateFormat("yyyy-MM-dd").parse(defaultTime);
                newUser.setLast_login(defDate);
                return repository.save(newUser);
            } catch (ParseException t) {
                return null;
            }
        }
    }

    public UserModel reset(String token, String email, String password) {
        if (checkEmpty(email) || checkEmpty(password) || checkEmpty(token)) {
            return null;
        }
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

    public UserModel changeName(int id, String username) {
        if(checkEmpty(username)) {
            return null;
        }
        UserModel obj = findByID(id);
        if (obj == null) {
            return null;
        }
        obj.setUsername(username);
        return repository.save(obj);
    }

}
