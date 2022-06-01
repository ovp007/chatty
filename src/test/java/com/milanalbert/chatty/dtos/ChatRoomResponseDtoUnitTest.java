package com.milanalbert.chatty.dtos;

import com.milanalbert.chatty.models.AppUser;
import com.milanalbert.chatty.models.ChatRoom;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChatRoomResponseDtoUnitTest {

  @Test
  void can_create_chat_room_response_dto() {

    AppUser appUser = new AppUser("username", "email", "password");
    ChatRoom chatRoom = new ChatRoom("chat room name", appUser);

    ChatRoomResponseDto responseDto = new ChatRoomResponseDto(chatRoom);

    assertEquals("chat room name", responseDto.name);
    assertEquals("username", responseDto.owner);
    assertNull(responseDto.ownerId);
  }
}
