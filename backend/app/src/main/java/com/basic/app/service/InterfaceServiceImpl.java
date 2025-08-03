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
import com.basic.app.dto.requestDto.InterfaceReqDto;
import com.basic.app.dto.responseDto.InterfaceResDto;
import com.basic.app.entity.Interface;
import com.basic.app.exception.ErrorCode;
import com.basic.app.exception.customException.BusinessException;
import com.basic.app.exception.customException.NotFoundException;
import com.basic.app.repository.InterfaceRepository;
import com.basic.app.repository.jooqRepository.InterfaceJooqRepository;
import com.basic.app.service.interfaces.InterfaceService;
import com.basic.app.util.Status;

@Service
@Transactional
public class InterfaceServiceImpl implements InterfaceService {

  @Autowired
  private InterfaceRepository interfaceRepository;

  @Autowired
  private InterfaceJooqRepository interfaceJooqRepository;

  @Override
  public Map<String, Object> findAllInterfaceForAdmin(Pageable pageable) {
    Map<String, Object> data = new HashMap<>();

    // 1. 모든 인터페이스 조회
    Page<Interface> interfaceList = interfaceRepository.findAllBySts(Status.POSITIVE, pageable);
    // 2. Entity -> DTO 변환
    PageResponse<InterfaceResDto> pagedinterfaceDtoList = ModelMapperUtils.map(interfaceList, InterfaceResDto.class);

    // 3. 결과를 Map에 담아 반환
    data.put("data", pagedinterfaceDtoList);

    return data;
  }

  @Override
  public Map<String, Object> findAllInterfaceWithConditionsForAdmin(InterfaceReqDto interfaceReqDto,
      Pageable pageable) {
    Map<String, Object> data = new HashMap<>();

    // 1. 조건에 맞는 인터페이스 조회
    Page<InterfaceResDto> interfaceDtoList = interfaceJooqRepository.findAllInterfaceWithConditions(
        interfaceReqDto,
        pageable);

    // 2. Page -> PageResponse 변환(이미 DTO로 변환된 상태이므로 추가 변환은 필요 없음)
    PageResponse<InterfaceResDto> pagedInterfaceDtoList = ModelMapperUtils.map(interfaceDtoList);

    // 3. 결과를 Map에 담아 반환
    data.put("data", pagedInterfaceDtoList);

    return data;
  }

  @Override
  public Map<String, Object> findByInterfaceForAdmin(String ifId) {
    Map<String, Object> data = new HashMap<>();

    // 1. ID로 인터페이스 조회(만약 인터페이스가 존재하지 않으면 null 반환)
    // 2. Entity -> DTO 변환
    InterfaceResDto interfaceResDto = interfaceRepository.findById(ifId)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .map(entity -> entity.toDto(InterfaceResDto.class))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 3. 결과를 Map에 담아 반환
    data.put("data", interfaceResDto);
    return data;
  }

  @Override
  public Map<String, Object> executeInterfaceForAdmin(InterfaceReqDto ifc) {
    Map<String, Object> data = new HashMap<>();
    // TODO: 인터페이스 실행 로직 구현
    return data;
  }

  @Override
  public Map<String, Object> findByInterfaceHistoryForAdmin(String ifId) {
    Map<String, Object> data = new HashMap<>();
    // TODO: 인터페이스 이력 조회 로직 구현
    return data;
  }

  @Override
  public Map<String, Object> insertInterfaceForAdmin(InterfaceReqDto ifc) {

    Map<String, Object> data = new HashMap<>();

    // 1. DTO -> Entity 변환
    Interface ifcEntity = ifc.toEntity(Interface.class);

    // 2. ID로 기존 엔티티 조회
    interfaceRepository.findById(ifcEntity.getIfId())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .ifPresent(entity -> {
          throw new BusinessException(ErrorCode.OBJECT_IS_EXISTED);
        });

    // 3. DTO -> Entity 후 데이터 저장
    Interface savedIfcEntity = interfaceRepository.save(ifcEntity);

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", savedIfcEntity.toDto(InterfaceResDto.class));
    return data;
  }

  @Override
  public Map<String, Object> updateInterfaceForAdmin(InterfaceReqDto ifc) {

    Map<String, Object> data = new HashMap<>();

    // 1. DTO -> Entity 변환
    Interface ifcEntity = ifc.toEntity(Interface.class);

    // 2. ID로 기존 엔티티 조회
    interfaceRepository.findById(ifcEntity.getIfId())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 3. 엔티티 수정 & 저장(자동)
    Interface savedIfcEntity = interfaceRepository.save(ifcEntity);

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", savedIfcEntity.toDto(InterfaceResDto.class));

    return data;
  }

  @Override
  public Map<String, Object> deleteInterfaceForAdmin(String ifId) {
    Map<String, Object> data = new HashMap<>();

    // 1. ID로 인터페이스 조회(만약 인터페이스가 존재하지 않으면 NotFoundException 발생)
    Interface interfaceObj = interfaceRepository.findByIfIdAndSts(ifId, Status.POSITIVE)
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 2. 상태를 'D'로 변경하여 삭제 처리(자동 save)
    interfaceObj.setSts(Status.NAGATIVE);

    // 3. 결과를 Map에 담아 반환
    data.put("data", "success");
    return data;
  }

}
