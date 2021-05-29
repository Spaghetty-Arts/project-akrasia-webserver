package com.spaghettyArts.projectakrasia.utils;

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

        if (!UpperCasePatten.matcher(password).find()) {
            return false;
        }

        if (!lowerCasePatten.matcher(password).find()) {
            return false;
        }

        if (!digitCasePatten.matcher(password).find()) {
            return false;
        }

        return true;
    }

    public static boolean checkUsername(String username) {
        return username.length() >= 8;
    }
}
