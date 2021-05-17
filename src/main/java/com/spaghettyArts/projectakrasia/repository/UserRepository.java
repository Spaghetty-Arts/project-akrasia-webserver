package com.spaghettyArts.projectakrasia.repository;

import com.spaghettyArts.projectakrasia.model.UserModel;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserModel, Integer> {


    UserModel findUserModelByEmail(String email);

    UserModel findUserModelByUsername(String user);
}
