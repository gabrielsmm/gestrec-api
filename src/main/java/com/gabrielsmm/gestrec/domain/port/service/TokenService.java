package com.gabrielsmm.gestrec.domain.port.service;

public interface TokenService {

    String gerarToken(Long usuarioId, String email, String perfil);

}

