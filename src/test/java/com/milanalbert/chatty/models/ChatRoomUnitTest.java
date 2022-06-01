package com.milanalbert.chatty.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChatRoomUnitTest {

    @Test
    void can_create_chat_room(){

        AppUser appUser = new AppUser("username", "email", "password");
        ChatRoom chatRoom = new ChatRoom("chat room name", appUser);

        assertEquals("username", chatRoom.getOwner().getUsername());
        assertEquals("email", chatRoom.getOwner().getEmail());
        assertEquals("password", chatRoom.getOwner().getPassword());
        assertEquals("chat room name", chatRoom.getName());

    }
}