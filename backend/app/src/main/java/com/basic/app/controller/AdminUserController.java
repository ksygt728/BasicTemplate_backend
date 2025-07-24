
/**
 * @파일명   : AdminUserController.java
 * @설명     : 사용자 관리 기능 제공 컨트롤러
 * @작성자   : 김승연
 * @작성일   : 2025.07.23
 * @변경이력 :
 *   2025.07.23     김승연       최초 생성
 */

package com.basic.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basic.app.custom.ApiResponse;
import com.basic.app.dto.requestDto.UserReqDto;
import com.basic.app.service.interfaces.UserService;

@RestController
@RequestMapping("/admin/user")
public class AdminUserController {

  @Autowired
  private UserService userService;

  /* [REQ_ADM_001] [화면 : 조직 관리 > 사용자 관리] [기능 : 사용자 정보 조회] */
  @GetMapping // 전체 사용자 조회 또는 쿼리 파라미터로 조건부 조회
  public ResponseEntity<ApiResponse<Map<String, Object>>> findAllUserForAdmin() {
    Map<String, Object> results = userService.findAllUserForAdmin();
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_002] [화면 : 조직 관리 > 사용자 관리] [기능 : 사용자 정보 상세조회] */
  @GetMapping("/{userId}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> findByUserForAdmin(@PathVariable String userId) {
    Map<String, Object> results = userService.findByUserForAdmin(userId);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_003] [화면 : 조직 관리 > 사용자 관리] [기능 : 사용자 정보 수정] */
  @PutMapping
  public ResponseEntity<ApiResponse<Map<String, Object>>> updateUserForAdmin(UserReqDto user) {
    Map<String, Object> results = userService.updateUserForAdmin(user);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_004] [화면 : 조직 관리 > 사용자 관리] [기능 : 사용자 정보 삭제] */
  @DeleteMapping("/{userId}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> deleteUserForAdmin(@PathVariable String userId) {
    Map<String, Object> results = userService.deleteUserForAdmin(userId);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }
}