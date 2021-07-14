package com.spaghettyArts.projectakrasia.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe relativa a validação de Inputs
 * @author Fabian Nunes
 * @version 1.0
 */
public class InputValidation {

    /**
     * Função ver se o input é vazio ou não
     * @param input valor introduzido pelo user
     * @return retorna True se for vazio ou false se não for
     * @author Fabian Nunes
     */
    public static boolean checkEmpty(String input) {
        String noSpace = input.replaceAll("\\s+","");
        return noSpace.equals("");
    }

    /**
     * Função ver a password é valida: Se tem mais de 7 caraters, 1 letra maiscula, 1 minuscula, 1 digito
     * @param password password introduzida
     * @return retorna True se for válida e false se for inválida
     * @author Fabian Nunes
     */
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

    /**
     * Função ver se o username tem mais de 7 caraters
     * @param username username introduzido pelo utilizador
     * @return retorna True se tiver mais de 7 caraters senãoi retorna false
     * @author Fabian Nunes
     */
    public static boolean checkUsername(String username) {
        return username.length() >= 8;
    }
}
