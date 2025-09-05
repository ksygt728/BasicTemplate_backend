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

/**
 * @파일명 : MultiLangServiceImpl.java
 * @설명 : 다국어 관련 서비스 구현체
 * @작성자 : 김승연
 * @작성일 : 2025.09.05
 * @변경이력 :
 *       2025.09.05 김승연 최초 생성
 */
@Transactional
@Service
public class MultiLangServiceImpl implements MultiLangService {

  @Autowired
  private MulLangRepository mulLangRepository;

  @Autowired
  private MulLangJooqRepository mulLangJooqRepository;

  @Autowired
  MessageSource messageSource;

  /**
   * @기능 : 관리자용 다국어 전체 목록 조회 (페이징)
   * @param mulLangReqDto 다국어 검색 조건 DTO
   * @param pageable      페이징 정보
   * @return 다국어 목록 정보가 담긴 Map
   */
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

  /**
   * @기능 : 관리자용 특정 다국어 상세 조회
   * @param langGubun 언어 구분
   * @param langCd    언어 코드
   * @return 다국어 상세 정보가 담긴 Map
   */
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

  /**
   * @기능 : 관리자용 다국어 신규 등록
   * @param mulLangReqDto 다국어 등록 요청 DTO
   * @return 등록된 다국어 정보가 담긴 Map
   */
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

  /**
   * @기능 : 관리자용 다국어 정보 수정
   * @param mulLangReqDto 다국어 수정 요청 DTO
   * @return 수정된 다국어 정보가 담긴 Map
   */
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

  /**
   * @기능 : 관리자용 다국어 전체 삭제 (언어 구분별)
   * @param langGubun 언어 구분
   * @param langCd    언어 코드
   * @return 삭제 성공 메시지가 담긴 Map
   */
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

  /**
   * @기능 : 관리자용 특정 다국어 상세 삭제
   * @param langType  언어 타입
   * @param langGubun 언어 구분
   * @param langCd    언어 코드
   * @return 삭제 성공 메시지가 담긴 Map
   */
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
