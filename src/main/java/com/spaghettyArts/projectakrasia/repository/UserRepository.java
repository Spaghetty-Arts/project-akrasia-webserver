package com.spaghettyArts.projectakrasia.repository;

import com.spaghettyArts.projectakrasia.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * O repositório para as querys a tabela user da base de dados
 * @author Fabian Nunes
 * @version 0.1
 */
public interface UserRepository extends JpaRepository<UserModel, Integer> {


    UserModel findUserModelByEmail(String email);

    UserModel findUserModelByUsername(String user);


}
