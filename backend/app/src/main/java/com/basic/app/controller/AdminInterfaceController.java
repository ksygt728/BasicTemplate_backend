
/**
 * @파일명   : AdminInterfaceController.java
 * @설명     : 외부 시스템 인터페이스 관리 기능 제공 컨트롤러
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
import com.basic.app.dto.requestDto.InterfaceReqDto;
import com.basic.app.service.interfaces.InterfaceService;

@RestController
@RequestMapping("/admin/interface")
public class AdminInterfaceController {

  @Autowired
  private InterfaceService interfaceService;

  /* [REQ_ADM_021] [화면 : 기준 정보 > 인터페이스 관리] [기능 : 인터페이스 기준정보 리스트 조회] */
  @GetMapping
  public ResponseEntity<ApiResponse<Map<String, Object>>> findAllInterfaceForAdmin() {
    Map<String, Object> results = interfaceService.findAllInterfaceForAdmin();
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_021_2] [화면 : 기준 정보 > 인터페이스 관리] [기능 : 인터페이스 기준정보 조회] */
  @GetMapping("/{ifId}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> findByInterfaceForAdmin(@PathVariable String ifId) {
    Map<String, Object> results = interfaceService.findByInterfaceForAdmin(ifId);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_022] [화면 : 기준 정보 > 인터페이스 관리] [기능 : 인터페이스 직접 실행] */
  @PostMapping("/execute/{ifc}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> executeInterfaceForAdmin(InterfaceReqDto ifc) {
    Map<String, Object> results = interfaceService.executeInterfaceForAdmin(ifc);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_023] [화면 : 기준 정보 > 인터페이스 관리] [기능 : 이력조회] */
  @GetMapping("/history/{ifId}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> findByInterfaceHistoryForAdmin(@PathVariable String ifId) {
    Map<String, Object> results = interfaceService.findByInterfaceHistoryForAdmin(ifId);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_024] [화면 : 기준 정보 > 인터페이스 관리] [기능 : 인터페이스 추가] */
  @PostMapping
  public ResponseEntity<ApiResponse<Map<String, Object>>> insertInterfaceForAdmin(
      @Validated(CreateGroup.class) InterfaceReqDto ifc) {
    Map<String, Object> results = interfaceService.insertInterfaceForAdmin(ifc);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_025] [화면 : 기준 정보 > 인터페이스 관리] [기능 : 인터페이스 수정] */
  @PutMapping
  public ResponseEntity<ApiResponse<Map<String, Object>>> updateInterfaceForAdmin(
      @Validated(UpdateGroup.class) InterfaceReqDto ifc) {
    Map<String, Object> results = interfaceService.updateInterfaceForAdmin(ifc);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_026] [화면 : 기준 정보 > 인터페이스 관리] [기능 : 인터페이스 삭제] */
  @DeleteMapping("/{ifId}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> deleteInterfaceForAdmin(@PathVariable String ifId) {
    Map<String, Object> results = interfaceService.deleteInterfaceForAdmin(ifId);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }
}