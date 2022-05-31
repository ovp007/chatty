package com.milanalbert.chatty.services;

import com.milanalbert.chatty.dtos.LoginRequestDto;
import com.milanalbert.chatty.dtos.RegisterRequestDto;
import com.milanalbert.chatty.dtos.StatusResponseDto;
import com.milanalbert.chatty.dtos.TokenResponseDto;

public interface UserService {

  StatusResponseDto store(RegisterRequestDto registerRequestDto);

  TokenResponseDto login(LoginRequestDto loginRequestDto);
}
