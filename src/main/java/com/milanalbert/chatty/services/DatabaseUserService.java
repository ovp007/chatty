package com.milanalbert.chatty.services;

import com.milanalbert.chatty.dtos.RegisterRequestDto;
import com.milanalbert.chatty.dtos.StatusResponseDto;
import com.milanalbert.chatty.exeptions.*;
import com.milanalbert.chatty.models.AppUser;
import com.milanalbert.chatty.repositories.UserRepository;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Service
public class DatabaseUserService implements UserService {

  private final UserRepository userRepository;

  public DatabaseUserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public StatusResponseDto store(RegisterRequestDto registerRequestDto) {

    if (registerRequestDto == null) throw new EmptyRequestBodyException();

    if (registerRequestDto.username == null || registerRequestDto.username.isEmpty())
      throw new UsernameIsRequiredException();

    if (registerRequestDto.email == null || registerRequestDto.email.isEmpty())
      throw new EmailIsRequiredException();

    if (registerRequestDto.password == null || registerRequestDto.password.isEmpty())
      throw new PasswordIsRequiredException();

    if (userRepository.existsByUsername(registerRequestDto.username))
      throw new UsernameAlreadyExistException();

    if (userRepository.existsByEmail(registerRequestDto.email.toLowerCase()))
      throw new EmailAlreadyExistException();

    userRepository.save(
        new AppUser(
            registerRequestDto.username, registerRequestDto.email, registerRequestDto.password));

    return new StatusResponseDto("ok");
  }
}
