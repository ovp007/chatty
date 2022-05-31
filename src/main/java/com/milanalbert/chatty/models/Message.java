package com.milanalbert.chatty.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String text;

  @ManyToOne private AppUser postedBy;

  private Boolean isDeleted;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @ManyToOne private ChatRoom chatRoom;

  public Message() {}

  public Message(String text, AppUser appUser, ChatRoom chatRoom) {
    this.text = text;
    this.isDeleted = false;
    this.chatRoom = chatRoom;
    this.createdAt = LocalDateTime.now();
  }

  public Long getId() {
    return id;
  }

  public String getText() {
    return text;
  }

  public AppUser getPostedBy() {
    return postedBy;
  }

  public Boolean getDeleted() {
    return isDeleted;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getDeletedAt() {
    return deletedAt;
  }

  public ChatRoom getChatRoom() {
    return chatRoom;
  }
}
