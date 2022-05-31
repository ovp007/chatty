package com.milanalbert.chatty.models;

import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "app_users")
public class AppUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NonNull
  @Column(unique = true)
  private String username;

  @NonNull
  @Column(unique = true)
  private String email;

  @NonNull private String password;

  private Boolean logIn;

  public AppUser() {}

  public AppUser(@NonNull String username, @NonNull String email, @NonNull String password) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.logIn = false;
  }

  public Long getId() {
    return id;
  }

  @NonNull
  public String getUsername() {
    return username;
  }

  @NonNull
  public String getEmail() {
    return email;
  }

  @NonNull
  public String getPassword() {
    return password;
  }

  public Boolean getLogIn() {
    return logIn;
  }

  public void setLogIn(Boolean logIn) {
    this.logIn = logIn;
  }
}
