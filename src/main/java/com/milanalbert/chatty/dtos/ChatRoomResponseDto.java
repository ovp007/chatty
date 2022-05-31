package com.milanalbert.chatty.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.milanalbert.chatty.models.ChatRoom;

public class ChatRoomResponseDto implements ResponseDto {

  public final Long id;
  public final String name;

  @JsonProperty("owner_id")
  public final Long ownerId;

  public final String owner;

  @JsonCreator
  public ChatRoomResponseDto(ChatRoom chatRoom) {
    this.id = chatRoom.getId();
    this.name = chatRoom.getName();
    this.ownerId = chatRoom.getOwner().getId();
    this.owner = chatRoom.getOwner().getUsername();
  }
}
