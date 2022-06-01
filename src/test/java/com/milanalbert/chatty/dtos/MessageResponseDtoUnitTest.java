package com.milanalbert.chatty.dtos;

import com.milanalbert.chatty.models.AppUser;
import com.milanalbert.chatty.models.ChatRoom;
import com.milanalbert.chatty.models.Message;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageResponseDtoUnitTest {


    @Test
    void can_create_message_response_dto() {

        AppUser appUser = new AppUser("username", "email", "password");
        ChatRoom chatRoom = new ChatRoom("chat room name", appUser);
        Message message = new Message("message text", appUser, chatRoom);

        MessageResponseDto responseDto = new MessageResponseDto(message);

        assertEquals("message text", responseDto.text);

    }
}