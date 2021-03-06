package com.milanalbert.chatty.repositories;

import com.milanalbert.chatty.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  AppUser getAppUserByUsername(String username);

  List<AppUser> findAllByIsLogInIsTrue();
}
