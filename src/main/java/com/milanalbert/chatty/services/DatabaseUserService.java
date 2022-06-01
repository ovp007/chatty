package com.milanalbert.chatty.services;

import com.milanalbert.chatty.dtos.*;
import com.milanalbert.chatty.exeptions.*;
import com.milanalbert.chatty.models.AppUser;
import com.milanalbert.chatty.repositories.UserRepository;
import com.milanalbert.chatty.utils.JwtUtil;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Service
public class DatabaseUserService implements UserService {

  private final UserRepository userRepository;
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  public DatabaseUserService(
      UserRepository userRepository,
      AuthenticationManager authenticationManager,
      PasswordEncoder passwordEncoder,
      JwtUtil jwtUtil) {
    this.userRepository = userRepository;
    this.authenticationManager = authenticationManager;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
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
            registerRequestDto.username,
            registerRequestDto.email,
            passwordEncoder.encode(registerRequestDto.password)));

    return new StatusResponseDto("ok");
  }

  @Override
  public TokenResponseDto login(LoginRequestDto loginRequestDto) {

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequestDto.username, loginRequestDto.password));

    User user = (User) authentication.getPrincipal();

    final String jwtToken = jwtUtil.generateToken(user);

    AppUser appUser = userRepository.getAppUserByUsername(user.getUsername());
    appUser.setLogIn(true);
    userRepository.save(appUser);

    return new TokenResponseDto(jwtToken);
  }

  @Override
  public AppUser getCurrentUser(String token) {
    return userRepository.getAppUserByUsername(jwtUtil.extractUsername(token.substring(7)));
  }

  @Override
  public List<AppUserResponseDto> getActiveUsers() {
    List<AppUser> users = userRepository.findAllByIsLogInIsTrue();

    return users.stream().map(AppUserResponseDto::new).collect(Collectors.toList());
  }

  @Override
  public StatusResponseDto logout(String token) {

    AppUser appUser = getCurrentUser(token);
    appUser.setLogIn(false);
    userRepository.save(appUser);
    return new StatusResponseDto("ok");
  }
}
