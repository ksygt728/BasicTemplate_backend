package com.basic.app.service.interfaces;

import java.util.Map;

import com.basic.app.dto.requestDto.UserReqDto;
import com.basic.app.dto.requestDto.specialDto.AuthReqDto;

public interface AuthService {

  Map<String, Object> signInForKakao(String code);

  Map<String, Object> signUp(UserReqDto userReqDto);

  Map<String, Object> signIn(AuthReqDto user);

}
