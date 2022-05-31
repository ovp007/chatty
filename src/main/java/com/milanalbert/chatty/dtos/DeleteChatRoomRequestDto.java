package com.milanalbert.chatty.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;

public class DeleteChatRoomRequestDto implements RequestDto {

  public final Long id;

  @JsonCreator
  public DeleteChatRoomRequestDto(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }
}
