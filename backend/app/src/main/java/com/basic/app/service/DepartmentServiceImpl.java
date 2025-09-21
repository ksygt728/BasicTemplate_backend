package com.basic.app.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basic.app.api.ModelMapperUtils;
import com.basic.app.api.PageResponse;
import com.basic.app.dto.requestDto.DepartmentReqDto;
import com.basic.app.dto.responseDto.DepartmentResDto;
import com.basic.app.entity.Department;
import com.basic.app.exception.ErrorCode;
import com.basic.app.exception.customException.BusinessException;
import com.basic.app.exception.customException.NotFoundException;
import com.basic.app.repository.DepartmentRepository;
import com.basic.app.repository.jooqRepository.DepartmentJooqRepository;
import com.basic.app.service.interfaces.DepartmentService;
import com.basic.app.util.Status;

/**
 * @파일명 : DepartmentServiceImpl.java
 * @설명 : 부서 관련 서비스 구현체
 * @작성자 : 김승연
 * @작성일 : 2025.09.05
 * @변경이력 :
 *       2025.09.05 김승연 최초 생성
 */
@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

  @Autowired
  private DepartmentRepository departmentRepository;

  @Autowired
  private DepartmentJooqRepository departmentJooqRepository;

  /**
   * @기능 : 관리자용 부서 전체 목록 조회 (페이징)
   * @param departmentReqDto 부서 검색 조건 DTO
   * @param pageable         페이징 정보
   * @return 부서 목록 정보가 담긴 Map
   */
  @Override
  public Map<String, Object> findAllDepartmentForAdmin(DepartmentReqDto departmentReqDto, Pageable pageable) {

    Map<String, Object> data = new HashMap<>();
    // 1. 조건에 맞는 인터페이스 조회
    Page<DepartmentResDto> departmentDtoList = departmentJooqRepository
        .findAllDepartmentWithConditions(departmentReqDto, pageable);
    // 2. Page -> PageResponse 변환(이미 DTO로 변환된 상태이므로 추가 변환은 필요 없음)

    PageResponse<DepartmentResDto> pagedDepartmentDtoList = ModelMapperUtils.map(departmentDtoList,
        DepartmentResDto.class);

    // 3. 결과를 Map에 담아 반환
    data.put("data", pagedDepartmentDtoList);
    return data;
  }

  /**
   * @기능 : 관리자용 특정 부서 상세 조회
   * @param deptCode 부서 코드
   * @return 부서 상세 정보가 담긴 Map
   */
  @Override
  public Map<String, Object> findByDepartmentForAdmin(String deptCode) {

    Map<String, Object> data = new HashMap<>();

    // 1. 조회(만약 인터페이스가 존재하지 않으면 null 반환)
    // 2. Entity -> DTO 변환
    DepartmentResDto departmentResDto = departmentRepository.findById(deptCode)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .map(entity -> entity.toDto(DepartmentResDto.class))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 3. 결과를 Map에 담아 반환
    data.put("data", departmentResDto);
    return data;
  }

  /**
   * @기능 : 관리자용 부서 등록 (HR에서 받아오는 정보로 추가 불가)
   * @param department 부서 등록 요청 DTO
   * @return 예외 발생 (등록 불가)
   */
  @Override
  public Map<String, Object> insertDepartmentForAdmin(DepartmentReqDto department) {

    Map<String, Object> data = new HashMap<>();

    // 부서정보는 HR에서 받아오는 정보로 추가할 수 없음
    throw new BusinessException(ErrorCode.DEPARTMENT_NOT_WRITE);

    // // 1. DTO -> Entity 변환
    // Department departmentEntity = department.toEntity(Department.class);

    // // 2. ID로 기존 엔티티 조회
    // departmentRepository.findById(departmentEntity.getDeptCode())
    // .filter(entity -> entity.getSts().equals(Status.POSITIVE))
    // .ifPresent(entity -> {
    // throw new BusinessException(ErrorCode.OBJECT_IS_EXISTED);
    // });

    // // 3. DTO -> Entity 후 데이터 저장
    // Department savedDepartmentEntity =
    // departmentRepository.save(departmentEntity);

    // // 4. Entity -> DTO 변환
    // // 5. 결과를 Map에 담아 반환
    // data.put("data", savedDepartmentEntity.toDto(DepartmentResDto.class));
    // return data;
  }

  /**
   * @기능 : 관리자용 부서 수정 (HR에서 받아오는 정보로 수정 불가)
   * @param department 부서 수정 요청 DTO
   * @return 예외 발생 (수정 불가)
   */
  @Override
  public Map<String, Object> updateDepartmentForAdmin(DepartmentReqDto department) {

    Map<String, Object> data = new HashMap<>();

    // 부서정보는 HR에서 받아오는 정보로 추가할 수 없음
    throw new BusinessException(ErrorCode.DEPARTMENT_NOT_WRITE);

    // // 1. DTO -> Entity 변환
    // Department departmentEntity = department.toEntity(Department.class);

    // // 2. ID로 기존 엔티티 조회
    // departmentRepository.findById(departmentEntity.getDeptCode())
    // .filter(entity -> entity.getSts().equals(Status.POSITIVE))
    // .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // // 3. 엔티티 수정 & 저장(자동)
    // Department savedDepartmentEntity =
    // departmentRepository.save(departmentEntity);

    // // 4. Entity -> DTO 변환
    // // 5. 결과를 Map에 담아 반환
    // data.put("data", savedDepartmentEntity.toDto(DepartmentResDto.class));

    // return data;
  }

  /**
   * @기능 : 관리자용 부서 삭제 (HR에서 받아오는 정보로 삭제 불가)
   * @param deptCode 부서 코드
   * @return 예외 발생 (삭제 불가)
   */
  @Override
  public Map<String, Object> deleteDepartmentForAdmin(String deptCode) {

    Map<String, Object> data = new HashMap<>();

    // 부서정보는 HR에서 받아오는 정보로 추가할 수 없음
    throw new BusinessException(ErrorCode.DEPARTMENT_NOT_WRITE);

    // // 1. ID로 조회(만약 인터페이스가 존재하지 않으면 NotFoundException 발생)
    // Department departmentEntity = departmentRepository.findById(deptCode)
    // .filter(entity -> entity.getSts().equals(Status.POSITIVE))
    // .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // // 2. 상태를 'D'로 변경하여 삭제 처리(자동 save)
    // departmentEntity.setSts(Status.NAGATIVE);

    // // 3. 결과를 Map에 담아 반환
    // data.put("data", "success");
    // return data;
  }

}