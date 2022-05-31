package com.milanalbert.chatty.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.milanalbert.chatty.dtos.ErrorResponseDto;
import org.springframework.core.env.Environment;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final Environment environment;

  public RestAuthenticationEntryPoint(Environment environment) {
    this.environment = environment;
  }

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException, ServletException {

    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    ErrorResponseDto error = new ErrorResponseDto(environment.getProperty("chatty.config.error.invalid.login.request"));
    response.setContentType(APPLICATION_JSON_VALUE);
    new ObjectMapper().writeValue(response.getOutputStream(), error);
  }
}
