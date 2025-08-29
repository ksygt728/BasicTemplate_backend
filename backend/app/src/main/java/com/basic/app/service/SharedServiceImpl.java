package com.basic.app.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basic.app.service.interfaces.SharedService;
import com.basic.app.service.specialService.MessageSource;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Transactional
@Service
public class SharedServiceImpl implements SharedService {

  @Autowired
  private MessageSource messageSource;

  @Override
  public Map<String, Object> getMulLangList(String localeText) {

    Map<String, String> messages = messageSource.getMessageList(localeText);

    return Map.of("data", messages);

  }

}
