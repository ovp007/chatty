package com.milanalbert.chatty.controllers;

import com.milanalbert.chatty.dtos.AppUserResponseDto;
import com.milanalbert.chatty.dtos.ChatRoomResponseDto;
import com.milanalbert.chatty.dtos.StatusResponseDto;
import com.milanalbert.chatty.models.AppUser;
import com.milanalbert.chatty.security.RestAuthenticationEntryPoint;
import com.milanalbert.chatty.services.UserDetailServiceImpl;
import com.milanalbert.chatty.services.UserService;
import com.milanalbert.chatty.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration
@WithMockUser
@WebMvcTest(UserRestController.class)
class UserRestControllerUnitTest {

  @MockBean UserService userService;

  @Autowired MockMvc mockMvc;

  @MockBean private JwtUtil jwtUtil;

  @MockBean private UserDetailServiceImpl userDetailService;

  @MockBean private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

  @Test
  void get_get_active_users() throws Exception {

    AppUser appUser = new AppUser("username", "email", "password");
    AppUserResponseDto appUserResponseDto = new AppUserResponseDto(appUser);
    AppUser appUser2 = new AppUser("username2", "email2", "password");
    AppUserResponseDto appUserResponseDto2 = new AppUserResponseDto(appUser2);

    List<AppUserResponseDto> users = new ArrayList<>();
    users.add(appUserResponseDto);
    users.add(appUserResponseDto2);

    Mockito.when(userService.getActiveUsers()).thenReturn(users);

    mockMvc
        .perform(MockMvcRequestBuilders.get("/users/active"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].username", is("username")))
        .andExpect(jsonPath("$[0].email", is("email")))
        .andExpect(jsonPath("$[1].username", is("username2")))
        .andExpect(jsonPath("$[1].email", is("email2")));
  }

  @Test
  void can_logout() throws Exception {

    StatusResponseDto responseDto = new StatusResponseDto("ok");

    Mockito.when(userService.logout(Mockito.any())).thenReturn(responseDto);

    mockMvc
        .perform(MockMvcRequestBuilders.post("/users/logout").header(AUTHORIZATION, "jwt token"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message", is("ok")));
  }
}
