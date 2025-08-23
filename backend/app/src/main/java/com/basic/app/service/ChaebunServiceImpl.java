package com.basic.app.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basic.app.api.ModelMapperUtils;
import com.basic.app.api.PageResponse;
import com.basic.app.dto.requestDto.ChaebunReqDto;
import com.basic.app.dto.responseDto.ChaebunResDto;
import com.basic.app.entity.Chaebun;
import com.basic.app.exception.ErrorCode;
import com.basic.app.exception.customException.BusinessException;
import com.basic.app.exception.customException.NotFoundException;
import com.basic.app.repository.ChaebunRepository;
import com.basic.app.repository.jooqRepository.ChaebunJooqRepository;
import com.basic.app.service.interfaces.ChaebunService;
import com.basic.app.util.Status;

@Transactional
@Service
public class ChaebunServiceImpl implements ChaebunService {

  @Autowired
  private ChaebunRepository chaebunRepository;

  @Autowired
  private ChaebunJooqRepository chaebunJooqRepository;

  @Override
  public Map<String, Object> findAllChaebunForAdmin(ChaebunReqDto chaebunReqDto, Pageable pageable) {

    Map<String, Object> data = new HashMap<>();

    // 1. 조건에 맞는 인터페이스 조회
    Page<ChaebunResDto> smsMDtoList = chaebunJooqRepository.findAllChaebunMWithConditions(chaebunReqDto,
        pageable);

    // 2. Page -> PageResponse 변환(이미 DTO로 변환된 상태이므로 추가 변환은 필요 없음)
    PageResponse<ChaebunResDto> pagedMailMDtoList = ModelMapperUtils.map(smsMDtoList);

    // 3. 결과를 Map에 담아 반환
    data.put("data", pagedMailMDtoList);

    return data;
  }

  @Override
  public Map<String, Object> findByChaebunForAdmin(String seqId) {
    Map<String, Object> data = new HashMap<>();

    // 1. ID로 인터페이스 조회(만약 인터페이스가 존재하지 않으면 null 반환)
    // 2. Entity -> DTO 변환
    ChaebunResDto chaebunResDto = chaebunRepository.findById(seqId)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .map(entity -> entity.toDto(ChaebunResDto.class))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 3. 결과를 Map에 담아 반환
    data.put("data", chaebunResDto);
    return data;
  }

  @Override
  public Map<String, Object> insertChaebunForAdmin(ChaebunReqDto chaebunReqDto) {

    Map<String, Object> data = new HashMap<>();

    // 1. DTO -> Entity 변환
    Chaebun chaebunEntity = chaebunReqDto.toEntity(Chaebun.class);

    // 2. ID로 기존 엔티티 조회
    chaebunRepository.findById(chaebunEntity.getSeqId())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .ifPresent(entity -> {
          throw new BusinessException(ErrorCode.OBJECT_IS_EXISTED);
        });

    // 3. DTO -> Entity 후 데이터 저장
    chaebunEntity.setCurrentValue(0); // 현재값은 0으로 초기화
    Chaebun savedChaebunEntity = chaebunRepository.save(chaebunEntity);

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", savedChaebunEntity.toDto(ChaebunResDto.class));
    return data;
  }

  @Override
  public Map<String, Object> updateChaebunForAdmin(ChaebunReqDto chaebunReqDto) {

    Map<String, Object> data = new HashMap<>();

    // 1. DTO -> Entity 변환
    Chaebun chaebunEntity = chaebunReqDto.toEntity(Chaebun.class);

    // 2. ID로 기존 엔티티 조회
    Chaebun currentChaebun = chaebunRepository.findById(chaebunEntity.getSeqId())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    chaebunEntity.setCurrentValue(currentChaebun.getCurrentValue()); // 현재값은 기존값 유지

    // 3. 엔티티 수정 & 저장(자동)
    Chaebun savedChaebunEntity = chaebunRepository.save(chaebunEntity);

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", savedChaebunEntity.toDto(ChaebunResDto.class));

    return data;
  }

  @Override
  public Map<String, Object> deleteChaebunForAdmin(String seqId) {
    Map<String, Object> data = new HashMap<>();

    // 1. ID로 인터페이스 조회(만약 인터페이스가 존재하지 않으면 NotFoundException 발생)
    Chaebun chaebunEntity = chaebunRepository.findById(seqId)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 2. 상태를 'D'로 변경하여 삭제 처리(자동 save)
    chaebunEntity.setSts(Status.NAGATIVE);

    // 3. 결과를 Map에 담아 반환
    data.put("data", "success");
    return data;

  }

  @Override
  public String generateSeq(String seqId) {

    Chaebun chaebun = chaebunRepository.findByIdAndSts(seqId, Status.POSITIVE)
        .orElseThrow(() -> new BusinessException(ErrorCode.CHAEBUN_NOT_REGISTERD, seqId));

    /* step 1 : 값 증가 계산 */
    // DATE_FORMAT을 사용하는데, 오늘 날짜와 마지막 수정일이 다르면 currentValue를 0으로 초기화
    String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    String lastUpdated = chaebun.getTimestamp().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

    if (!today.equals(lastUpdated) && chaebun.getDateformat() != null && !chaebun.getDateformat().isEmpty()) {
      chaebun.setCurrentValue(0); // 초기화
    }

    // 현재값 + 증가량
    int valuePart = chaebun.getCurrentValue() + chaebun.getStep();
    chaebun.setCurrentValue(valuePart);

    if (valuePart >= Math.pow(10, chaebun.getLength())) { // 최대값 초과 시 에러
      throw new BusinessException(ErrorCode.CHAEBUN_OVERFLOW, seqId);
    }

    String nextValue = String.format("%0" + chaebun.getLength() + "d", valuePart);

    /* step 2 : 데이터포맷 계산 */
    String datePart = "";
    if (chaebun.getDateformat() != null) {
      datePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern(chaebun.getDateformat()));
    }

    /* step 3 : 패턴 적용 */
    String pattern = chaebun.getPattern();
    pattern = pattern.replace("{PREFIX}", chaebun.getPrefix())
        .replace("{DATEFORMAT}", datePart)
        .replace("{VALUE}", nextValue);

    chaebunRepository.save(chaebun);

    return pattern;
  }

}
