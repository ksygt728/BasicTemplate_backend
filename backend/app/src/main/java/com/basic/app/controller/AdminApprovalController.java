
/**
 * @파일명   : AdminApprovalController.java
 * @설명     : 결재 관련 기능 제공 컨트롤러
 * @작성자   : 김승연
 * @작성일   : 2025.07.23
 * @변경이력 :
 *     날짜           이름        변경내용
 *   ----------     --------    -------------------------------
 *   2025.07.23     김승연       최초 생성
 */
package com.basic.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basic.app.api.ApiResponse;
import com.basic.app.service.interfaces.ApprovalService;

@RestController
@RequestMapping("/admin/approval")
public class AdminApprovalController {

  @Autowired
  private ApprovalService approvalService;

  /* [REQ_ADM_086] [화면 : 시스템 관리 > 결재 관리] [기능 : 예정] */
  @GetMapping("/planned") // 예정된 기능이므로 임시 경로를 지정
  public ResponseEntity<ApiResponse<Map<String, Object>>> findPlannedApprovals() {
    Map<String, Object> data = approvalService.findPlannedApprovals();
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(data));
  }
}