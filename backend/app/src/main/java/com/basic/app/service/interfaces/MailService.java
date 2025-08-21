package com.basic.app.service.interfaces;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.basic.app.dto.requestDto.MailMReqDto;

public interface MailService {

  public void mailSendTest1() throws Exception;

  public void mailSendTest2() throws Exception;

  Map<String, Object> findAllMailForAdmin(MailMReqDto mailMReqDto, Pageable pageable) throws Exception;

  Map<String, Object> findByMailForAdmin(String mailId);

  Map<String, Object> findByMailHistoryForAdmin(String mailId, Pageable pageable);

  Map<String, Object> insertMailForAdmin(MailMReqDto mailM);

  Map<String, Object> updateMailForAdmin(MailMReqDto mailM);

  Map<String, Object> deleteMailForAdmin(String mailId);

}
