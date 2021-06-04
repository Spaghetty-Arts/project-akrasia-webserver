package com.spaghettyArts.projectakrasia.utils;


import org.mindrot.jbcrypt.BCrypt;

/**
 * Classe relativa encriptação da password usando BCrypt
 * @author Fabian Nunes
 * @version 0.1
 */
public class Encryption {

    private static int workload = 12;

    /**
     * Função para encriptar a password numa string de 60 caraters utilizando bcrypt
     * @param password_plaintext a password do user
     * @return Password encriptada
     * @author Fabian Nunes
     */
    public static String hashPassword(String password_plaintext) {
        String salt = BCrypt.gensalt(workload);
        String hashed_password = BCrypt.hashpw(password_plaintext, salt);

        return(hashed_password);
    }

    /**
     * Função para desencriptar a password comparando a password introduzida com a encriptada
     * @param password_plaintext a password do user
     * @param stored_hash a password encriptada
     * @return Retorna true se a password foi igual senão retorna false
     * @author Fabian Nunes
     */
    public static boolean checkPassword(String password_plaintext, String stored_hash) {
        boolean password_verified = false;

        if(null == stored_hash || !stored_hash.startsWith("$2a$"))
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

        password_verified = BCrypt.checkpw(password_plaintext, stored_hash);

        return(password_verified);
    }



}
