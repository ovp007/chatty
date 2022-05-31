package com.milanalbert.chatty.dtos;

public class TokenResponseDto implements ResponseDto {

    public final String token;

    public TokenResponseDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
