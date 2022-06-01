package com.milanalbert.chatty.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenResponseDtoUnitTest {

    @Test
    void can_create_token_response_dto() {

        TokenResponseDto responseDto = new TokenResponseDto("JWT token");

        assertEquals("JWT token", responseDto.token);
    }
}