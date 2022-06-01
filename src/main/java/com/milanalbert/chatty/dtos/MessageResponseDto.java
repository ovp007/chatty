package com.milanalbert.chatty.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.milanalbert.chatty.models.Message;

import java.time.LocalDateTime;

public class MessageResponseDto implements ResponseDto {

  public final String text;

  @JsonProperty("author_id")
  public final Long authorId;

  @JsonProperty("created_at")
  public final LocalDateTime createdAt;

  @JsonCreator
  public MessageResponseDto(Message message) {
    this.text = message.getText();
    this.authorId = message.getPostedBy().getId();
    this.createdAt = message.getCreatedAt();
  }

  public String getText() {
    return text;
  }

  public Long getAuthorId() {
    return authorId;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
}
