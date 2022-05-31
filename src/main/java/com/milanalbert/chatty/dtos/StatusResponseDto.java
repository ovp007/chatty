package com.milanalbert.chatty.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;

public class StatusResponseDto implements ResponseDto {

  public final String message;

  @JsonCreator
  public StatusResponseDto(String message) {
    this.message = message;
  }
}
