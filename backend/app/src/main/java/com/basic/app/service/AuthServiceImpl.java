package com.basic.app.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.basic.app.exception.ErrorCode;
import com.basic.app.exception.customException.SystemErrorException;
import com.basic.app.service.interfaces.AuthService;
import com.basic.app.sms.SmsSendManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Transactional
@Service
public class AuthServiceImpl implements AuthService {

  @Value("${spring.kakao.api-key}")
  private String API_KEY;

  @Value("${spring.kakao.client-secret-key}")
  private String KAKAO_CLIENT_SECRET_KEY;

  @Value("${spring.kakao.redirect-uri}")
  private String KAAKOO_REDIRECT_URI;

  @Value("${spring.kakao.access-token-uri}")
  private String KAKAO_ACCESS_TOKEN_URI;

  @Autowired
  private SmsSendManager smsSendManager;

  private static final String SMS_AUTH_PREFIX = "smsAuth:";
  private static final long SMS_AUTH_EXPIRE_TIME = 180; // 3분

  @Autowired
  private RedisTemplate redisTemplate;

  @Override
  public Map<String, Object> signInForKakao(String code) {

    Map<String, Object> data = new HashMap<>();

    try {

      /* Step 2 : 카카오 Access Token 발급 */
      RestTemplate restTemplate = new RestTemplate();

      // Header 설정
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

      // POST Body 설정
      MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
      body.add("grant_type", "authorization_code");
      body.add("client_id", API_KEY); // REST API KEY
      body.add("client_secret", KAKAO_CLIENT_SECRET_KEY); // Redirect URI
      body.add("redirect_uri", KAAKOO_REDIRECT_URI); // Redirect URI
      body.add("code", code);
      body.add("scope", "talk_message,friends");

      HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

      // POST 요청 (올바른 방법)
      Map<String, Object> response = restTemplate.postForObject(
          KAKAO_ACCESS_TOKEN_URI,
          request,
          HashMap.class);

      data.put("data", response);

    } catch (Exception e) {
      throw new SystemErrorException(e, ErrorCode.KAKAO_AUTH_ERROR, "");
    }

    return data;
  }

  @Override
  public Map<String, Object> smsAuth(String phoneNum) {
    Map<String, Object> data = new HashMap<>();

    String code = String.valueOf((int) (Math.random() * 900000) + 100000);

    smsSendManager.sendSms("SMS_AUTH", phoneNum, Map.of("code", code)); // SMS 발송

    // Redis에 저장 (3분 TTL)
    redisTemplate.opsForValue()
        .set(SMS_AUTH_PREFIX + phoneNum, code, SMS_AUTH_EXPIRE_TIME, TimeUnit.SECONDS);

    return data;

  }

  @Override
  public Map<String, Object> smsAuthValidation(String phoneNum, String smsCode) {
    Map<String, Object> data = new HashMap<>();

    boolean isValid = false;
    try {
      String key = SMS_AUTH_PREFIX + phoneNum;
      String savedCode = redisTemplate.opsForValue().get(key).toString();

      if (savedCode != null && savedCode.equals(smsCode)) {
        redisTemplate.delete(key); // 일회성 사용 후 삭제
        isValid = true;
      }
    } catch (Exception e) {
      e.printStackTrace();
      isValid = false; // null인 경우 인증번호 만료
    }
    data.put("data", isValid);

    return data;

  }
}