package com.basic.app.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
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
import com.basic.app.sms.SmsSendManager;
import com.basic.app.util.Status;

/**
 * @파일명 : SmsServiceImpl.java
 * @설명 : SMS 관련 서비스 구현체 (SMS 발송, 인증, 이력 관리)
 * @작성자 : 김승연
 * @작성일 : 2025.09.05
 * @변경이력 :
 *       2025.09.05 김승연 최초 생성
 */
@Transactional
@Service
public class SmsServiceImpl implements SmsService {

  @Autowired
  private SmsMRepository smsMRepository;

  @Autowired
  private SmsMJooqRepository smsMJooqRepository;

  @Autowired
  private RedisTemplate redisTemplate;

  @Autowired
  private SmsHRepository smsHRepository;

  @Autowired
  private SmsSendManager smsSendManager;

  private static final String SMS_AUTH_PREFIX = "smsAuth:";
  private static final long SMS_AUTH_EXPIRE_TIME = 180; // 3분

  /**
   * @기능 : 관리자용 SMS 전체 목록 조회 (페이징)
   * @param smsMReqDto SMS 검색 조건 DTO
   * @param pageable   페이징 정보
   * @return SMS 목록 정보가 담긴 Map
   */
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

  }

  /**
   * @기능 : 관리자용 특정 SMS 상세 조회
   * @param smsId SMS ID
   * @return SMS 상세 정보가 담긴 Map
   */
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

  /**
   * @기능 : 관리자용 특정 SMS의 발송 이력 조회 (페이징)
   * @param smsId    SMS ID
   * @param pageable 페이징 정보
   * @return SMS 발송 이력 정보가 담긴 Map
   */
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

  /**
   * @기능 : 관리자용 SMS 정보 신규 등록
   * @param smsM SMS 등록 요청 DTO
   * @return 등록된 SMS 정보가 담긴 Map
   */
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

  /**
   * @기능 : 관리자용 SMS 정보 수정
   * @param smsM SMS 수정 요청 DTO
   * @return 수정된 SMS 정보가 담긴 Map
   */
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

  /**
   * @기능 : 관리자용 SMS 정보 삭제 (상태 변경)
   * @param smsId 삭제할 SMS ID
   * @return 삭제 성공 메시지가 담긴 Map
   */
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

  /**
   * @기능 : SMS 인증번호 발송
   * @param phoneNum 휴대폰 번호
   * @return 발송 결과가 담긴 Map
   */
  @Override
  public Map<String, Object> smsAuth(String phoneNum) {
    Map<String, Object> data = new HashMap<>();

    String code = String.valueOf((int) (Math.random() * 900000) + 100000);

    smsSendManager.sendSms("SMS_AUTH", phoneNum, Map.of("code", code)); // SMS 발송

    // Redis에 저장 (3분 TTL)
    redisTemplate.opsForValue()
        .set(SMS_AUTH_PREFIX + phoneNum, code, SMS_AUTH_EXPIRE_TIME, TimeUnit.SECONDS);

    return data;

  }

  /**
   * @기능 : SMS 인증번호 검증
   * @param phoneNum 휴대폰 번호
   * @param smsCode  인증번호
   * @return 검증 결과가 담긴 Map
   */
  @Override
  public Map<String, Object> smsAuthValidation(String phoneNum, String smsCode) {
    Map<String, Object> data = new HashMap<>();

    boolean isValid = false;
    try {
      String key = SMS_AUTH_PREFIX + phoneNum;
      String savedCode = redisTemplate.opsForValue().get(key).toString();

      if (savedCode != null && savedCode.equals(smsCode)) {
        redisTemplate.delete(key); // 일회성 사용 후 삭제
        isValid = true;
      }
    } catch (Exception e) {
      e.printStackTrace();
      isValid = false; // null인 경우 인증번호 만료
    }
    data.put("data", isValid);

    return data;

  }

}