package com.basic.app.service.interfaces;

import java.util.Map;

public interface AuthService {

  Map<String, Object> signInForKakao(String code);

}
