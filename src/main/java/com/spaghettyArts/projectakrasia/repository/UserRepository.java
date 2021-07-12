package com.spaghettyArts.projectakrasia.repository;

import com.spaghettyArts.projectakrasia.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * O repositório para as querys a tabela user da base de dados
 * @author Fabian Nunes
 * @version 1.0
 */
public interface UserRepository extends JpaRepository<UserModel, Integer> {

    UserModel findUserModelByEmail(String email);

    UserModel findUserModelByUsername(String user);

    List<UserModel> findAllByUserOnline(Integer id);
}
