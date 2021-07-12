package com.spaghettyArts.projectakrasia.repository;

import com.spaghettyArts.projectakrasia.model.ResetModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * O repositório para as querys a tabela reset da base de dados
 * @author Fabian Nunes
 * @version 1.0
 */
public interface ResetRepository extends JpaRepository<ResetModel, Integer> {

    ResetModel findByTokenAndEmail(String token, String email);

    ResetModel findByEmail(String email);
}
