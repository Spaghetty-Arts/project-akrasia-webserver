package com.spaghettyArts.projectakrasia.Utils;

import com.spaghettyArts.projectakrasia.model.UserModel;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateValidation {

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
        } else {
            obj.setLogin_reward(0);
        }
        obj.setLast_login(now);
       return obj;
    }
}
