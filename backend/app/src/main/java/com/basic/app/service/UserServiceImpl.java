package com.basic.app.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basic.app.dto.requestDto.UserReqDto;
import com.basic.app.dto.requestDto.specialDto.AuthReqDto;
import com.basic.app.dto.responseDto.UserResDto;
import com.basic.app.entity.Department;
import com.basic.app.entity.User;
import com.basic.app.exception.ErrorCode;
import com.basic.app.exception.customException.BusinessException;
import com.basic.app.exception.customException.NotFoundException;
import com.basic.app.jwt.JwtProperties;
import com.basic.app.jwt.JwtProvider;
import com.basic.app.repository.DepartmentRepository;
import com.basic.app.repository.UserRepository;
import com.basic.app.service.interfaces.UserService;
import com.basic.app.util.Status;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Transactional
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private PasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtProvider jwtProvider;

  @Autowired
  private JwtProperties jwtProperties;

  @Autowired
  private StringRedisTemplate redisTemplate;

  @Autowired
  UserRepository userRepository;

  @Autowired
  DepartmentRepository departmentRepository;

  @Override
  public Map<String, Object> findAllUserForAdmin() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAllUserForAdmin'");
  }

  @Override
  public Map<String, Object> findByUserForAdmin(String userId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByUserForAdmin'");
  }

  @Override
  public Map<String, Object> updateUserForAdmin(UserReqDto user) {

    // Email, ID는 수정 불가

    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateUserForAdmin'");
  }

  @Override
  public Map<String, Object> deleteUserForAdmin(String userId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteUserForAdmin'");
  }

}
