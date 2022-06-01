package com.milanalbert.chatty.advice;

import com.milanalbert.chatty.dtos.ErrorResponseDto;
import com.milanalbert.chatty.exeptions.*;
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

  @ExceptionHandler(UnauthorizedToDeleteException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponseDto unauthorizedToDeleteException() {
    return new ErrorResponseDto(
        environment.getProperty("chatty.config.error.unauthorized.to.delete"));
  }

  @ExceptionHandler(ChatRoomNotExistException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponseDto chatRoomNotExistException() {
    return new ErrorResponseDto(environment.getProperty("chatty.config.error.chat.room.not.exist"));
  }

  @ExceptionHandler(InvalidIdException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponseDto invalidIdException() {
    return new ErrorResponseDto(environment.getProperty("chatty.config.error.invalid.id"));
  }

  @ExceptionHandler(ChatRoomNameIsMissingException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponseDto chatRoomNameIsMissingException() {
    return new ErrorResponseDto(
        environment.getProperty("chatty.config.error.chat.room.name.is.missing"));
  }

  @ExceptionHandler(ChatRoomNameAlreadyTakenException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponseDto chatRoomNameAlreadyTakenException() {
    return new ErrorResponseDto(
        environment.getProperty("chatty.config.error.chat.room.name.already.taken"));
  }

  @ExceptionHandler(MessageTestIsMissingException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponseDto messageTestIsMissingException() {
    return new ErrorResponseDto(
        environment.getProperty("chatty.config.error.message.text.is.missing"));
  }

  @ExceptionHandler(UnauthorizedToDeleteMessageException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponseDto unauthorizedToDeleteMessageException() {
    return new ErrorResponseDto(
        environment.getProperty("chatty.config.error.unauthorize.to.delete.message"));
  }
}
