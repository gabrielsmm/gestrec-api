package com.gabrielsmm.gestrec.application.port.service;

public interface TokenService {

    String gerarToken(Long usuarioId, String email, String perfil);

}

