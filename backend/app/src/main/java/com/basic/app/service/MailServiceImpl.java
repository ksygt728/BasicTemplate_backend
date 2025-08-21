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
import com.basic.app.dto.requestDto.MailMReqDto;
import com.basic.app.dto.responseDto.MailHResDto;
import com.basic.app.dto.responseDto.MailMResDto;
import com.basic.app.dto.responseDto.ScheHResDto;
import com.basic.app.entity.ComCodeM;
import com.basic.app.entity.MailH;
import com.basic.app.entity.MailM;
import com.basic.app.entity.ScheH;
import com.basic.app.exception.ErrorCode;
import com.basic.app.exception.customException.BusinessException;
import com.basic.app.exception.customException.NotFoundException;
import com.basic.app.mail.MailSendManager;
import com.basic.app.repository.MailHRepository;
import com.basic.app.repository.MailMRepository;
import com.basic.app.repository.jooqRepository.MailMJooqRepository;
import com.basic.app.service.interfaces.MailService;
import com.basic.app.util.Status;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Transactional
@Service
public class MailServiceImpl implements MailService {

  @Autowired
  private MailSendManager mailSendManager;

  @Autowired
  private MailMJooqRepository mailMJooqRepository;

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
  public Map<String, Object> findAllMailForAdmin(MailMReqDto mailMReqDto,
      Pageable pageable) throws Exception {

    Map<String, Object> data = new HashMap<>();

    // 1. 조건에 맞는 인터페이스 조회
    Page<MailMResDto> mailMDtoList = mailMJooqRepository.findAllMailHWithConditions(mailMReqDto,
        pageable);

    // 2. Page -> PageResponse 변환(이미 DTO로 변환된 상태이므로 추가 변환은 필요 없음)
    PageResponse<MailMResDto> pagedMailMDtoList = ModelMapperUtils.map(mailMDtoList);

    // 3. 결과를 Map에 담아 반환
    data.put("data", pagedMailMDtoList);

    return data;
  }

  @Override
  public Map<String, Object> findByMailForAdmin(String mailId) {
    Map<String, Object> data = new HashMap<>();

    // 1. ID로 인터페이스 조회(만약 인터페이스가 존재하지 않으면 null 반환)
    // 2. Entity -> DTO 변환
    MailMResDto mailMResDto = mailMRepository.findById(mailId)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .map(entity -> entity.toDto(MailMResDto.class))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 3. 결과를 Map에 담아 반환
    data.put("data", mailMResDto);
    return data;

  }

  @Override
  public Map<String, Object> findByMailHistoryForAdmin(String mailId, Pageable pageable) {

    Map<String, Object> data = new HashMap<>();

    // 1. 모든 인터페이스 조회
    Page<MailH> pagedMailHList = mailHRepository.findAllByMailIdAndSts(mailId,
        Status.POSITIVE,
        pageable);

    // 2. Entity -> DTO 변환
    PageResponse<MailHResDto> pageResponseList = ModelMapperUtils.map(pagedMailHList, MailHResDto.class);

    // 3. 결과를 Map에 담아 반환
    data.put("data", pageResponseList);

    return data;
  }

  @Override
  public Map<String, Object> insertMailForAdmin(MailMReqDto mailM) {

    Map<String, Object> data = new HashMap<>();

    // 1. DTO -> Entity 변환
    MailM mailMEntity = mailM.toEntity(MailM.class);

    // 2. ID로 기존 엔티티 조회
    mailMRepository.findById(mailMEntity.getMailId())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .ifPresent(entity -> {
          throw new BusinessException(ErrorCode.OBJECT_IS_EXISTED);
        });

    // 3. DTO -> Entity 후 데이터 저장
    MailM savedMailMEntity = mailMRepository.save(mailMEntity);

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", savedMailMEntity.toDto(MailMResDto.class));
    return data;

  }

  @Override
  public Map<String, Object> updateMailForAdmin(MailMReqDto mailM) {

    Map<String, Object> data = new HashMap<>();

    // 1. DTO -> Entity 변환
    MailM mailMEntity = mailM.toEntity(MailM.class);

    // 2. ID로 기존 엔티티 조회
    mailMRepository.findById(mailMEntity.getMailId())
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 3. 엔티티 수정 & 저장(자동)
    MailM savedMailMEntity = mailMRepository.save(mailMEntity);

    // 4. Entity -> DTO 변환
    // 5. 결과를 Map에 담아 반환
    data.put("data", savedMailMEntity.toDto(MailMResDto.class));

    return data;

  }

  @Override
  public Map<String, Object> deleteMailForAdmin(String mailId) {
    Map<String, Object> data = new HashMap<>();

    // 1. ID로 인터페이스 조회(만약 인터페이스가 존재하지 않으면 NotFoundException 발생)
    MailM mailMEntity = mailMRepository.findById(mailId)
        .filter(entity -> entity.getSts().equals(Status.POSITIVE))
        .orElseThrow(() -> new NotFoundException(ErrorCode.OBJECT_NOT_FOUND));

    // 2. 상태를 'D'로 변경하여 삭제 처리(자동 save)
    mailMEntity.setSts(Status.NAGATIVE);

    // 3. 결과를 Map에 담아 반환
    data.put("data", "success");
    return data;

  }

}
