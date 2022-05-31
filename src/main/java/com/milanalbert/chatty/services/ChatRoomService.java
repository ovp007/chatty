package com.milanalbert.chatty.services;

import com.milanalbert.chatty.dtos.ChatRoomResponseDto;
import com.milanalbert.chatty.dtos.CreateChatRoomRequestDto;
import com.milanalbert.chatty.dtos.StatusResponseDto;

import java.util.List;

public interface ChatRoomService {

  List<ChatRoomResponseDto> getAllChatRooms();

  ChatRoomResponseDto getChatRoomById(Long id);

  StatusResponseDto store(String token, CreateChatRoomRequestDto requestDto);

  StatusResponseDto delete(String token, Long id);
}
