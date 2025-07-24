package com.basic.app.service.interfaces;

import java.util.Map;

import com.basic.app.dto.requestDto.MailMReqDto;

public interface MailService {

  Map<String, Object> findAllMailForAdmin();

  Map<String, Object> findByMailForAdmin(String mailId);

  Map<String, Object> findByMailHistoryForAdmin(String mailId);

  Map<String, Object> insertMailForAdmin(MailMReqDto mailM);

  Map<String, Object> updateMailForAdmin(MailMReqDto mailM);

  Map<String, Object> deleteMailForAdmin(String mailId);

}
