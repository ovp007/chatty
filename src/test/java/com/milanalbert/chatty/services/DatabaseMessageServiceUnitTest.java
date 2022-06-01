package com.milanalbert.chatty.services;

import com.milanalbert.chatty.dtos.*;
import com.milanalbert.chatty.exeptions.*;
import com.milanalbert.chatty.models.AppUser;
import com.milanalbert.chatty.models.ChatRoom;
import com.milanalbert.chatty.models.Message;
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

class DatabaseMessageServiceUnitTest {

    private MessageRepository messageRepository;
    private ChatRoomService roomService;
    private ChatRoomRepository chatRoomRepository;
    private UserService userService;
    private JwtUtil jwtUtil;
    private MessageService messageService;

    private List<Message> messages = new ArrayList<>();
    private AppUser appUser;

    @BeforeEach
    void setUp() {
        this.messageRepository = Mockito.mock(MessageRepository.class);
        this.roomService = Mockito.mock(ChatRoomService.class);
        this.chatRoomRepository = Mockito.mock(ChatRoomRepository.class);
        this.userService = Mockito.mock(UserService.class);
        this.jwtUtil = Mockito.mock(JwtUtil.class);

        this.messageService = new DatabaseMessageService(messageRepository,
                roomService,
                chatRoomRepository,
                userService,
                jwtUtil);

        appUser = new AppUser("username", "email", "password");
        ChatRoom chatRoom = new ChatRoom("chat room name", appUser);
        Message message = new Message("message text", appUser, chatRoom);
        Message message2 = new Message("message text2", appUser, chatRoom);

        messages.add(message);
        messages.add(message2);
    }

    @Test
    void can_get_all_messages() {

        Mockito.when(messageRepository.getMessageByChatRoomId(1L)).thenReturn(messages);

        List<MessageResponseDto> result = messageService.getAllMessages(1L);

        assertEquals(2, result.size());
        assertEquals("message text", result.get(0).text);
              assertEquals("message text2", result.get(1).text);

    }

    @Test
    void can_get_message_by_id() {

        Mockito.when(messageRepository.findById(1L)).thenReturn(Optional.of(messages.get(0)));

        MessageResponseDto result = messageService.getMessage(1L);

        assertEquals("message text", result.text);

    }

    @Test
    void can_store_message() {

        CreateMessageRequestDto requestDto = new CreateMessageRequestDto("message text 3");

        Mockito.when(chatRoomRepository.existsById(Mockito.any())).thenReturn(true);
        Mockito.when(userService.getCurrentUser(Mockito.any())).thenReturn(appUser);

        StatusResponseDto result = messageService.store(requestDto,"jwt token", 1L );

        assertEquals("ok", result.message);
    }

    @Test
    void can_delete() {

        Mockito.when(messageRepository.existsById(Mockito.any())).thenReturn(true);
        Mockito.when(messageRepository.existsByIdAndPostedByUsername(Mockito.any(), Mockito.any()))
                .thenReturn(true);

        StatusResponseDto result = messageService.delete("jwt token", 1L);

        assertEquals("ok", result.message);
    }

    @Test
    void store_with_empty_body_throws_exception() {

        assertThrows(EmptyRequestBodyException.class, () -> messageService.store(null,"jwt token", 1L));
    }

    @Test
    void store_with_missing_text_throws_exception() {

        Mockito.when(chatRoomRepository.existsById(Mockito.any())).thenReturn(true);
        Mockito.when(messageRepository.existsByIdAndPostedByUsername(Mockito.any(), Mockito.any()))
                .thenReturn(true);

        assertThrows(
                MessageTextIsMissingException.class,
                () -> messageService.store(new CreateMessageRequestDto(null),"jwt token", 1L));
    }

    @Test
    void delete_with_invalid_id_throws_exception() {

        Mockito.when(messageRepository.existsByIdAndPostedByUsername(Mockito.any(), Mockito.any()))
                .thenReturn(true);

        assertThrows(InvalidIdException.class, () -> messageService.delete("jwt token", 1L));
    }

    @Test
    void delete_not_own_room_throws_exception() {

        Mockito.when(messageRepository.existsById(Mockito.any())).thenReturn(true);

        assertThrows(UnauthorizedToDeleteMessageException.class, () -> messageService.delete("jwt token", 1L));
    }
}