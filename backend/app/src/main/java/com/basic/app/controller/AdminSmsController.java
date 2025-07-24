/**
 * @파일명   : AdminSmsController.java
 * @설명     : SMS 템플릿 및 발송 관리 기능 제공 컨트롤러
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basic.app.custom.ApiResponse;
import com.basic.app.dto.requestDto.SmsMReqDto;
import com.basic.app.service.interfaces.SmsService;

@RestController
@RequestMapping("/admin/sms")
public class AdminSmsController {

  @Autowired
  private SmsService smsService;

  /* [REQ_ADM_067] [화면 : 시스템 관리 > SMS 발송 로그] [기능 : SMS 리스트 조회] */
  @GetMapping
  public ResponseEntity<ApiResponse<Map<String, Object>>> findAllSmsForAdmin() {
    Map<String, Object> results = smsService.findAllSmsForAdmin();
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_067_2] [화면 : 시스템 관리 > SMS 발송 로그] [기능 : SMS 조회] */
  @GetMapping("/{smsId}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> findBySmsForAdmin(@PathVariable String smsId) {
    Map<String, Object> results = smsService.findBySmsForAdmin(smsId);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_071] [화면 : 시스템 관리 > SMS 발송 로그] [기능 : SMS 추가] */
  @PostMapping
  public ResponseEntity<ApiResponse<Map<String, Object>>> insertSmsForAdmin(SmsMReqDto smsM) {
    Map<String, Object> results = smsService.insertSmsForAdmin(smsM);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_072] [화면 : 시스템 관리 > SMS 발송 로그] [기능 : SMS 수정] */
  @PutMapping
  public ResponseEntity<ApiResponse<Map<String, Object>>> updateSmsForAdmin(SmsMReqDto smsM) {
    Map<String, Object> results = smsService.updateSmsForAdmin(smsM);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_073] [화면 : 시스템 관리 > SMS 발송 로그] [기능 : SMS 삭제] */
  @DeleteMapping("/{smsId}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> deleteSmsForAdmin(@PathVariable String smsId) {
    Map<String, Object> results = smsService.deleteSmsForAdmin(smsId);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_068] [화면 : 시스템 관리 > SMS 발송 로그] [기능 : SMS 이력 조회] */
  @GetMapping("/history/{smsId}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> findBySmsHistoryForAdmin(@PathVariable String smsId) {
    Map<String, Object> results = smsService.findBySmsHistoryForAdmin(smsId);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* 사용 X */
  /* [REQ_ADM_069] [화면 : 시스템 관리 > SMS 발송 로그] [기능 : SMS 템플릿 조회] */
  // @GetMapping("/templates")
  // public ResponseEntity<ApiResponse<Map<String, Object>>> findSmsTemplates() {
  // Map<String, Object> results = smsService.findSmsTemplates();
  // return
  // ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  // }

  /* 사용 X */
  /* [REQ_ADM_070] [화면 : 시스템 관리 > SMS 발송 로그] [기능 : SMS 템플릿 조회] */
  // @GetMapping("/templates/{templateId}")
  // public ResponseEntity<ApiResponse<Map<String, Object>>> findSmsTemplate(
  // @PathVariable ResponseEntity<ApiResponse<Map<String, Object>>> templateId) {
  // Map<String, Object> results = smsService.findSmsTemplate(templateId);
  // return
  // ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  // }

}