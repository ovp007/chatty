package com.milanalbert.chatty.dtos;

import com.milanalbert.chatty.models.AppUser;
import com.milanalbert.chatty.models.ChatRoom;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateChatRoomRequestDtoUnitTest {

  @Test
  void can_create_create_chat_room_request_dto() {

    CreateChatRoomRequestDto requestDto = new CreateChatRoomRequestDto("chat room name");

    assertEquals("chat room name", requestDto.name);
  }
}
