package com.milanalbert.chatty.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;

public class CreateChatRoomRequestDto {

  public final String name;

  @JsonCreator
  public CreateChatRoomRequestDto(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
