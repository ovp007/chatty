package com.milanalbert.chatty.controllers;

import com.milanalbert.chatty.dtos.StatusResponseDto;
import com.milanalbert.chatty.dtos.TokenResponseDto;
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

import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration
@WithMockUser
@WebMvcTest(RegisterRestController.class)
class RegisterRestControllerUnitTest {

  private @MockBean UserService userService;

  @Autowired private MockMvc mockMvc;

  @MockBean private JwtUtil jwtUtil;

  @MockBean private UserDetailServiceImpl userDetailService;

  @MockBean private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

  @Test
  void can_register() throws Exception {

    StatusResponseDto responseDto = new StatusResponseDto("ok");

    Mockito.when(userService.store(Mockito.any())).thenReturn(responseDto);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"username\": \"username\", "
                        + "\"email\": \"email\","
                        + " \"password\": \"password\" }"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message", is("ok")));
  }

  @Test
  void can_login() throws Exception {

    TokenResponseDto tokenResponseDto = new TokenResponseDto("Jwt token");

    Mockito.when(userService.login(Mockito.any())).thenReturn(tokenResponseDto);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"username\", " + " \"password\": \"password\" }"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token", is("Jwt token")));
  }
}
