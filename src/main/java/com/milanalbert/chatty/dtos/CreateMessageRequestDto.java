package com.milanalbert.chatty.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;

public class CreateMessageRequestDto implements RequestDto {

  public final String text;

  @JsonCreator
  public CreateMessageRequestDto(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }
}
