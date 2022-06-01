package com.milanalbert.chatty.controllers;

import com.milanalbert.chatty.models.AppUser;
import com.milanalbert.chatty.models.ChatRoom;
import com.milanalbert.chatty.repositories.ChatRoomRepository;
import com.milanalbert.chatty.repositories.UserRepository;
import com.milanalbert.chatty.services.UserDetailServiceImpl;
import com.milanalbert.chatty.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ChatRoomRestControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ChatRoomRepository chatRoomRepository;

  @Autowired private UserRepository userRepository;

  @Autowired private JwtUtil jwtUtil;

  @Autowired private UserDetailServiceImpl userDetailService;

  private String token;

  @BeforeEach
  void setUp() {

    assertEquals(0, chatRoomRepository.count());

    AppUser appUser = new AppUser("username", "email", "password");
    userRepository.save(appUser);

    ChatRoom chatRoom = new ChatRoom("chat room name", appUser);
    ChatRoom chatRoom2 = new ChatRoom("chat room name2", appUser);
    chatRoomRepository.save(chatRoom);
    chatRoomRepository.save(chatRoom2);

    assertEquals(2, chatRoomRepository.count());

    token = jwtUtil.generateToken(userDetailService.loadUserByUsername(appUser.getUsername()));

  }

  @Test
  void getAllRooms() throws Exception {

    mockMvc
        .perform(MockMvcRequestBuilders.get("/rooms").header(AUTHORIZATION, "jwt token"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name", is("chat room name")))
        .andExpect(jsonPath("$[0].owner", is("username")))
        .andExpect(jsonPath("$[0].id", is(1)))
        .andExpect(jsonPath("$[1].name", is("chat room name2")))
        .andExpect(jsonPath("$[1].owner", is("username")))
        .andExpect(jsonPath("$[1].id", is(2)));
  }

  @Test
  void store() throws Exception {

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/rooms")
                .header(AUTHORIZATION, "Bearer " + token)
                .content("{\"name\": \"new chat room3\"}")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message", is("ok")));

    assertEquals(3, chatRoomRepository.count());
  }

  @Test
  void delete() throws Exception {

    mockMvc
        .perform(MockMvcRequestBuilders.delete("/rooms/1").header(AUTHORIZATION, "Bearer " + token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message", is("ok")));

    assertEquals(1, chatRoomRepository.count());
  }

  @Test
  void show() throws Exception {

    mockMvc
        .perform(MockMvcRequestBuilders.get("/rooms/1").header(AUTHORIZATION, "Bearer " + token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is("chat room name")))
        .andExpect(jsonPath("$.owner", is("username")))
        .andExpect(jsonPath("$.id", is(1)));
  }
}
