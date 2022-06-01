package com.milanalbert.chatty.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateMessageRequestDtoUnitTest {

    @Test
    void can_create_create_message_request_dto() {

        CreateMessageRequestDto requestDto = new CreateMessageRequestDto("message text");

        assertEquals("message text", requestDto.text);
    }
}