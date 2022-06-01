package com.milanalbert.chatty.services;

import com.milanalbert.chatty.dtos.*;
import com.milanalbert.chatty.exeptions.*;
import com.milanalbert.chatty.models.AppUser;
import com.milanalbert.chatty.models.ChatRoom;
import com.milanalbert.chatty.models.Message;
import com.milanalbert.chatty.repositories.UserRepository;
import com.milanalbert.chatty.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseUserServiceUnitTest {

  private UserRepository userRepository;
  private AuthenticationManager authenticationManager;
  private PasswordEncoder passwordEncoder;
  private JwtUtil jwtUtil;
  private UserService userService;

  @BeforeEach
  void setUp() {
    this.userRepository = Mockito.mock(UserRepository.class);
    this.authenticationManager = Mockito.mock(AuthenticationManager.class);
    this.passwordEncoder = Mockito.mock(PasswordEncoder.class);
    this.jwtUtil = Mockito.mock(JwtUtil.class);

    this.userService =
        new DatabaseUserService(userRepository, authenticationManager, passwordEncoder, jwtUtil);
  }

  @Test
  void can_store_user() {

    RegisterRequestDto requestDto = new RegisterRequestDto("username", "ëmail", "password");
    Mockito.when(userRepository.existsByUsername(Mockito.any())).thenReturn(false);
    Mockito.when(userRepository.existsByEmail(Mockito.any())).thenReturn(false);

    StatusResponseDto responseDto = userService.store(requestDto);

    assertEquals("ok", responseDto.message);
  }

  @Test
  void can_login() {

    AppUser appUser = new AppUser("username", "email", "password");

    LoginRequestDto loginRequestDto = new LoginRequestDto("username", "password");

    User user = new User("username", "password", true, true, true, true, new ArrayList<>());

    Authentication authentication =
        new UsernamePasswordAuthenticationToken(user, new Object(), new ArrayList<>());

    Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);
    Mockito.when(userRepository.getAppUserByUsername(Mockito.any())).thenReturn(appUser);

    Mockito.when(jwtUtil.generateToken(Mockito.any())).thenReturn("jwt token");

    TokenResponseDto tokenResponseDto = userService.login(loginRequestDto);

    assertEquals("jwt token", tokenResponseDto.token);
  }

  @Test
  void getCurrentUser() {

    AppUser appUser = new AppUser("username", "email", "password");

    Mockito.when(userRepository.getAppUserByUsername(Mockito.any())).thenReturn(appUser);

   AppUser result = userService.getCurrentUser("jwt token");

    assertEquals("username", result.getUsername());

  }

  @Test
  void getActiveUsers() {
    AppUser appUser = new AppUser("username", "email", "password");
    AppUser appUser2 = new AppUser("username2", "email2", "password");
    appUser.setLogIn(true);
    appUser2.setLogIn(true);

    List<AppUser> users = new ArrayList<>();
    users.add(appUser);
    users.add(appUser2);

    Mockito.when(userRepository.findAllByIsLogInIsTrue()).thenReturn(users);

    List<AppUserResponseDto> appUsers = userService.getActiveUsers();

    assertEquals("username", appUsers.get(0).username);
    assertEquals("email", appUsers.get(0).email);
    assertEquals("username2", appUsers.get(1).username);
    assertEquals("email2", appUsers.get(1).email);


  }

}
