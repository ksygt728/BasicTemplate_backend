package com.basic.app.service.interfaces;

import java.util.Map;

public interface AuthService {

  Map<String, Object> signInForKakao(String code);

  Map<String, Object> smsAuth(String phoneNum);

  Map<String, Object> smsAuthValidation(String phoneNum, String smsCode);

}
