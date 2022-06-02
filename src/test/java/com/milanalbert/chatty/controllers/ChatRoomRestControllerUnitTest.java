package com.milanalbert.chatty.controllers;

import com.milanalbert.chatty.dtos.ChatRoomResponseDto;
import com.milanalbert.chatty.dtos.StatusResponseDto;
import com.milanalbert.chatty.exeptions.ChatRoomNameAlreadyTakenException;
import com.milanalbert.chatty.exeptions.ChatRoomNameIsMissingException;
import com.milanalbert.chatty.exeptions.ChatRoomNotExistException;
import com.milanalbert.chatty.exeptions.EmptyRequestBodyException;
import com.milanalbert.chatty.models.AppUser;
import com.milanalbert.chatty.models.ChatRoom;
import com.milanalbert.chatty.security.RestAuthenticationEntryPoint;
import com.milanalbert.chatty.services.ChatRoomService;
import com.milanalbert.chatty.services.UserDetailServiceImpl;
import com.milanalbert.chatty.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration
@WithMockUser
@WebMvcTest(ChatRoomRestController.class)
class ChatRoomRestControllerUnitTest {

  @MockBean private ChatRoomService chatRoomService;

  @Autowired private MockMvc mockMvc;

  @MockBean private JwtUtil jwtUtil;

  @MockBean private UserDetailServiceImpl userDetailService;

  @MockBean private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

  private List<ChatRoomResponseDto> rooms;

  @BeforeEach
  void setUp() {

    AppUser appUser = new AppUser("username", "email", "password");
    ChatRoom chatRoom = new ChatRoom("chat room name", appUser);

    AppUser appUser2 = new AppUser("username2", "email2", "password");
    ChatRoom chatRoom2 = new ChatRoom("chat room name 2", appUser2);

    rooms = new ArrayList<>();
    rooms.add(new ChatRoomResponseDto(chatRoom));
    rooms.add(new ChatRoomResponseDto(chatRoom2));
  }

  @Test
  void get_all_chat_rooms() throws Exception {

    Mockito.when(chatRoomService.getAllChatRooms()).thenReturn(rooms);

    mockMvc
        .perform(MockMvcRequestBuilders.get("/rooms").header(AUTHORIZATION, "jwt token"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name", is("chat room name")))
        .andExpect(jsonPath("$[0].owner", is("username")))
        .andExpect(jsonPath("$[1].name", is("chat room name 2")))
        .andExpect(jsonPath("$[1].owner", is("username2")));
  }

  @Test
  void can_store_room() throws Exception {

    StatusResponseDto responseDto = new StatusResponseDto("ok");
    Mockito.when(chatRoomService.store(Mockito.any(), Mockito.any())).thenReturn(responseDto);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/rooms")
                .header(AUTHORIZATION, "Jwt token")
                .content("{\"name\": \"new chat room\"}")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message", is("ok")));
  }

  @Test
  void can_delete_room() throws Exception {

    StatusResponseDto responseDto = new StatusResponseDto("ok");

    Mockito.when(chatRoomService.delete(Mockito.any(), Mockito.any())).thenReturn(responseDto);

    mockMvc
        .perform(
            MockMvcRequestBuilders.delete("/rooms/1")
                .header(AUTHORIZATION, "Jwt token")
                .content("{\"name\": \"new chat room\"}")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message", is("ok")));
  }

  @Test
  void can_get_one_room() throws Exception {

    ChatRoomResponseDto responseDto = rooms.get(0);

    Mockito.when(chatRoomService.getChatRoomById(Mockito.any())).thenReturn(responseDto);

    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/rooms/1")
                .header(AUTHORIZATION, "Jwt token")
                .content("{\"name\": \"new chat room\"}")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is("chat room name")))
        .andExpect(jsonPath("$.owner", is("username")));
  }

  @Test
  void store_with_empty_request_body_shows_error() throws Exception {

    Mockito.when(chatRoomService.store(Mockito.any(), Mockito.any()))
        .thenThrow(EmptyRequestBodyException.class);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/rooms")
                .header(AUTHORIZATION, "Jwt token")
                .content("")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error", is("Invalid request")));
  }

  @Test
  void store_with_empty_chat_room_name_shows_error() throws Exception {

    Mockito.when(chatRoomService.store(Mockito.any(), Mockito.any()))
        .thenThrow(ChatRoomNameIsMissingException.class);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/rooms")
                .header(AUTHORIZATION, "Jwt token")
                .content("{\"name\": \" \" }")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error", is("Chat room name is missing")));
  }

  @Test
  void store_with_already_existing_name_shows_error() throws Exception {

    Mockito.when(chatRoomService.store(Mockito.any(), Mockito.any()))
        .thenThrow(ChatRoomNameAlreadyTakenException.class);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/rooms")
                .header(AUTHORIZATION, "Jwt token")
                .content("{\"name\": \"room \" }")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error", is("Chat room with this name already exist")));
  }

  @Test
  void not_existing_room_shows_error() throws Exception {

    Mockito.when(chatRoomService.getChatRoomById(Mockito.any()))
        .thenThrow(ChatRoomNotExistException.class);

    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/rooms/1")
                .header(AUTHORIZATION, "Jwt token")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error", is("Chat room does not exist")));
  }
}
