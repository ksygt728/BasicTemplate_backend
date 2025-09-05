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

/**
 * @파일명 : CustomUserDetailsService.java
 * @설명 : Spring Security UserDetailsService 구현 클래스
 * @작성자 : 김승연
 * @작성일 : 2025.07.31
 * @변경이력 :
 *       2025.07.31 김승연 최초 생성
 */
@Log4j2
@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  /**
   * @기능 : 사용자명으로 사용자 정보를 로드
   * @param username 사용자명(사용자 ID)
   * @return UserDetails 객체
   * @throws UsernameNotFoundException 사용자를 찾을 수 없는 경우
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.info("[TRY LOGIN] --- [CLASS] CustomUserDetailsService --- [METHOD] loadUserByUsername ---");

    User user = userRepository.findById(username)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE) && entity.getUserType().equals("CBMS"))
        .orElseThrow(() -> {
          throw new UsernameNotFoundException("");
        });

    return new CustomUserDetails(user);
  }

}
