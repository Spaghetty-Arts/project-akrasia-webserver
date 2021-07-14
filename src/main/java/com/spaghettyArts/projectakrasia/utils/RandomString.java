package com.spaghettyArts.projectakrasia.utils;

import java.security.SecureRandom;

/**
 * Classe relativa a geração de tokens
 * @author Fabian Nunes
 * @version 1.0
 */
public class RandomString {
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    /**
     * Função para gerar um token com letras e digitos
     * @param len tamanho do token
     * @return retorna o token
     * @author Fabian Nunes
     */
    public static String randomString(int len){
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }
}
