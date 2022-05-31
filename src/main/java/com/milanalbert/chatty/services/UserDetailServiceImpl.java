package com.milanalbert.chatty.services;

import com.milanalbert.chatty.models.AppUser;
import com.milanalbert.chatty.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.getAppUserByUsername(username);
        if (user == null) throw new UsernameNotFoundException("Username not found");
        return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}
