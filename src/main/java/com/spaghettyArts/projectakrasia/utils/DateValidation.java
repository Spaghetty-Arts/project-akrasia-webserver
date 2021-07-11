package com.spaghettyArts.projectakrasia.utils;

import com.spaghettyArts.projectakrasia.model.UserModel;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Classe relativa a validação de datas
 * @author Fabian Nunes
 * @version 0.1
 */
public class DateValidation {

    /**
     * Função para atualizar o timestamp de login do user caso seja um login consecutivo irá atualizar esse dado, caso
     * não seja volta para 1.
     * @param obj Objeto Usermodel que vêm do login
     * @return Irá retornar o objeto que foi recebido como parametro com o login timestamp e reward atualizado
     * @author Fabian Nunes
     */
    public static UserModel updateLogin(UserModel obj) {
        Date now = new Date();

        Date oldTime = obj.getLast_login();

        LocalDate localDate = now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        LocalDate localDateO = oldTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int dayN = localDate.getDayOfYear();
        int dayO = localDateO.getDayOfYear();

        if (dayN - dayO == 1) {
            int row = obj.getLogin_reward();
            obj.setLogin_reward(row+1);
            obj.setGot_reward(0);
        } else if (dayN - dayO == 0) {
            obj.setLast_login(now);
            obj.setGot_reward(1);
            return obj;
        } else {
            obj.setLogin_reward(1);
            obj.setGot_reward(0);
        }
        obj.setLast_login(now);
       return obj;
    }
}
