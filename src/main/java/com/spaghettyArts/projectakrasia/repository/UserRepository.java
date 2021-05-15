package com.spaghettyArts.projectakrasia.repository;

import com.spaghettyArts.projectakrasia.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Integer> {

}
