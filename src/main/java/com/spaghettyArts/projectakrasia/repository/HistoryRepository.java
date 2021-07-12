package com.spaghettyArts.projectakrasia.repository;

import com.spaghettyArts.projectakrasia.model.HistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * O repositório para as querys a tabela history da base de dados
 * @author Fabian Nunes
 * @version 1.0
 */
public interface HistoryRepository extends JpaRepository<HistoryModel, Integer> {


}
