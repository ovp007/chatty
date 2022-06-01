package com.milanalbert.chatty.controllers;

import com.milanalbert.chatty.dtos.MessageResponseDto;
import com.milanalbert.chatty.dtos.StatusResponseDto;
import com.milanalbert.chatty.models.AppUser;
import com.milanalbert.chatty.models.ChatRoom;
import com.milanalbert.chatty.models.Message;
import com.milanalbert.chatty.security.RestAuthenticationEntryPoint;
import com.milanalbert.chatty.services.MessageService;
import com.milanalbert.chatty.services.UserDetailServiceImpl;
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

import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration
@WithMockUser
@WebMvcTest(MessageRestController.class)
class MessageRestControllerUnitTest {

  @MockBean MessageService messageService;

  @Autowired MockMvc mockMvc;

  @MockBean private JwtUtil jwtUtil;

  @MockBean private UserDetailServiceImpl userDetailService;

  @MockBean private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

  @Test
  void can_show_all_messages() throws Exception {

    AppUser appUser = new AppUser("username", "email", "password");
    ChatRoom chatRoom = new ChatRoom("chat room name", appUser);
    Message message = new Message("message text", appUser, chatRoom);
    Message message2 = new Message("message2 text", appUser, chatRoom);

    MessageResponseDto responseDto = new MessageResponseDto(message);
    MessageResponseDto responseDto2 = new MessageResponseDto(message2);

    List<MessageResponseDto> messages = new ArrayList<>();

    messages.add(responseDto);
    messages.add(responseDto2);

    Mockito.when(messageService.getAllMessages(Mockito.any())).thenReturn(messages);

    mockMvc
        .perform(MockMvcRequestBuilders.get("/rooms/1/messages").header(AUTHORIZATION, "jwt token"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].text", is("message text")))
        .andExpect(jsonPath("$[1].text", is("message2 text")));
  }

  @Test
  void can_store_message() throws Exception {

    StatusResponseDto responseDto = new StatusResponseDto("ok");

    Mockito.when(messageService.store(Mockito.any(), Mockito.any(), Mockito.any()))
        .thenReturn(responseDto);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/rooms/1/messages")
                .header(AUTHORIZATION, "jwt token")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"text \": \"message text\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message", is("ok")));
  }

  @Test
  void can_delete_message() throws Exception {
    StatusResponseDto responseDto = new StatusResponseDto("ok");

    Mockito.when(messageService.delete(Mockito.any(), Mockito.any())).thenReturn(responseDto);

    mockMvc
        .perform(
            MockMvcRequestBuilders.delete("/rooms/messages/1")
                .header(AUTHORIZATION, "jwt token")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message", is("ok")));
  }
}
