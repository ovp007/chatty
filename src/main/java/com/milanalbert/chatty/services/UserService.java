package com.milanalbert.chatty.services;

import com.milanalbert.chatty.dtos.LoginRequestDto;
import com.milanalbert.chatty.dtos.RegisterRequestDto;
import com.milanalbert.chatty.dtos.StatusResponseDto;
import com.milanalbert.chatty.dtos.TokenResponseDto;
import com.milanalbert.chatty.models.AppUser;

public interface UserService {

  StatusResponseDto store(RegisterRequestDto registerRequestDto);

  TokenResponseDto login(LoginRequestDto loginRequestDto);

  AppUser getCurrentUser(String token);
}
