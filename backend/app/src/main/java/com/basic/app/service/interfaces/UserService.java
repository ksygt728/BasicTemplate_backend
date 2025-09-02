package com.basic.app.service.interfaces;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.basic.app.dto.requestDto.UserReqDto;

public interface UserService {

  Map<String, Object> findAllUserForAdmin(UserReqDto userReqDto, Pageable pageable);

  Map<String, Object> findByUserForAdmin(String userId);

  Map<String, Object> updateUserForAdmin(UserReqDto user);

  Map<String, Object> deleteUserForAdmin(String userId);

}
