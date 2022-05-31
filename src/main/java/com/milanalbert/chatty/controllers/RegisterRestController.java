package com.milanalbert.chatty.controllers;

import com.milanalbert.chatty.dtos.RegisterRequestDto;
import com.milanalbert.chatty.dtos.ResponseDto;
import com.milanalbert.chatty.dtos.StatusResponseDto;
import com.milanalbert.chatty.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterRestController {

  private final UserService userService;

  public RegisterRestController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<? extends ResponseDto> register(
      @RequestBody RegisterRequestDto registerRequestDto) {

    StatusResponseDto statusResponseDto = userService.store(registerRequestDto);

    return new ResponseEntity<>(statusResponseDto, HttpStatus.OK);
  }
}
