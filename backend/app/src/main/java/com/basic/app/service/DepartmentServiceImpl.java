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

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

  @Autowired
  private DepartmentRepository departmentRepository;

  @Autowired
  private DepartmentJooqRepository departmentJooqRepository;

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