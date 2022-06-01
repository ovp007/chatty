package com.milanalbert.chatty.repositories;

import com.milanalbert.chatty.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

  List<Message> getMessageByChatRoomId(Long id);

  Boolean existsByIdAndPostedByUsername(Long id, String username);
}
