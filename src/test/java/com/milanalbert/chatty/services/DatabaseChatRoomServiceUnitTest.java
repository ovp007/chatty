package com.milanalbert.chatty.services;

import com.milanalbert.chatty.dtos.ChatRoomResponseDto;
import com.milanalbert.chatty.dtos.CreateChatRoomRequestDto;
import com.milanalbert.chatty.dtos.CreateMessageRequestDto;
import com.milanalbert.chatty.dtos.StatusResponseDto;
import com.milanalbert.chatty.exeptions.ChatRoomNameIsMissingException;
import com.milanalbert.chatty.exeptions.EmptyRequestBodyException;
import com.milanalbert.chatty.exeptions.InvalidIdException;
import com.milanalbert.chatty.exeptions.UnauthorizedToDeleteException;
import com.milanalbert.chatty.models.AppUser;
import com.milanalbert.chatty.models.ChatRoom;
import com.milanalbert.chatty.repositories.ChatRoomRepository;
import com.milanalbert.chatty.repositories.MessageRepository;
import com.milanalbert.chatty.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseChatRoomServiceUnitTest {

  private final List<ChatRoom> rooms = new ArrayList<>();
  private ChatRoomRepository chatRoomRepository;
  private UserService userService;
  private JwtUtil jwtUtil;
  private ChatRoomService chatRoomService;

  @BeforeEach
  void setUp() {

    this.chatRoomRepository = Mockito.mock(ChatRoomRepository.class);
    this.userService = Mockito.mock(UserService.class);
    this.jwtUtil = Mockito.mock(JwtUtil.class);

    this.chatRoomService = new DatabaseChatRoomService(chatRoomRepository, userService, jwtUtil);

    AppUser appUser = new AppUser("username", "email", "password");
    ChatRoom chatRoom = new ChatRoom("chat room name", appUser);

    AppUser appUser2 = new AppUser("username2", "email2", "password");
    ChatRoom chatRoom2 = new ChatRoom("chat room name 2", appUser2);

    rooms.add(chatRoom);
    rooms.add(chatRoom2);
  }

  @Test
  void getAllChatRooms() {

    Mockito.when(chatRoomRepository.findAll()).thenReturn(rooms);

    List<ChatRoomResponseDto> result = chatRoomService.getAllChatRooms();

    assertEquals(2, result.size());
    assertEquals("chat room name", result.get(0).name);
    assertEquals("username", result.get(0).owner);
    assertEquals("chat room name 2", result.get(1).name);
    assertEquals("username2", result.get(1).owner);
  }

  @Test
  void getChatRoomById() {

    Mockito.when(chatRoomRepository.findById(Mockito.any())).thenReturn(Optional.of(rooms.get(0)));

    ChatRoomResponseDto result = chatRoomService.getChatRoomById(1L);

    assertEquals("chat room name", result.name);
    assertEquals("username", result.owner);
  }

  @Test
  void store() {
    CreateChatRoomRequestDto requestDto = new CreateChatRoomRequestDto("chat room name 3");

    Mockito.when(chatRoomRepository.existsByName(Mockito.any())).thenReturn(false);

    StatusResponseDto result = chatRoomService.store("jwt token", requestDto);

    assertEquals("ok", result.message);
  }

  @Test
  void can_delete_chat_room() {

    Mockito.when(chatRoomRepository.existsById(Mockito.any())).thenReturn(true);
    Mockito.when(chatRoomRepository.existsByIdAndOwnerUsername(Mockito.any(), Mockito.any()))
        .thenReturn(true);

    StatusResponseDto result = chatRoomService.delete("jwt token", 1L);

    assertEquals("ok", result.message);
  }

  @Test
  void store_with_empty_body_throws_exception() {

    assertThrows(EmptyRequestBodyException.class, () -> chatRoomService.store("jwt token", null));
  }

  @Test
  void store_with_missing_name_throws_exception() {

    assertThrows(
        ChatRoomNameIsMissingException.class,
        () -> chatRoomService.store("jwt token", new CreateChatRoomRequestDto(null)));
  }

  @Test
  void delete_with_invalid_id_throws_exception() {

    Mockito.when(chatRoomRepository.existsByIdAndOwnerUsername(Mockito.any(), Mockito.any()))
        .thenReturn(true);

    assertThrows(InvalidIdException.class, () -> chatRoomService.delete("jwt token", 1L));
  }

  @Test
  void delete_not_own_room_throws_exception() {

    Mockito.when(chatRoomRepository.existsById(Mockito.any())).thenReturn(true);

    assertThrows(UnauthorizedToDeleteException.class, () -> chatRoomService.delete("jwt token", 1L));
  }
}
