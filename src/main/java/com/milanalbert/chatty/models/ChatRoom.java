package com.milanalbert.chatty.models;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_rooms")
public class ChatRoom {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NonNull
  @Column(unique = true)
  private String name;

  @NonNull @ManyToOne private AppUser owner;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  public ChatRoom() {}

  public ChatRoom(@NonNull String name, @NonNull AppUser owner) {
    this.name = name;
    this.owner = owner;
    this.createdAt = LocalDateTime.now();
  }

  public Long getId() {
    return id;
  }

  @NonNull
  public String getName() {
    return name;
  }

  @NonNull
  public AppUser getOwner() {
    return owner;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
}
