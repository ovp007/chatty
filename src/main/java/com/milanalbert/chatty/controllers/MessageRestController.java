package com.milanalbert.chatty.controllers;

import com.milanalbert.chatty.dtos.CreateMessageRequestDto;
import com.milanalbert.chatty.dtos.MessageResponseDto;
import com.milanalbert.chatty.dtos.ResponseDto;
import com.milanalbert.chatty.dtos.StatusResponseDto;
import com.milanalbert.chatty.services.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/rooms/")
public class MessageRestController {

  private final MessageService messageService;

  public MessageRestController(MessageService messageService) {
    this.messageService = messageService;
  }

  @GetMapping("{id}/messages")
  public ResponseEntity<List<MessageResponseDto>> index(@PathVariable Long id) {

    List<MessageResponseDto> messages = messageService.getAllMessages(id);

    return new ResponseEntity<>(messages, HttpStatus.OK);
  }

  @PostMapping("{id}/messages")
  public ResponseEntity<? extends ResponseDto> store(
      @PathVariable Long id,
      @RequestHeader(AUTHORIZATION) String token,
      @RequestBody CreateMessageRequestDto requestDto) {

    StatusResponseDto statusResponseDto = messageService.store(requestDto, token, id);

    return new ResponseEntity<>(statusResponseDto, HttpStatus.OK);
  }

  @DeleteMapping("messages/{id}")
  public ResponseEntity<? extends ResponseDto> delete(
      @PathVariable Long id, @RequestHeader(AUTHORIZATION) String token) {

    StatusResponseDto statusResponseDto = messageService.delete(token, id);

    return new ResponseEntity<>(statusResponseDto, HttpStatus.OK);
  }
}
