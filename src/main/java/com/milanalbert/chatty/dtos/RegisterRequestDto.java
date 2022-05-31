package com.milanalbert.chatty.dtos;

public class RegisterRequestDto implements RequestDto {

  public final String username;
  public final String email;
  public final String password;

  public RegisterRequestDto(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }
}
