/**
 * @파일명   : AdminLogController.java
 * @설명     : 시스템 로그(접속, 오류 등) 관리 기능 제공 컨트롤러
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basic.app.api.ApiResponse;
import com.basic.app.service.interfaces.LogService;

@RestController
@RequestMapping("/admin/log") // Logs 및 Errors는 admin 하위에 직접 배치
public class AdminLogController {
  @Autowired
  private LogService logService;

  /* [REQ_ADM_064] [화면 : 시스템 관리 > 사용자 접속 로그] [기능 : 사용자 접속로그 리스트 조회] */
  @GetMapping("/api-log")
  public ResponseEntity<ApiResponse<Map<String, Object>>> findAllAccessLogForAdmin() {
    Map<String, Object> data = logService.findAllAccessLogForAdmin();
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(data));
  }

  /* [REQ_ADM_064_2] [화면 : 시스템 관리 > 사용자 접속 로그] [기능 : 사용자 접속로그 조회] */
  @GetMapping("/api-log/{logAct}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> findByAccessLogForAdmin(@PathVariable String logId) {
    Map<String, Object> data = logService.findByAccessLogForAdmin(logId);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(data));
  }

  /* [REQ_ADM_065] [화면 : 시스템 관리 > Error 관리] [기능 : 에러 리스트 조회] */
  @GetMapping("/error-log")
  public ResponseEntity<ApiResponse<Map<String, Object>>> findAllErrorLogForAdmin() {
    Map<String, Object> data = logService.findAllErrorLogForAdmin();
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(data));
  }

  /* [REQ_ADM_066] [화면 : 시스템 관리 > Error 관리] [기능 : 에러 상세정보 조회] */
  @GetMapping("/error-log/{errId}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> findByErrorLogForAdmin(@PathVariable String errId) {
    Map<String, Object> data = logService.findByErrorLogForAdmin(errId);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(data));
  }
}