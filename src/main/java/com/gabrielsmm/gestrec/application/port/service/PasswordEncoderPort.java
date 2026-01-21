package com.gabrielsmm.gestrec.application.port.service;

public interface PasswordEncoderPort {

    String encode(String rawPassword);

    boolean matches(String rawPassword, String encodedPassword);

}
