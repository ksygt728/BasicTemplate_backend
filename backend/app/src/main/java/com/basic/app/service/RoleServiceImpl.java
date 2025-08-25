package com.basic.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basic.app.api.ModelMapperUtils;
import com.basic.app.api.PageResponse;
import com.basic.app.dto.requestDto.RoleMenuReqDto;
import com.basic.app.dto.requestDto.RoleReqDto;
import com.basic.app.dto.requestDto.RoleUserReqDto;
import com.basic.app.dto.requestDto.UserReqDto;
import com.basic.app.dto.responseDto.RoleResDto;
import com.basic.app.dto.responseDto.RoleUserResDto;
import com.basic.app.dto.responseDto.UserResDto;
import com.basic.app.entity.Role;
import com.basic.app.entity.RoleUser;
import com.basic.app.entity.User;
import com.basic.app.exception.ErrorCode;
import com.basic.app.exception.customException.BusinessException;
import com.basic.app.exception.customException.NotFoundException;
import com.basic.app.repository.MenuRepository;
import com.basic.app.repository.RoleMenuRepository;
import com.basic.app.repository.RoleRepository;
import com.basic.app.repository.RoleUserRepository;
import com.basic.app.repository.UserRepository;
import com.basic.app.repository.jooqRepository.RoleJooqRepository;
import com.basic.app.repository.jooqRepository.UserJooqRepository;
import com.basic.app.service.interfaces.RoleService;
import com.basic.app.util.Status;
import com.basic.app.util.Validator;

@Transactional
@Service
public class RoleServiceImpl implements RoleService {

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MenuRepository menuRepository;

  @Autowired
  private RoleUserRepository roleUserRepository;

  @Autowired
  private RoleMenuRepository roleMenuRepository;

  @Autowired
  private UserJooqRepository userJooqRepository;

  @Autowired
  private RoleJooqRepository roleJooqRepository;

  @Override
  public Map<String, Object> findAllRoleForAdmin(RoleReqDto roleReqDto, Pageable pageable) {

    Map<String, Object> data = new HashMap<>();

    // 1. 조건에 맞는 인터페이스 조회
    Page<RoleResDto> roleDtoList = roleJooqRepository.findAllRoleWithConditions(roleReqDto,
        pageable);

    // 2. Page -> PageResponse 변환(이미 DTO로 변환된 상태이므로 추가 변환은 필요 없음)
    PageResponse<RoleResDto> pagedRoleDtoList = ModelMapperUtils.map(roleDtoList);

    // 3. 결과를 Map에 담아 반환
    data.put("data", pagedRoleDtoList);

    return data;
  }

  @Override
  public Map<String, Object> findByRoleForAdmin(String roleCd) {
    Map<String, Object> data = new HashMap<>();

    // 1. ID로 인터페이스 조회(만약 인터페이스가 존재하지 않으면 null 반환)
    // 2. Entity -> DTO 변환
    RoleResDto roleResDto = roleRepository.findById(roleCd)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .map(entity -> entity.toDto(RoleResDto.class))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 3. 결과를 Map에 담아 반환
    data.put("data", roleResDto);
    return data;
  }

  @Override
  public Map<String, Object> insertRoleForAdmin(RoleReqDto roleReqDto) {

    Map<String, Object> data = new HashMap<>();

    // 1. DTO -> Entity 변환
    Role roleEntity = roleReqDto.toEntity(Role.class);

    // 2. ID로 기존 엔티티 조회
    roleRepository.findById(roleEntity.getRoleCd())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .ifPresent(entity -> {
          throw new BusinessException(ErrorCode.OBJECT_IS_EXISTED);
        });

    // 3. DTO -> Entity 후 데이터 저장
    Role savedRoleEntity = roleRepository.save(roleEntity);

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", savedRoleEntity.toDto(RoleResDto.class));
    return data;
  }

  @Override
  public Map<String, Object> updateRoleForAdmin(RoleReqDto roleReqDto) {

    Map<String, Object> data = new HashMap<>();

    // 1. DTO -> Entity 변환
    Role roleEntity = roleReqDto.toEntity(Role.class);

    // 2. ID로 기존 엔티티 조회
    roleRepository.findById(roleEntity.getRoleCd())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 3. 엔티티 수정 & 저장(자동)
    Role savedRoleEntity = roleRepository.save(roleEntity);

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", savedRoleEntity.toDto(RoleResDto.class));

    return data;
  }

