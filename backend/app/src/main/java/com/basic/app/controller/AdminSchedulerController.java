/**
 * @파일명   : AdminSchedulerController.java
 * @설명     : 스케줄러 작업 관리 기능 제공 컨트롤러
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basic.app.custom.ApiResponse;
import com.basic.app.dto.group.CreateGroup;
import com.basic.app.dto.group.UpdateGroup;
import com.basic.app.dto.requestDto.ScheMReqDto;
import com.basic.app.service.interfaces.SchedulerService;

@RestController
@RequestMapping("/admin/scheduler")
public class AdminSchedulerController {

  @Autowired
  private SchedulerService schedulerService;

  /* [REQ_ADM_074] [화면 : 시스템 관리 > 스케쥴러 관리] [기능 : 스케쥴러 리스트 조회] */
  @GetMapping
  public ResponseEntity<ApiResponse<Map<String, Object>>> findAllSchedulerForAdmin() {
    Map<String, Object> data = schedulerService.findAllSchedulerForAdmin();
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(data));
  }

  /* [REQ_ADM_074_2] [화면 : 시스템 관리 > 스케쥴러 관리] [기능 : 스케쥴러 조회] */
  @GetMapping("/{scheId}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> findBySchedulerForAdmin(@PathVariable String scheId) {
    Map<String, Object> data = schedulerService.findBySchedulerForAdmin(scheId);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(data));
  }

  /* [REQ_ADM_075] [화면 : 시스템 관리 > 스케쥴러 관리] [기능 : 스케쥴러 이력 조회] */
  @GetMapping("/history/{scheId}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> findBySchedulerHistoryForAdmin(@PathVariable String scheId) {
    Map<String, Object> data = schedulerService.findBySchedulerHistoryForAdmin(scheId);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(data));
  }

  /* 사용 X */
  /* [REQ_ADM_076] [화면 : 시스템 관리 > 스케쥴러 관리] [기능 : 스케쥴러 템플릿 조회] */
  // @GetMapping("/templates")
  // public ResponseEntity<ApiResponse<Map<String, Object>>>
  // findSchedulerTemplates() {
  // Map<String, Object> data = schedulerService.findSchedulerTemplates();
  // return
  // ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(data));
  // }

  /* [REQ_ADM_077] [화면 : 시스템 관리 > 스케쥴러 관리] [기능 : 스케쥴러 직접실행] */
  @PostMapping("/execute/{scheId}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> executeSchedulerForAdmin(@PathVariable String scheId) {
    Map<String, Object> data = schedulerService.executeSchedulerForAdmin(scheId);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(data));
  }

  /* [REQ_ADM_078] [화면 : 시스템 관리 > 스케쥴러 관리] [기능 : 스케쥴러 추가] */
  @PostMapping
  public ResponseEntity<ApiResponse<Map<String, Object>>> insertSchedulerForAdmin(
      @Validated(CreateGroup.class) ScheMReqDto scheM) {
    Map<String, Object> data = schedulerService.insertSchedulerForAdmin(scheM);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(data));
  }

  /* [REQ_ADM_079] [화면 : 시스템 관리 > 스케쥴러 관리] [기능 : 스케쥴러 수정] */
  @PutMapping
  public ResponseEntity<ApiResponse<Map<String, Object>>> updateSchedulerForAdmin(
      @Validated(UpdateGroup.class) ScheMReqDto scheM) {
    Map<String, Object> data = schedulerService.updateSchedulerForAdmin(scheM);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(data));
  }

  /* [REQ_ADM_080] [화면 : 시스템 관리 > 스케쥴러 관리] [기능 : 스케쥴러 삭제] */
  @DeleteMapping("/{schedulerId}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> deleteSchedulerForAdmin(ScheMReqDto scheM) {
    Map<String, Object> data = schedulerService.deleteSchedulerForAdmin(scheM);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(data));
  }
}