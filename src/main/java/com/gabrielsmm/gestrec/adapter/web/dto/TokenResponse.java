package com.gabrielsmm.gestrec.adapter.web.dto;

public record TokenResponse(
        String token,
        String tipo
) {
    public TokenResponse(String token) {
        this(token, "Bearer");
    }
}
