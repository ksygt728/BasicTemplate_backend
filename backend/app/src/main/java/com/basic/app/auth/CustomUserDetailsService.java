package com.basic.app.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.basic.app.entity.User;
import com.basic.app.repository.UserRepository;
import com.basic.app.util.Status;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.info("[TRY LOGIN] --- [CLASS] CustomUserDetailsService --- [METHOD] loadUserByUsername ---");

    User user = userRepository.findById(username)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> {
          throw new UsernameNotFoundException("");
        });

    return new CustomUserDetails(user);
  }

}
