package com.milanalbert.chatty.controllers;

import com.milanalbert.chatty.dtos.ChatRoomResponseDto;
import com.milanalbert.chatty.dtos.CreateChatRoomRequestDto;
import com.milanalbert.chatty.dtos.ResponseDto;
import com.milanalbert.chatty.dtos.StatusResponseDto;
import com.milanalbert.chatty.services.ChatRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequestMapping("/rooms")
@RestController
public class ChatRoomRestController {

  ChatRoomService chatRoomService;

  public ChatRoomRestController(ChatRoomService chatRoomService) {
    this.chatRoomService = chatRoomService;
  }

  @GetMapping("")
  public ResponseEntity<List<ChatRoomResponseDto>> getAllRooms() {

    List<ChatRoomResponseDto> rooms = chatRoomService.getAllChatRooms();

    return new ResponseEntity<>(rooms, HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<? extends ResponseDto> store(
      @RequestHeader(AUTHORIZATION) String token,
      @RequestBody CreateChatRoomRequestDto requestDto) {

    StatusResponseDto statusResponseDto = chatRoomService.store(token, requestDto);

    return new ResponseEntity<>(statusResponseDto, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<? extends ResponseDto> delete(
      @RequestHeader(AUTHORIZATION) String token, @PathVariable Long id) {

    StatusResponseDto statusResponseDto = chatRoomService.delete(token, id);
    return new ResponseEntity<>(statusResponseDto, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<? extends ResponseDto> show(@PathVariable Long id) {

    ChatRoomResponseDto responseDto = chatRoomService.getChatRoomById(id);
    return new ResponseEntity<>(responseDto, HttpStatus.OK);
  }
}
