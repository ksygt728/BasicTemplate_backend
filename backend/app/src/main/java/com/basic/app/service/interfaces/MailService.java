package com.basic.app.service.interfaces;

import java.util.List;
import java.util.Map;

import com.basic.app.dto.requestDto.MailMReqDto;

public interface MailService {

  public void mailSendTest1() throws Exception;

  public void mailSendTest2() throws Exception;

  Map<String, Object> findAllMailForAdmin();

  Map<String, Object> findByMailForAdmin(String mailId);

  Map<String, Object> findByMailHistoryForAdmin(String mailId);

  Map<String, Object> insertMailForAdmin(MailMReqDto mailM);

  Map<String, Object> updateMailForAdmin(MailMReqDto mailM);

  Map<String, Object> deleteMailForAdmin(String mailId);

}
