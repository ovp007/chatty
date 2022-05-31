package com.milanalbert.chatty.dtos;

public class LoginRequestDto {

    public final String username;
    public final String password;

    public LoginRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
