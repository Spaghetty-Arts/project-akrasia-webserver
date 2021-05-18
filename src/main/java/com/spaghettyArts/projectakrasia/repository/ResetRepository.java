package com.spaghettyArts.projectakrasia.repository;

import com.spaghettyArts.projectakrasia.model.ResetModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResetRepository extends JpaRepository<ResetModel, Integer> {

    ResetModel findByTokenAndEmail(String token, String email);
}