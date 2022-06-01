package com.milanalbert.chatty.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusResponseDtoUnitTest {

    @Test
    void can_create_status_response_dto() {

        StatusResponseDto responseDto = new StatusResponseDto("ok");

        assertEquals("ok", responseDto.message);
    }
}