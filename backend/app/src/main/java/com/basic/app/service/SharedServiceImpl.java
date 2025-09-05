package com.basic.app.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basic.app.service.interfaces.SharedService;
import com.basic.app.service.specialService.MessageSource;

import lombok.extern.log4j.Log4j2;

/**
 * @파일명 : SharedServiceImpl.java
 * @설명 : 공통 서비스 구현체 (다국어 메시지 조회)
 * @작성자 : 김승연
 * @작성일 : 2025.09.05
 * @변경이력 :
 *       2025.09.05 김승연 최초 생성
 */
@Log4j2
@Transactional
@Service
public class SharedServiceImpl implements SharedService {

  @Autowired
  private MessageSource messageSource;

  /**
   * @기능 : 다국어 메시지 목록 조회
   * @param localeText 로케일 텍스트 (언어 코드)
   * @return 다국어 메시지 목록이 담긴 Map
   */
  @Override
  public Map<String, Object> getMulLangList(String localeText) {

    Map<String, String> messages = messageSource.getMessageList(localeText);

    return Map.of("data", messages);

  }

}
