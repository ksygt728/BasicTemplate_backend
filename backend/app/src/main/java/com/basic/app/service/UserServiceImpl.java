package com.basic.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basic.app.api.ModelMapperUtils;
import com.basic.app.api.PageResponse;
import com.basic.app.dto.requestDto.UserReqDto;
import com.basic.app.dto.responseDto.UserResDto;
import com.basic.app.entity.RoleUser;
import com.basic.app.entity.User;
import com.basic.app.exception.ErrorCode;
import com.basic.app.exception.customException.BusinessException;
import com.basic.app.exception.customException.NotFoundException;
import com.basic.app.jwt.JwtProperties;
import com.basic.app.jwt.JwtProvider;
import com.basic.app.repository.DepartmentRepository;
import com.basic.app.repository.RoleUserRepository;
import com.basic.app.repository.UserRepository;
import com.basic.app.repository.jooqRepository.UserJooqRepository;
import com.basic.app.service.interfaces.UserService;
import com.basic.app.util.Status;

import lombok.extern.log4j.Log4j2;

/**
 * @파일명 : UserServiceImpl.java
 * @설명 : 사용자 관련 서비스 구현체 (사용자 관리)
 * @작성자 : 김승연
 * @작성일 : 2025.09.05
 * @변경이력 :
 *       2025.09.05 김승연 최초 생성
 */
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
  UserJooqRepository userJooqRepository;

  @Autowired
  DepartmentRepository departmentRepository;

  @Autowired
  private RoleUserRepository roleUserRepository;

  /**
   * @기능 : 관리자용 사용자 전체 목록 조회 (페이징)
   * @param userReqDto 사용자 검색 조건 DTO
   * @param pageable   페이징 정보
   * @return 사용자 목록 정보가 담긴 Map
   */
  @Override
  public Map<String, Object> findAllUserForAdmin(UserReqDto userReqDto, Pageable pageable) {

    Map<String, Object> data = new HashMap<>();

    // 1. 조건에 맞는 인터페이스 조회
    Page<UserResDto> userDtoList = userJooqRepository.findAllUserWithConditions(userReqDto,
        pageable);

    // 2. Page -> PageResponse 변환(이미 DTO로 변환된 상태이므로 추가 변환은 필요 없음)
    PageResponse<UserResDto> pagedUserDtoList = ModelMapperUtils.map(userDtoList);

    // 3. 결과를 Map에 담아 반환
    data.put("data", pagedUserDtoList);

    return data;
  }

  /**
   * @기능 : 관리자용 특정 사용자 상세 조회
   * @param userId 사용자 ID
   * @return 사용자 상세 정보가 담긴 Map
   */
  @Override
  public Map<String, Object> findByUserForAdmin(String userId) {

    Map<String, Object> data = new HashMap<>();

    // 1. ID로 인터페이스 조회(만약 인터페이스가 존재하지 않으면 null 반환)
    // 2. Entity -> DTO 변환
    UserResDto userResDto = userRepository.findById(userId)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .map(entity -> entity.toDto(UserResDto.class))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 3. 결과를 Map에 담아 반환
    data.put("data", userResDto);
    return data;
  }

  /**
   * @기능 : 관리자용 사용자 정보 수정 (이메일, 사용자 타입은 수정 불가)
   * @param userReqDto 사용자 수정 요청 DTO
   * @return 수정된 사용자 정보가 담긴 Map
   */
  @Override
  public Map<String, Object> updateUserForAdmin(UserReqDto userReqDto) {

    // Email, ID는 수정 불가
    Map<String, Object> data = new HashMap<>();

    // 1. DTO -> Entity 변환
    User userEntity = userReqDto.toEntity(User.class);

    // 2. ID로 기존 엔티티 조회
    User currentUser = userRepository.findById(userEntity.getUserId())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    if (!currentUser.getEmail().equals(userEntity.getEmail())) {
      throw new BusinessException(ErrorCode.EMAIL_NOT_UPDATE);
    }

    if (!currentUser.getUserType().equals(userEntity.getUserType())) {
      throw new BusinessException(ErrorCode.EMAIL_NOT_UPDATE);
    }

    // 수정 가능한 항목만 별도 기입
    currentUser.setPhoneNum(userEntity.getPhoneNum());

    // 3. 엔티티 수정 & 저장(자동)
    User savedUserEntity = userRepository.save(currentUser);

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", savedUserEntity.toDto(UserResDto.class));

    return data;

  }

  /**
   * @기능 : 관리자용 사용자 삭제 (관련 역할도 함께 삭제)
   * @param userId 삭제할 사용자 ID
   * @return 삭제 성공 메시지가 담긴 Map
   */
  @Override
  public Map<String, Object> deleteUserForAdmin(String userId) {
    Map<String, Object> data = new HashMap<>();

    // 1. ID로 인터페이스 조회(만약 인터페이스가 존재하지 않으면 NotFoundException 발생)
    User userEntity = userRepository.findById(userId)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    userEntity.setSts(Status.NAGATIVE);

    List<RoleUser> roles = roleUserRepository.findByRoleUserIdUserIdAndSts(userId, Status.POSITIVE);

    roles.forEach(role -> role.setSts(Status.NAGATIVE));

    roleUserRepository.saveAll(roles);

    // 3. 결과를 Map에 담아 반환
    data.put("data", "success");
    return data;

  }

}
