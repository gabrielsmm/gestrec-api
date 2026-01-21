package com.gabrielsmm.gestrec.shared.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (args == null || args.length == 0) {
            System.out.println("123 => " + encoder.encode("123"));
            return;
        }

        for (String pwd : args) {
            String hash = encoder.encode(pwd);
            System.out.println(pwd + " => " + hash);
        }
    }
}

