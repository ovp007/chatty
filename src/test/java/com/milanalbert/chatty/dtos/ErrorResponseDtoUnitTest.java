package com.milanalbert.chatty.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseDtoUnitTest {

    @Test
    void can_create_error_response_dto() {

        ErrorResponseDto responseDto = new ErrorResponseDto("error text");

        assertEquals("error text", responseDto.error);
    }
}