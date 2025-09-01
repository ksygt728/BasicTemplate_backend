package com.basic.app.service.interfaces;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.basic.app.dto.requestDto.SmsMReqDto;

public interface SmsService {

  Map<String, Object> findAllSmsForAdmin(SmsMReqDto smsMReqDto,
      Pageable pageable);

  Map<String, Object> findBySmsForAdmin(String smsId);

  Map<String, Object> findBySmsHistoryForAdmin(String smsId, Pageable pageable);

  Map<String, Object> insertSmsForAdmin(SmsMReqDto smsM);

  Map<String, Object> updateSmsForAdmin(SmsMReqDto smsM);

  Map<String, Object> deleteSmsForAdmin(String smsId);

  Map<String, Object> smsAuth(String phoneNum);

  Map<String, Object> smsAuthValidation(String phoneNum, String smsCode);
}
