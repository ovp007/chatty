package com.milanalbert.chatty.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.milanalbert.chatty.models.AppUser;

public class AppUserResponseDto implements ResponseDto {

  public final Long id;
  public final String username;
  public final String email;

  @JsonCreator
  public AppUserResponseDto(AppUser appUser) {
    this.id = appUser.getId();
    this.username = appUser.getUsername();
    this.email = appUser.getEmail();
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }
}
