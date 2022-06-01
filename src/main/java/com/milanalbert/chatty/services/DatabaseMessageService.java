package com.milanalbert.chatty.services;

import com.milanalbert.chatty.dtos.CreateMessageRequestDto;
import com.milanalbert.chatty.dtos.MessageResponseDto;
import com.milanalbert.chatty.dtos.StatusResponseDto;
import com.milanalbert.chatty.exeptions.EmptyRequestBodyException;
import com.milanalbert.chatty.exeptions.InvalidIdException;
import com.milanalbert.chatty.exeptions.MessageTestIsMissingException;
import com.milanalbert.chatty.exeptions.UnauthorizedToDeleteMessageException;
import com.milanalbert.chatty.models.AppUser;
import com.milanalbert.chatty.models.ChatRoom;
import com.milanalbert.chatty.models.Message;
import com.milanalbert.chatty.repositories.ChatRoomRepository;
import com.milanalbert.chatty.repositories.MessageRepository;
import com.milanalbert.chatty.utils.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DatabaseMessageService implements MessageService {

  private final MessageRepository messageRepository;
  private final ChatRoomService roomService;
  private final ChatRoomRepository chatRoomRepository;
  private final UserService userService;
  private final JwtUtil jwtUtil;

  public DatabaseMessageService(
      MessageRepository messageRepository,
      ChatRoomService roomService,
      ChatRoomRepository chatRoomRepository,
      UserService userService,
      JwtUtil jwtUtil) {
    this.messageRepository = messageRepository;
    this.roomService = roomService;
    this.chatRoomRepository = chatRoomRepository;
    this.userService = userService;
    this.jwtUtil = jwtUtil;
  }

  @Override
  public List<MessageResponseDto> getAllMessages(Long id) {
    List<Message> messages = messageRepository.getMessageByChatRoomId(id);
    return messages.stream().map(MessageResponseDto::new).collect(Collectors.toList());
  }

  @Override
  public MessageResponseDto getMessage(Long id) {
    Optional<Message> messageOptional = messageRepository.findById(id);

    if (messageOptional.isEmpty()) throw new InvalidIdException();
    return new MessageResponseDto(messageOptional.get());
  }

  @Override
  public StatusResponseDto store(CreateMessageRequestDto requestDto, String token, Long id) {

    if (requestDto == null) throw new EmptyRequestBodyException();

    if (!chatRoomRepository.existsById(id)) throw new InvalidIdException();

    if (requestDto.text == null || requestDto.text.isEmpty())
      throw new MessageTestIsMissingException();

    AppUser user = userService.getCurrentUser(token);
    System.out.println(user.getId());
    ChatRoom chatRoom = chatRoomRepository.getById(id);

    Message message = new Message(requestDto.text, user, chatRoom);

    messageRepository.save(message);

    return new StatusResponseDto("ok");
  }

  @Override
  public StatusResponseDto delete(String token, Long id) {

    String username = jwtUtil.extractUsername(token.substring(7));
    if (!messageRepository.existsById(id)) throw new InvalidIdException();
    if (!messageRepository.existsByIdAndPostedByUsername(id, username))
      throw new UnauthorizedToDeleteMessageException();

    Message message = messageRepository.getById(id);

    messageRepository.delete(message);

    return new StatusResponseDto("ok");
  }
}
