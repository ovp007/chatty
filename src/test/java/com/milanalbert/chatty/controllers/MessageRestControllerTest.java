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
import org.springframework.http.MediaType;
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
class MessageRestControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private MessageRepository messageRepository;

  @Autowired private ChatRoomRepository chatRoomRepository;

  @Autowired private UserRepository userRepository;

  @Autowired private JwtUtil jwtUtil;

  @Autowired private UserDetailServiceImpl userDetailService;

  private String token;

  @BeforeEach
  void setUp() {

    assertEquals(0, messageRepository.count());

    AppUser appUser = new AppUser("username", "email", "password");
    userRepository.save(appUser);

    ChatRoom chatRoom = new ChatRoom("chat room name", appUser);
    chatRoomRepository.save(chatRoom);

    Message message1 = new Message("message text", appUser, chatRoom);
    Message message2 = new Message("message text2", appUser, chatRoom);
    messageRepository.save(message1);
    messageRepository.save(message2);

    assertEquals(2, messageRepository.count());

    token = jwtUtil.generateToken(userDetailService.loadUserByUsername(appUser.getUsername()));
  }

  @Test
  void index() throws Exception {

    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/rooms/1/messages")
                .header(AUTHORIZATION, "Bearer " + token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].text", is("message text")))
        .andExpect(jsonPath("$[0].author_id", is(1)))
        .andExpect(jsonPath("$[1].text", is("message text2")))
        .andExpect(jsonPath("$[1].author_id", is(1)));
  }

  @Test
  void store() throws Exception {

    mockMvc
            .perform(
                    MockMvcRequestBuilders.post("/rooms/1/messages")
                            .header(AUTHORIZATION, "Bearer "+token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"text\": \"message text\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message", is("ok")));

    assertEquals(3, messageRepository.count());
  }

  @Test
  void delete() throws Exception {
    mockMvc
            .perform(
                    MockMvcRequestBuilders.delete("/rooms/messages/1")
                            .header(AUTHORIZATION, "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message", is("ok")));

  }
}
