package com.gabrielsmm.gestrec.application.port;

public interface TokenService {

    String gerarToken(Long usuarioId, String email, String perfil);

}

