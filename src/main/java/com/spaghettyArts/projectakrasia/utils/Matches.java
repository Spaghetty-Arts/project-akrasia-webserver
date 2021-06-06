package com.spaghettyArts.projectakrasia.utils;

/**
 * Classe relativa a obtençao do rank do user
 * @author Fabian Nunes
 * @version 0.1
 */
public class Matches {

    /**
     * Função que irá gerar o rank do user caso tenha menos de 25 batalhas terá rank 1, caso tenha 25 e menos de 50
     * terá rank 2 se ganhar 50% das batalhas totais e se tiver mais de 50 batalhas terá rank 3 se ganhar mais de 75%, rank 2 se ganhar mais de 50%
     * @param loses numero de partidas perdidas
     * @param wins numero de partidas ganhas
     * @return rank
     * @autor Fabian Nunes
     */
    public static Integer defineRank(int loses, int wins) {
        int total = loses + wins;
        if (total >= 50) {
            if (wins > total * 0.75) {
                return 3;
            } else if(wins > total * 0.5) {
                return 2;
            } else {
                return 1;
            }
        } else if (total >= 25 && total < 50) {
            if (wins > total * 0.5) {
                return 2;
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }
}
