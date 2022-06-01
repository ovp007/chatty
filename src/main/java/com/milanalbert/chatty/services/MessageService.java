package com.milanalbert.chatty.services;

import com.milanalbert.chatty.dtos.CreateMessageRequestDto;
import com.milanalbert.chatty.dtos.MessageResponseDto;
import com.milanalbert.chatty.dtos.StatusResponseDto;

import java.util.List;

public interface MessageService {

  List<MessageResponseDto> getAllMessages(Long id);

  MessageResponseDto getMessage(Long id);

  StatusResponseDto store(CreateMessageRequestDto requestDto, String token, Long id);

  StatusResponseDto delete(String token, Long id);
}
