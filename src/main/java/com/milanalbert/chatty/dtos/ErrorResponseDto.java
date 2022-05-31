package com.milanalbert.chatty.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ErrorResponseDto {

  public final String error;

  @JsonCreator
  public ErrorResponseDto(String error) {
    this.error = error;
  }
}
