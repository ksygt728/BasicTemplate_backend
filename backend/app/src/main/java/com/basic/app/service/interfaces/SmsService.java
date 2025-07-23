package com.basic.app.service.interfaces;

import java.util.Map;

import com.basic.app.entity.SmsM;

public interface SmsService {

  Map<String, Object> findAllSmsForAdmin();

  Map<String, Object> findBySmsForAdmin(String smsId);

  Map<String, Object> findBySmsHistoryForAdmin(String smsId);

  Map<String, Object> insertSmsForAdmin(SmsM smsM);

  Map<String, Object> updateSmsForAdmin(SmsM smsM);

  Map<String, Object> deleteSmsForAdmin(String smsId);
}
