package com.milanalbert.chatty.services;

import com.milanalbert.chatty.dtos.*;
import com.milanalbert.chatty.models.AppUser;

import java.util.List;

public interface UserService {

  StatusResponseDto store(RegisterRequestDto registerRequestDto);

  TokenResponseDto login(LoginRequestDto loginRequestDto);

  AppUser getCurrentUser(String token);

  List<AppUserResponseDto> getActiveUsers();

  StatusResponseDto logout(String token);
}
