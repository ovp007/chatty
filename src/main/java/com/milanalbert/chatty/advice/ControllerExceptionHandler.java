package com.milanalbert.chatty.advice;

import com.milanalbert.chatty.dtos.ErrorResponseDto;
import com.milanalbert.chatty.exeptions.EmptyRequestBodyException;
import com.milanalbert.chatty.exeptions.InvalidLoginInformationException;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

  private final Environment environment;

  public ControllerExceptionHandler(Environment environment) {
    this.environment = environment;
  }

  @ExceptionHandler(EmptyRequestBodyException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponseDto emptyRequestBodyException() {
    return new ErrorResponseDto(environment.getProperty("chatty.config.error.empty.request"));
  }

  @ExceptionHandler(InvalidLoginInformationException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponseDto invalidLoginInformationException() {
    return new ErrorResponseDto(
        environment.getProperty("chatty.config.error.invalid.login.request"));
  }
}
