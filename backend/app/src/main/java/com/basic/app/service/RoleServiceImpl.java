package com.basic.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.catalina.mapper.Mapper;
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
import com.basic.app.dto.responseDto.RoleMenuResDto;
import com.basic.app.dto.responseDto.RoleResDto;
import com.basic.app.dto.responseDto.RoleUserResDto;
import com.basic.app.dto.responseDto.UserResDto;
import com.basic.app.entity.Menu;
import com.basic.app.entity.Role;
import com.basic.app.entity.RoleMenu;
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
import com.basic.app.repository.jooqRepository.RoleMenuJooqRepository;
import com.basic.app.repository.jooqRepository.UserJooqRepository;
import com.basic.app.service.interfaces.RoleService;
import com.basic.app.util.Status;
import com.basic.app.util.Validator;

/**
 * @파일명 : RoleServiceImpl.java
 * @설명 : 역할 관련 서비스 구현체 (역할, 역할-메뉴, 역할-사용자 관리)
 * @작성자 : 김승연
 * @작성일 : 2025.09.05
 * @변경이력 :
 *       2025.09.05 김승연 최초 생성
 */
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

  @Autowired
  private RoleMenuJooqRepository roleMenuJooqRepository;

  /**
   * @기능 : 관리자용 역할 전체 목록 조회 (페이징)
   * @param roleReqDto 역할 검색 조건 DTO
   * @param pageable   페이징 정보
   * @return 역할 목록 정보가 담긴 Map
   */
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

  /**
   * @기능 : 관리자용 특정 역할 상세 조회
   * @param roleCd 역할 코드
   * @return 역할 상세 정보가 담긴 Map
   */
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

  /**
   * @기능 : 관리자용 역할 신규 등록
   * @param roleReqDto 역할 등록 요청 DTO
   * @return 등록된 역할 정보가 담긴 Map
   */
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

  /**
   * @기능 : 관리자용 역할 정보 수정
   * @param roleReqDto 역할 수정 요청 DTO
   * @return 수정된 역할 정보가 담긴 Map
   */
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

  /**
   * @기능 : 관리자용 역할 삭제 (관련 역할-사용자도 함께 삭제)
   * @param roleCd 삭제할 역할 코드
   * @return 삭제 성공 메시지가 담긴 Map
   */
  @Override
  public Map<String, Object> deleteRoleForAdmin(String roleCd) {
    Map<String, Object> data = new HashMap<>();

    // 1. ID로 인터페이스 조회(만약 인터페이스가 존재하지 않으면 NotFoundException 발생)
    Role roleEntity = roleRepository.findById(roleCd)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 2. 상태를 'D'로 변경하여 삭제 처리(자동 save)
    roleUserRepository.findByRoleUserIdRoleCdAndSts(roleCd, Status.POSITIVE)
        .stream().forEach(entity -> {
          entity.setSts(Status.NAGATIVE);
        });

    roleEntity.setSts(Status.NAGATIVE);

    // RoleMenu, RoleUser 도 cascade로 같이 변경됨

    // 3. 결과를 Map에 담아 반환
    data.put("data", "success");
    return data;

  }

  /**
   * @기능 : 관리자용 특정 역할의 메뉴 권한 조회 (트리 구조)
   * @param roleCd 역할 코드
   * @return 역할별 메뉴 권한 정보가 담긴 Map
   */
  @Override
  public Map<String, Object> findByRoleMenuForAdmin(String roleCd) {

    Map<String, Object> data = new HashMap<>();

    // 1. ID로 기존 엔티티 조회
    roleRepository.findById(roleCd)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND, roleCd));

    List<RoleMenuResDto> roleMenuResDtoList = roleMenuJooqRepository
        .findByMenuTreeWithRole(roleCd);

    // 메뉴코드 → Menu 매핑
    Map<String, RoleMenuResDto> menuMap = roleMenuResDtoList.stream()
        .collect(Collectors.toMap(RoleMenuResDto::getMenuCd, m -> m));

    // 최상위 메뉴 리스트
    List<RoleMenuResDto> menuTree = new ArrayList<>();

    for (RoleMenuResDto menu : roleMenuResDtoList) {
      if (menu.getUpperMenu() != null) {
        RoleMenuResDto parent = menuMap.get(menu.getUpperMenu());
        if (parent != null) {
          parent.getChildMenus().add(menu);
        }
      } else {
        // 상위 메뉴 없으면 최상위 메뉴로 추가
        menuTree.add(menu);
      }
    }

    // 결과를 Map에 담아 반환
    data.put("data", menuTree);

    return data;

  }

  /**
   * @기능 : 관리자용 특정 역할의 메뉴 권한 수정
   * @param roleMenuReqDtoList 역할-메뉴 수정 요청 DTO 목록
   * @return 수정된 역할-메뉴 정보가 담긴 Map
   */
  @Override
  public Map<String, Object> updateRoleMenuForAdmin(List<RoleMenuReqDto> roleMenuReqDtoList) {
    Map<String, Object> data = new HashMap<>();

    // 1. DTO -> Entity 변환
    List<RoleMenu> roleMenuEntityList = roleMenuReqDtoList.stream()
        .map(dto -> dto.toEntity(dto))
        .toList();

    /*
     * [코드 주석 사유]
     * - 실제 있는 데이터를 수정하는게 아니라 수정자체가 insert또는 update가 될 수 있음 -> 데이터가 있던 없던 무조건 insert
     * - 기준정보테이블에는 데이터가 있는지 확인 / 본 테이블은 데이터 확인 X
     */
    // // 2. ID로 기존 엔티티 조회 --- IGNORE ---
    // List<RoleMenu> validatedRoleUserEntityList =
    // Validator.existsAll(roleMenuRepository, RoleMenu::getRoleMenuId,
    // roleMenuEntityList.stream().map(RoleMenu::getRoleMenuId).toList())
    // .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 2. ID로 기존 엔티티 조회
    roleMenuEntityList.forEach(entity -> {

      Role role = roleRepository.findById(entity.getRoleMenuId().getRoleCd())
          .filter(e -> e.getSts().equals(Status.POSITIVE))
          .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND, entity.getRoleMenuId().getRoleCd()));

      Menu menu = menuRepository.findById(entity.getRoleMenuId().getMenuCd())
          .filter(e -> e.getSts().equals(Status.POSITIVE))
          .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND, entity.getRoleMenuId().getMenuCd()));

      // 3. 엔티티 수정 & 저장(자동)
      entity.setRole(role);
      entity.setMenu(menu);
    });

    List<RoleMenu> savedMenuEntity = roleMenuRepository.saveAll(roleMenuEntityList);

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", savedMenuEntity.stream().map(entity -> entity.toDto(entity)).toList());

    return data;
  }

  /**
   * @기능 : 관리자용 특정 사용자의 역할 조회 (페이징)
   * @param userId   사용자 ID
   * @param pageable 페이징 정보
   * @return 사용자별 역할 정보가 담긴 Map
   */
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

  /**
   * @기능 : 관리자용 역할-사용자 전체 목록 조회 (페이징)
   * @param userReqDto 사용자 검색 조건 DTO
   * @param pageable   페이징 정보
   * @return 사용자 목록 정보가 담긴 Map
   */
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

  /**
   * @기능 : 관리자용 역할-사용자 관계 신규 등록
   * @param roleUserReqDtoList 역할-사용자 등록 요청 DTO 목록
   * @return 등록된 역할-사용자 정보가 담긴 Map
   */
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
          .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND, entity.getRoleUserId().getUserId()));

      Role roleEntity = roleRepository.findById(entity.getRoleUserId().getRoleCd())
          .filter(role -> role.getSts().equals(Status.POSITIVE))
          .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND, entity.getRoleUserId().getRoleCd()));

      entity.setUser(userEntity);
      entity.setRole(roleEntity);

    });

    List<RoleUser> savedRoleUserList = roleUserRepository.saveAll(roleUserEntityList);

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", ModelMapperUtils.map(savedRoleUserList, RoleUserResDto.class));
    return data;

  }

  /**
   * @기능 : 관리자용 역할-사용자 관계 삭제
   * @param roleUserReqDtoList 역할-사용자 삭제 요청 DTO 목록
   * @return 삭제 성공 메시지가 담긴 Map
   */
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
