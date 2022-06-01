package com.milanalbert.chatty.controllers;

import com.milanalbert.chatty.dtos.AppUserResponseDto;
import com.milanalbert.chatty.dtos.ResponseDto;
import com.milanalbert.chatty.dtos.StatusResponseDto;
import com.milanalbert.chatty.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
public class UserRestController {

  UserService userService;

  public UserRestController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/users/active")
  public ResponseEntity<List<AppUserResponseDto>> getActiveUsers() {

    List<AppUserResponseDto> users = userService.getActiveUsers();

    return new ResponseEntity<>(users, HttpStatus.OK);
  }

  @PostMapping("/users/logout")
  public ResponseEntity<? extends ResponseDto> logout(@RequestHeader(AUTHORIZATION) String token) {
    StatusResponseDto responseDto = userService.logout(token);

    return new ResponseEntity<>(responseDto, HttpStatus.OK);
  }
}
