package com.milanalbert.chatty.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestDtoUnitTest {

    @Test
    void can_create_login_request_dto() {

        LoginRequestDto requestDto = new LoginRequestDto("username","password");

        assertEquals("username", requestDto.username);
        assertEquals("password", requestDto.password);
    }
}