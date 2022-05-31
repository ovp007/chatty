package com.milanalbert.chatty.services;

import com.milanalbert.chatty.dtos.ChatRoomResponseDto;
import com.milanalbert.chatty.dtos.CreateChatRoomRequestDto;
import com.milanalbert.chatty.dtos.StatusResponseDto;
import com.milanalbert.chatty.exeptions.*;
import com.milanalbert.chatty.models.ChatRoom;
import com.milanalbert.chatty.repositories.ChatRoomRepository;
import com.milanalbert.chatty.utils.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DatabaseChatRoomService implements ChatRoomService {

  private final ChatRoomRepository chatRoomRepository;
  private final UserService userService;
  private final JwtUtil jwtUtil;

  public DatabaseChatRoomService(
      ChatRoomRepository chatRoomRepository, UserService userService, JwtUtil jwtUtil) {
    this.chatRoomRepository = chatRoomRepository;
    this.userService = userService;
    this.jwtUtil = jwtUtil;
  }

  @Override
  public List<ChatRoomResponseDto> getAllChatRooms() {

    List<ChatRoom> chatRooms = chatRoomRepository.findAll();

    return chatRooms.stream().map(ChatRoomResponseDto::new).collect(Collectors.toList());
  }

  @Override
  public ChatRoomResponseDto getChatRoomById(Long id) {

    Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findById(id);

    if (chatRoomOptional.isEmpty()) throw new ChatRoomNotExistException();
    return new ChatRoomResponseDto(chatRoomOptional.get());
  }

  @Override
  public StatusResponseDto store(String token, CreateChatRoomRequestDto requestDto) {

    if (requestDto == null) throw new EmptyRequestBodyException();

    if (requestDto.name == null || requestDto.name.isEmpty())
      throw new ChatRoomNameIsMissingException();

    if (chatRoomRepository.existsByName(requestDto.name))
      throw new ChatRoomNameAlreadyTakenException();

    ChatRoom chatRoom = new ChatRoom(requestDto.name, userService.getCurrentUser(token));

    chatRoomRepository.save(chatRoom);
    return new StatusResponseDto("ok");
  }

  @Override
  public StatusResponseDto delete(String token, Long id) {

    String username = jwtUtil.extractUsername(token.substring(7));
    if (!chatRoomRepository.existsById(id)) throw new InvalidIdException();
    if (!chatRoomRepository.existsByIdAndOwnerUsername(id, username))
      throw new UnauthorizedToDeleteException();

    return new StatusResponseDto("ok");
  }
}
