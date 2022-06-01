package com.milanalbert.chatty.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppUserUnitTest {

    @Test
    void can_create_appuser(){

        AppUser appUser = new AppUser("username", "email", "password");

        assertEquals("username", appUser.getUsername());
        assertEquals("email", appUser.getEmail());
        assertEquals("password", appUser.getPassword());
        assertNull(appUser.getId());
        assertFalse(appUser.getLogIn());

    }

}