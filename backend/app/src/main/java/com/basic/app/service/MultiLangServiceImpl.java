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
import com.basic.app.dto.requestDto.MulLangReqDto;
import com.basic.app.dto.responseDto.MulLangResDto;
import com.basic.app.entity.MulLang;
import com.basic.app.entity.compositeKey.MulLangId;
import com.basic.app.exception.ErrorCode;
import com.basic.app.exception.customException.BusinessException;
import com.basic.app.exception.customException.NotFoundException;
import com.basic.app.repository.MulLangRepository;
import com.basic.app.repository.jooqRepository.MulLangJooqRepository;
import com.basic.app.service.interfaces.MultiLangService;
import com.basic.app.service.specialService.MessageSource;
import com.basic.app.util.Status;

@Transactional
@Service
public class MultiLangServiceImpl implements MultiLangService {

  @Autowired
  private MulLangRepository mulLangRepository;

  @Autowired
  private MulLangJooqRepository mulLangJooqRepository;

  @Autowired
  MessageSource messageSource;

  @Override
  public Map<String, Object> findAllMulLangForAdmin(MulLangReqDto mulLangReqDto, Pageable pageable) {

    Map<String, Object> data = new HashMap<>();

    // 1. 조건에 맞는 인터페이스 조회
    Page<MulLangResDto> mulLangDtoList = mulLangJooqRepository.findAllMulLangWithConditions(mulLangReqDto,
        pageable);

    // 2. Page -> PageResponse 변환(이미 DTO로 변환된 상태이므로 추가 변환은 필요 없음)
    PageResponse<MulLangResDto> pagedMulLangDtoList = ModelMapperUtils.map(mulLangDtoList);

    // 3. 결과를 Map에 담아 반환
    data.put("data", pagedMulLangDtoList);

    return data;
  }

  @Override
  public Map<String, Object> findByMulLangForAdmin(String langGubun, String langCd) {
    Map<String, Object> data = new HashMap<>();

    // 1. ID로 인터페이스 조회(만약 인터페이스가 존재하지 않으면 null 반환)
    // 2. Entity -> DTO 변환
    List<MulLangResDto> mulLangResDto = mulLangRepository.findByMulLangIdLangGubunAndMulLangIdLangCd(langGubun, langCd)
        .stream()
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .map(entity -> entity.toDto(entity))
        .toList();

    // 3. 결과를 Map에 담아 반환
    data.put("data", mulLangResDto);
    return data;
  }

  @Override
  public Map<String, Object> insertMulLangForAdmin(MulLangReqDto mulLangReqDto) {

    Map<String, Object> data = new HashMap<>();

    // 1. DTO -> Entity 변환
    MulLang mulLangEntity = mulLangReqDto.toEntity(mulLangReqDto);

    // 2. ID로 기존 엔티티 조회
    mulLangRepository.findById(mulLangEntity.getMulLangId())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .ifPresent(entity -> {
          throw new BusinessException(ErrorCode.OBJECT_IS_EXISTED);
        });

    // 3. DTO -> Entity 후 데이터 저장
    MulLang savedMulLangEntity = mulLangRepository.save(mulLangEntity);

    messageSource.reloadCache();

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", savedMulLangEntity.toDto(savedMulLangEntity));
    return data;
  }

  @Override
  public Map<String, Object> updateMulLangForAdmin(MulLangReqDto mulLangReqDto) {

    Map<String, Object> data = new HashMap<>();

    // 1. DTO -> Entity 변환
    MulLang mulLangEntity = mulLangReqDto.toEntity(mulLangReqDto);

    // 2. ID로 기존 엔티티 조회
    mulLangRepository.findById(mulLangEntity.getMulLangId())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 3. 엔티티 수정 & 저장(자동)
    MulLang savedMulLangEntity = mulLangRepository.save(mulLangEntity);

    messageSource.reloadCache();

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", savedMulLangEntity.toDto(savedMulLangEntity));

    return data;
  }

  @Override
  public Map<String, Object> deleteMulLangForAdmin(String langGubun, String langCd) {

    Map<String, Object> data = new HashMap<>();

    // 1. ID로 인터페이스 조회(만약 인터페이스가 존재하지 않으면 NotFoundException 발생)
    List<MulLang> mulLangEntityList = mulLangRepository.findByMulLangIdLangGubunAndMulLangIdLangCd(langGubun, langCd)
        .stream()
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .toList();

    // 2. 상태를 'D'로 변경하여 삭제 처리(자동 save)
    mulLangEntityList.forEach(entity -> {
      entity.setSts(Status.NAGATIVE);
    });

    messageSource.reloadCache();

    // 3. 결과를 Map에 담아 반환
    data.put("data", "success");
    return data;

  }

  @Override
  public Map<String, Object> deleteMulLangDetailForAdmin(String langType, String langGubun, String langCd) {

    Map<String, Object> data = new HashMap<>();

    // 1. ID로 인터페이스 조회(만약 인터페이스가 존재하지 않으면 NotFoundException 발생)
    MulLangId mulLangId = MulLangId.builder()
        .langType(langType)
        .langGubun(langGubun)
        .langCd(langCd)
        .build();

    MulLang mulLangEntity = mulLangRepository.findById(mulLangId)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 2. 상태를 'D'로 변경하여 삭제 처리(자동 save)
    mulLangEntity.setSts(Status.NAGATIVE);

    messageSource.reloadCache();

    // 3. 결과를 Map에 담아 반환
    data.put("data", "success");
    return data;

  }

}
