package com.milanalbert.chatty.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.milanalbert.chatty.dtos.ErrorResponseDto;
import com.milanalbert.chatty.utils.JwtUtil;
import io.jsonwebtoken.SignatureException;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final Environment environment;

  public JwtRequestFilter(JwtUtil jwtUtil, Environment environment) {
    this.jwtUtil = jwtUtil;
    this.environment = environment;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    String authorizationHeader = request.getHeader(AUTHORIZATION);
    String username = null;
    String jwt;

    try {
      if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
        jwt = authorizationHeader.substring(7);
        username = jwtUtil.extractUsername(jwt);
      }

      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
        usernamePasswordAuthenticationToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      }
      chain.doFilter(request, response);
    } catch (SignatureException e) {

      ErrorResponseDto error = new ErrorResponseDto(environment.getProperty("chatty.config.error.invalid.token"));
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType(APPLICATION_JSON_VALUE);
      new ObjectMapper().writeValue(response.getOutputStream(), error);
    }
  }
}
