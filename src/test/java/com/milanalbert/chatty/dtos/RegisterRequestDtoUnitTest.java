package com.milanalbert.chatty.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterRequestDtoUnitTest {

    @Test
    void can_create_register_request_dto() {

        RegisterRequestDto requestDto = new RegisterRequestDto("username","email","password");

        assertEquals("username", requestDto.username);
        assertEquals("email", requestDto.email);
        assertEquals("password", requestDto.password);
    }
}