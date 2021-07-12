package com.spaghettyArts.projectakrasia.utils;

/**
 * Classe relativa ao daily reward
 * @author Fabian Nunes
 * @version 1.0
 */
public class DailyReward {

    /**
     * Função para obter o reward baseado na sequencia de dias
     * não seja volta para 1.
     * @param day dia do daily reward
     * @return Irá retornar o valor a receber de reward
     * @author Fabian Nunes
     */
    public static int getDaily(int day) {
        if (day < 7) {
            return day * 100;
        } else {
            return 700;
        }
    }
}
