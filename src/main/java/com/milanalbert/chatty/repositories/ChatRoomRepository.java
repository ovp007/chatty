package com.milanalbert.chatty.repositories;

import com.milanalbert.chatty.models.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

  Boolean existsByIdAndOwnerUsername(Long id, String username);

  Boolean existsByName(String name);
}