  @Override
  public Map<String, Object> deleteRoleForAdmin(String roleCd) {
    Map<String, Object> data = new HashMap<>();

    // 1. ID로 인터페이스 조회(만약 인터페이스가 존재하지 않으면 NotFoundException 발생)
    Role roleEntity = roleRepository.findById(roleCd)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 2. 상태를 'D'로 변경하여 삭제 처리(자동 save)
    roleEntity.setSts(Status.NAGATIVE);

    // RoleMenu, RoleUser 도 cascade로 같이 변경됨

    // 3. 결과를 Map에 담아 반환
    data.put("data", "success");
    return data;

  }

  @Override
  public Map<String, Object> findByRoleMenuForAdmin(String roleCd) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByRoleMenuForAdmin'");
  }

  @Override
  public Map<String, Object> updateRoleMenuForAdmin(List<RoleMenuReqDto> roleMenu) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateRoleMenuForAdmin'");
  }

  @Override
  public Map<String, Object> findByRoleUserForAdmin(String userId, Pageable pageable) {

    Map<String, Object> data = new HashMap<>();

    // 1. 조건에 맞는 인터페이스 조회
    Page<RoleUser> roleUsers = roleUserRepository.findByRoleUserIdUserIdAndSts(userId, Status.POSITIVE, pageable);

    // 2. Page -> PageResponse 변환(이미 DTO로 변환된 상태이므로 추가 변환은 필요 없음)
    PageResponse<RoleUserResDto> pagedRoleUserDtoList = ModelMapperUtils.map(roleUsers,
        RoleUserResDto.class);

    // 3. 결과를 Map에 담아 반환
    data.put("data", pagedRoleUserDtoList);

    return data;
  }

  @Override
  public Map<String, Object> findAllRoleUserForAdmin(UserReqDto userReqDto, Pageable pageable) {
    Map<String, Object> data = new HashMap<>();

    // 1. 조건에 맞는 인터페이스 조회
    Page<UserResDto> userDtoList = userJooqRepository.findAllUserWithConditions(userReqDto, pageable);

    // 2. Page -> PageResponse 변환(이미 DTO로 변환된 상태이므로 추가 변환은 필요 없음)
    PageResponse<UserResDto> pagedUserDtoList = ModelMapperUtils.map(userDtoList);

    // 3. 결과를 Map에 담아 반환
    data.put("data", pagedUserDtoList);

    return data;
  }

  @Override
  public Map<String, Object> insertRoleUserForAdmin(List<RoleUserReqDto> roleUserReqDtoList) {

    Map<String, Object> data = new HashMap<>();

    // 1. DTO -> Entity 변환
    List<RoleUser> roleUserEntityList = roleUserReqDtoList.stream()
        .map(dto -> dto.toEntity(dto))
        .toList();

    // 2. ID로 기존 엔티티 조회
    Validator.existsOne(roleUserRepository, roleUserEntityList.stream()
        .map(RoleUser::getRoleUserId).toList())
        .ifPresent(entity -> {
          throw new BusinessException(ErrorCode.OBJECT_IS_EXISTED);
        });

    // 3. DTO -> Entity 후 데이터 저장
    roleUserEntityList.forEach(entity -> {
      User userEntity = userRepository.findById(entity.getRoleUserId().getUserId())
          .filter(user -> user.getSts().equals(Status.POSITIVE))
          .orElseThrow(() -> new BusinessException(ErrorCode.OBJECT_IS_EXISTED, entity.getRoleUserId().getUserId()));

      Role roleEntity = roleRepository.findById(entity.getRoleUserId().getRoleCd())
          .filter(role -> role.getSts().equals(Status.POSITIVE))
          .orElseThrow(() -> new BusinessException(ErrorCode.OBJECT_IS_EXISTED, entity.getRoleUserId().getRoleCd()));

      entity.setUser(userEntity);
      entity.setRole(roleEntity);

    });

    List<RoleUser> savedRoleUserList = roleUserRepository.saveAll(roleUserEntityList);

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", ModelMapperUtils.map(savedRoleUserList, RoleUserResDto.class));
    return data;

  }

  @Override
  public Map<String, Object> deleteRoleUserForAdmin(List<RoleUserReqDto> roleUserReqDtoList) {
    Map<String, Object> data = new HashMap<>();

    // 1. DTO -> Entity 변환
    List<RoleUser> roleUserEntityList = roleUserReqDtoList.stream()
        .map(dto -> dto.toEntity(dto))
        .toList();

    // 2. ID로 기존 엔티티 조회
    List<RoleUser> validatedRoleUserEntityList = Validator.existsAll(roleUserRepository, RoleUser::getRoleUserId,
        roleUserEntityList.stream().map(RoleUser::getRoleUserId).toList())
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 3. 상태를 'D'로 변경하여 삭제 처리(자동 save)
    validatedRoleUserEntityList.forEach(entity -> entity.setSts(Status.NAGATIVE));

    // 4. 결과를 Map에 담아 반환
    data.put("data", "success");
    return data;
  }

}
