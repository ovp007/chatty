package com.milanalbert.chatty.controllers;

import com.milanalbert.chatty.dtos.*;
import com.milanalbert.chatty.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.GeneratedValue;

@RestController
public class RegisterRestController {

  private final UserService userService;

  public RegisterRestController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<? extends ResponseDto> register(
      @RequestBody(required = false) RegisterRequestDto registerRequestDto) {

    StatusResponseDto statusResponseDto = userService.store(registerRequestDto);

    return new ResponseEntity<>(statusResponseDto, HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<? extends ResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
    TokenResponseDto tokenResponseDto = userService.login(loginRequestDto);

    return new ResponseEntity<>(tokenResponseDto, HttpStatus.OK);
  }

}
