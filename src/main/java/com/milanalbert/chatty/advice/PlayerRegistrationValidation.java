package com.milanalbert.chatty.advice;

import com.milanalbert.chatty.dtos.ErrorResponseDto;
import com.milanalbert.chatty.exeptions.*;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PlayerRegistrationValidation {

  private final Environment environment;

  public PlayerRegistrationValidation(Environment environment) {
    this.environment = environment;
  }

  @ExceptionHandler(UsernameIsRequiredException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponseDto usernameIsrequired() {
    return new ErrorResponseDto(environment.getProperty("chatty.config.error.username.is.empty"));
  }

  @ExceptionHandler(EmailIsRequiredException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponseDto emailIsRequiredException() {
    return new ErrorResponseDto(environment.getProperty("chatty.config.error.email.is.empty"));
  }

  @ExceptionHandler(PasswordIsRequiredException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponseDto passwordIsRequiredException() {
    return new ErrorResponseDto(environment.getProperty("chatty.config.error.password.is.empty"));
  }

  @ExceptionHandler(UsernameAlreadyExistException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponseDto usernameAlreadyExistException() {
    return new ErrorResponseDto(
        environment.getProperty("chatty.config.error.username.already.exist"));
  }

  @ExceptionHandler(EmailAlreadyExistException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponseDto emailAlreadyExistException() {
    return new ErrorResponseDto(environment.getProperty("chatty.config.error.email.already.exist"));
  }
}
