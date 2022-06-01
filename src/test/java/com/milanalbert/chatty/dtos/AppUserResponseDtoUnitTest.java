package com.milanalbert.chatty.dtos;

import com.milanalbert.chatty.models.AppUser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppUserResponseDtoUnitTest {

    @Test
    void can_create_appuser_response_dto(){

        AppUser appUser = new AppUser("username", "email", "password");

        AppUserResponseDto responseDto = new AppUserResponseDto(appUser);

        assertEquals("username", responseDto.username);
        assertEquals("email", responseDto.email);
        assertNull(responseDto.getId());


    }

}