package com.basic.app.service.specialService;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.basic.app.repository.MulLangRepository;
import com.basic.app.util.Status;

import jakarta.annotation.PostConstruct;

@Component
public class MessageSource extends AbstractMessageSource {

  private static final String CACHE_PREFIX = "mul_lang:";

  @Autowired
  private MulLangRepository mulLangRepository;

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  // 초기 로딩
  @PostConstruct
  public void loadMessages() {
    // 1. 기존 Redis 캐시 삭제
    Set<String> keys = redisTemplate.keys("mul_lang:*");
    if (keys != null && !keys.isEmpty()) {
      redisTemplate.delete(keys);
    }

    mulLangRepository.findAll().stream()
        .filter(entity -> entity.getSts().equals(Status.POSITIVE) && entity.getUseYn().equals("Y"))
        // .filter(entity -> entity.getMulLangId().getLangType().equals(langType))
        .toList()
        .forEach(lang -> {
          String key = CACHE_PREFIX + lang.getMulLangId().getLangType() + "|" + lang.getMulLangId().getLangGubun() + "."
              + lang.getMulLangId().getLangCd(); // e.g. ko|err.1001
          redisTemplate.opsForValue().set(key, lang.getLangNm()); // Redis 캐시에 저장
        });
  }

  // API 호출 시 캐시 갱신
  public void reloadCache() {

    loadMessages();
  }

  // Front End에서 실제 사용할 메세지 항목 대상
  public Map<String, String> getMessageList(String localeText) {

    Map<String, String> messages = new LinkedHashMap<>();
    Set<String> keys = redisTemplate.keys(CACHE_PREFIX + localeText + "|*");
    if (keys != null) {
      for (String key : keys) {
        String value = redisTemplate.opsForValue().get(key);
        if (value != null) {
          // key에서 prefix와 localeText를 제거하여 code만 추출
          String code = key.substring((CACHE_PREFIX + localeText + "|").length());
          messages.put(code, value);
        }
      }
    }
    return messages;
  }

  // Back End에서 사용할 메세지 대상
  public String getMessage(String code, String localeText, Object[] args) {

    Locale locale = null;

    if (localeText == null || localeText.isEmpty() || localeText.equals("ko"))
      locale = Locale.KOREAN;
    else if (localeText.equals("en"))
      locale = Locale.ENGLISH;
    else if (localeText.equals("zh"))
      locale = Locale.CHINA;
    else
      locale = Locale.KOREAN;

    MessageFormat messageFormat = resolveCode(code, locale);
    if (messageFormat != null) {
      return messageFormat.format(args);
    } else {
      return code; // 메시지를 찾지 못한 경우 코드 자체를 반환
    }
  }

  @Override
  protected MessageFormat resolveCode(String code, Locale locale) {
    String key = CACHE_PREFIX + locale.getLanguage() + "|" + code;
    String message = redisTemplate.opsForValue().get(key);
    return message != null ? new MessageFormat(message, locale) : null;
  }
}