package com.spaghettyArts.projectakrasia.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidation {

    public static boolean checkEmpty(String input) {
        String noSpace = input.replaceAll("\\s+","");
        return noSpace.equals("");
    }

    public static boolean checkPasswordInput(String password) {
        Pattern UpperCasePatten = Pattern.compile("[A-Z]");
        Pattern lowerCasePatten = Pattern.compile("[a-z]");
        Pattern digitCasePatten = Pattern.compile("[0-9]");

        if (password.length() < 8) {
            return false;
        }

        Matcher upperM = UpperCasePatten.matcher(password);
        Matcher lowerM = lowerCasePatten.matcher(password);
        Matcher digitM = digitCasePatten.matcher(password);

        return upperM.find() && lowerM.find() && digitM.find();
    }

    public static boolean checkUsername(String username) {
        return username.length() >= 8;
    }
}
