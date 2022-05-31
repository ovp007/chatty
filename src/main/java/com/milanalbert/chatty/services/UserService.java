package com.milanalbert.chatty.services;

import com.milanalbert.chatty.dtos.RegisterRequestDto;
import com.milanalbert.chatty.dtos.StatusResponseDto;

public interface UserService {

  StatusResponseDto store(RegisterRequestDto registerRequestDto);
}
