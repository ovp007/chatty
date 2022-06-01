package com.milanalbert.chatty.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageUnitTest {

    @Test
    void can_create_message(){

        AppUser appUser = new AppUser("username", "email", "password");
        ChatRoom chatRoom = new ChatRoom("chat room name", appUser);
        Message message = new Message("message text", appUser, chatRoom);

        assertEquals("username", message.getChatRoom().getOwner().getUsername());
        assertEquals("email", message.getChatRoom().getOwner().getEmail());
        assertEquals("password", message.getChatRoom().getOwner().getPassword());
        assertEquals("chat room name", message.getChatRoom().getName());
        assertEquals("message text", message.getText());

    }
}