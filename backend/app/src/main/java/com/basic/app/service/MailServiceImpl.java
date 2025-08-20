package com.basic.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basic.app.dto.requestDto.MailMReqDto;
import com.basic.app.entity.ComCodeM;
import com.basic.app.mail.MailSendManager;
import com.basic.app.repository.MailHRepository;
import com.basic.app.repository.MailMRepository;
import com.basic.app.service.interfaces.MailService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Transactional
@Service
public class MailServiceImpl implements MailService {

  @Autowired
  private MailSendManager mailSendManager;

  @Autowired
  private MailMRepository mailMRepository;

  @Autowired
  private MailHRepository mailHRepository;

  @Override
  public void mailSendTest1() throws Exception {
    // 정상적으로 메일 아이디가 있는경우
    List<String> toEmails = List.of("ksygt728@naver.com", "ksygt728@gmail.com");
    ComCodeM comCodeM1 = ComCodeM.builder()
        .grpCd("TEST_CODE1")
        .grpNm("테스트 코드1")
        .grpCdType("TEST1")
        .build();
    ComCodeM comCodeM2 = ComCodeM.builder()
        .grpCd("TEST_CODE2")
        .grpNm("테스트 코드2")
        .grpCdType("TEST2")
        .build();
    List<ComCodeM> comCodes = List.of(comCodeM1, comCodeM2);

    mailSendManager.sendMail("MAIL-001", toEmails, Map.of("params", comCodes));
  }

  @Override
  public void mailSendTest2() throws Exception {

    List<String> toEmails = List.of("ksygt728@naver.com", "ksygt728@gmail.com",
        "ksygt728@naver.com",
        "ksygt728ㅁgmail.com");
    ComCodeM comCodeM1 = ComCodeM.builder()
        .grpCd("TEST_CODE1")
        .grpNm("테스트 코드1")
        .grpCdType("TEST1")
        .build();
    ComCodeM comCodeM2 = ComCodeM.builder()
        .grpCd("TEST_CODE2")
        .grpNm("테스트 코드2")
        .grpCdType("TEST2")
        .build();
    List<ComCodeM> comCodes = List.of(comCodeM1, comCodeM2);

    mailSendManager.insertMailHistory("MAIL-002", toEmails, Map.of("params", comCodes));

    // mailSendManager.sendMail("MAIL-001", toEmails, Map.of("params", comCodes));

    // mailSendManager.sendMailToBatch();

    // mailSendManager.sendMailByLogId("8f268dd8-8771-41f5-802e-f62029212752");

  }

  @Override
  public Map<String, Object> findAllMailForAdmin() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAllMailForAdmin'");
  }

  @Override
  public Map<String, Object> findByMailForAdmin(String mailId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByMailForAdmin'");
  }

  @Override
  public Map<String, Object> findByMailHistoryForAdmin(String mailId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByMailHistoryForAdmin'");
  }

  @Override
  public Map<String, Object> insertMailForAdmin(MailMReqDto mailM) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'insertMailForAdmin'");
  }

  @Override
  public Map<String, Object> updateMailForAdmin(MailMReqDto mailM) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateMailForAdmin'");
  }

  @Override
  public Map<String, Object> deleteMailForAdmin(String mailId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteMailForAdmin'");
  }

}
