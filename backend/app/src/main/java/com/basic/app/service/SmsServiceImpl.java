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
import com.basic.app.dto.requestDto.SmsMReqDto;
import com.basic.app.dto.responseDto.SmsHResDto;
import com.basic.app.dto.responseDto.SmsMResDto;
import com.basic.app.entity.SmsH;
import com.basic.app.entity.SmsM;
import com.basic.app.exception.ErrorCode;
import com.basic.app.exception.customException.BusinessException;
import com.basic.app.exception.customException.NotFoundException;
import com.basic.app.repository.SmsHRepository;
import com.basic.app.repository.SmsMRepository;
import com.basic.app.repository.jooqRepository.SmsMJooqRepository;
import com.basic.app.service.interfaces.SmsService;
import com.basic.app.sms.SmsProvider;
import com.basic.app.util.Status;

@Transactional
@Service
public class SmsServiceImpl implements SmsService {

  @Autowired
  private SmsProvider smsProvider;

  @Autowired
  private SmsMRepository smsMRepository;

  @Autowired
  private SmsMJooqRepository smsMJooqRepository;

  @Autowired
  private SmsHRepository smsHRepository;

  @Override
  public Map<String, Object> findAllSmsForAdmin(SmsMReqDto smsMReqDto,
      Pageable pageable) {

    Map<String, Object> data = new HashMap<>();

    // 1. 조건에 맞는 인터페이스 조회
    Page<SmsMResDto> smsMDtoList = smsMJooqRepository.findAllSmsMWithConditions(smsMReqDto,
        pageable);

    // 2. Page -> PageResponse 변환(이미 DTO로 변환된 상태이므로 추가 변환은 필요 없음)
    PageResponse<SmsMResDto> pagedMailMDtoList = ModelMapperUtils.map(smsMDtoList);

    // 3. 결과를 Map에 담아 반환
    data.put("data", pagedMailMDtoList);

    return data;
    // String uesrname1 = "김승연1";
    // int code1 = (int) (Math.random() * 900000) + 100000;

    // String uesrname2 = "김승연2";
    // int code2 = (int) (Math.random() * 900000) + 100000;

    // smsProvider.sendSms("SMS-001", "01091360767", Map.of(
    // "username", uesrname1,
    // "code", code1));

    // smsProvider.sendSms("SMS-001", "01091360767", Map.of(
    // "username", uesrname2,
    // "code", code2));

    // return Map.of("message", "SMS 발송 성공");

  }

  @Override
  public Map<String, Object> findBySmsForAdmin(String smsId) {
    Map<String, Object> data = new HashMap<>();

    // 1. ID로 인터페이스 조회(만약 인터페이스가 존재하지 않으면 null 반환)
    // 2. Entity -> DTO 변환
    SmsMResDto smsMResDto = smsMRepository.findById(smsId)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .map(entity -> entity.toDto(SmsMResDto.class))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 3. 결과를 Map에 담아 반환
    data.put("data", smsMResDto);
    return data;
  }

  @Override
  public Map<String, Object> findBySmsHistoryForAdmin(String smsId, Pageable pageable) {

    Map<String, Object> data = new HashMap<>();

    // 1. 모든 인터페이스 조회
    Page<SmsH> pagedMailHList = smsHRepository.findAllBySmsIdAndSts(smsId,
        Status.POSITIVE,
        pageable);

    // 2. Entity -> DTO 변환
    PageResponse<SmsHResDto> pageResponseList = ModelMapperUtils.map(pagedMailHList, SmsHResDto.class);

    // 3. 결과를 Map에 담아 반환
    data.put("data", pageResponseList);

    return data;
  }

  @Override
  public Map<String, Object> insertSmsForAdmin(SmsMReqDto smsM) {

    Map<String, Object> data = new HashMap<>();

    // 1. DTO -> Entity 변환
    SmsM smsMEntity = smsM.toEntity(SmsM.class);

    // 2. ID로 기존 엔티티 조회
    smsMRepository.findById(smsMEntity.getSmsId())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .ifPresent(entity -> {
          throw new BusinessException(ErrorCode.OBJECT_IS_EXISTED);
        });

    // 3. DTO -> Entity 후 데이터 저장
    SmsM savedSmsMEntity = smsMRepository.save(smsMEntity);

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", savedSmsMEntity.toDto(SmsMResDto.class));
    return data;
  }

  @Override
  public Map<String, Object> updateSmsForAdmin(SmsMReqDto smsM) {

    Map<String, Object> data = new HashMap<>();

    // 1. DTO -> Entity 변환
    SmsM smsMEntity = smsM.toEntity(SmsM.class);

    // 2. ID로 기존 엔티티 조회
    smsMRepository.findById(smsMEntity.getSmsId())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 3. 엔티티 수정 & 저장(자동)
    SmsM savedSmsMEntity = smsMRepository.save(smsMEntity);

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", savedSmsMEntity.toDto(SmsMResDto.class));

    return data;
  }

  @Override
  public Map<String, Object> deleteSmsForAdmin(String smsId) {
    Map<String, Object> data = new HashMap<>();

    // 1. ID로 인터페이스 조회(만약 인터페이스가 존재하지 않으면 NotFoundException 발생)
    SmsM smsMEntity = smsMRepository.findById(smsId)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 2. 상태를 'D'로 변경하여 삭제 처리(자동 save)
    smsMEntity.setSts(Status.NAGATIVE);

    // 3. 결과를 Map에 담아 반환
    data.put("data", "success");
    return data;

  }

}