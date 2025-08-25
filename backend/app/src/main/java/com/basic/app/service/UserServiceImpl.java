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
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateUserForAdmin'");
  }

  @Override
  public Map<String, Object> deleteUserForAdmin(String userId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteUserForAdmin'");
  }

  @Override
  public Map<String, Object> signUp(UserReqDto userReqDto) {

    Map<String, Object> data = new HashMap<>();
    String deptCode = userReqDto.getDeptCode();

    // 1. DTO -> Entity 변환
    User userEntity = userReqDto.toEntity(User.class);

    // 2. ID로 기존 엔티티 조회
    userRepository.findById(userEntity.getUserId())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .ifPresent(entity -> {
          throw new BusinessException(ErrorCode.OBJECT_IS_EXISTED);
        });

    // 3. 데이터 저장
    Department department = departmentRepository.findById(deptCode)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new BusinessException(ErrorCode.OBJECT_NOT_FOUND));

    userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword())); // 비밀번호 암호화
    userEntity.setRole("ROLE_GUEST"); // 기본 역할 설정
    userEntity.setDepartment(department); // 기본 부서 설정
    User savedUserEntity = userRepository.save(userEntity);

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", savedUserEntity.toDto(UserResDto.class));
    return data;
  }

  @Override
  public Map<String, Object> signIn(AuthReqDto user) {
    Map<String, Object> data = new HashMap<>();

    String userId = user.getUserId();
    String password = user.getPassword();

    // 1. 인증토큰 생성
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userId, password);

    // 2. 로그인 시도
    /*
     * - CustomUserDetailsService loadUserbyUsername()이 실행
     * authentication이 정상적으로 return 되면 DB에 있는 ID와 password가 일치한 것 = 인증완료
     * 인증 성공 후 자동으로 SecurityContext에 인증객체 저장
     */
    Authentication authentication;
    authentication = authenticationManager.authenticate(token);

    // 3. 인증 성공 후 SecurityContext에 인증객체 저장
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // 4. 로그인 성공한 유저정보 가져오기
    User authenticatedUser = userRepository.findById(authentication.getName())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new BusinessException(ErrorCode.OBJECT_NOT_FOUND));

    // 5. JWT 토큰 생성
    String accessTokenHeader = jwtProperties.getAccessTokenHeader();
    String refreshTokenHeader = jwtProperties.getRefreshTokenHeader();

    String accessToken = jwtProvider.createAccessToken(authenticatedUser);
    String refreshToken = jwtProvider.createRefreshToken(authenticatedUser);

    // 6. Redis에 Refresh Token 저장
    redisTemplate.opsForValue().set(
        refreshTokenHeader + ":" + authenticatedUser.getUserId(), // Redis Key
        refreshToken, // Redis Value
        jwtProperties.getExpireTime().getRefreshToken().toMillis(), TimeUnit.MILLISECONDS) // 만료시간 설정(자동삭제)
    ;

    // 7. 로그인 성공한 유저정보를 Map에 담아 반환
    data.put(accessTokenHeader, accessToken);
    data.put(refreshTokenHeader, refreshToken);

    return data;

  }

}
