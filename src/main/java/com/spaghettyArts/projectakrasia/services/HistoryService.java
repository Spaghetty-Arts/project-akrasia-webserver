package com.spaghettyArts.projectakrasia.services;

import com.spaghettyArts.projectakrasia.model.HistoryModel;
import com.spaghettyArts.projectakrasia.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * A classe serviço com as funções relativas a tabela history
 * @author Fabian Nunes
 * @version 0.1
 */
@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    /**
     * A função irá criar guardar o jogo na base de dados
     * @param winer id do vencedor
     * @param loser id do perdedor
     * @author Fabian Nunes
     */
    public HistoryModel endMatch(int winer, int loser) {
        HistoryModel obj = new HistoryModel(winer, loser);
        return historyRepository.save(obj);
    }
}
