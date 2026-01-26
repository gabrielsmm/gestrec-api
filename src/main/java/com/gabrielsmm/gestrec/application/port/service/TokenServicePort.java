package com.gabrielsmm.gestrec.application.port.service;

public interface TokenServicePort {

    String gerarToken(Long usuarioId, String email, String perfil);

}

