package com.milanalbert.chatty.controllers;

import com.milanalbert.chatty.models.AppUser;
import com.milanalbert.chatty.models.ChatRoom;
import com.milanalbert.chatty.models.Message;
import com.milanalbert.chatty.repositories.ChatRoomRepository;
import com.milanalbert.chatty.repositories.MessageRepository;
import com.milanalbert.chatty.repositories.UserRepository;
import com.milanalbert.chatty.services.UserDetailServiceImpl;
import com.milanalbert.chatty.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserRestControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private UserRepository userRepository;

  @Autowired private JwtUtil jwtUtil;

  @Autowired private UserDetailServiceImpl userDetailService;

  private String token;
  private AppUser appUser;

  @BeforeEach
  void setUp() {

    assertEquals(0, userRepository.count());

    appUser = new AppUser("username", "email", "password");
    AppUser appUser2 = new AppUser("username2", "email2", "password");

    appUser.setLogIn(true);
    appUser2.setLogIn(true);
    userRepository.save(appUser);
    userRepository.save(appUser2);

    assertEquals(2, userRepository.count());

    token = jwtUtil.generateToken(userDetailService.loadUserByUsername(appUser.getUsername()));
  }

  @Test
  void getActiveUsers() throws Exception {

    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/users/active").header(AUTHORIZATION, "Bearer " + token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].username", is("username")))
        .andExpect(jsonPath("$[0].email", is("email")))
        .andExpect(jsonPath("$[0].id", is(1)));
  }

  @Test
  void logout() throws Exception {
    appUser.setLogIn(false);
    userRepository.save(appUser);

    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/users/active").header(AUTHORIZATION, "Bearer " + token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].username", is("username2")))
        .andExpect(jsonPath("$[0].email", is("email2")))
        .andExpect(jsonPath("$[0].id", is(2)));
  }
}
