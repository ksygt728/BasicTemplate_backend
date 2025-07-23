
/**
 * @파일명   : AdminCodeController.java
 * @설명     : 공통 코드 관리 기능 제공 컨트롤러
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
import com.basic.app.entity.ComCodeD;
import com.basic.app.entity.ComCodeM;
import com.basic.app.entity.ComCodeT;
import com.basic.app.service.interfaces.CodeService;

@RestController
@RequestMapping("/admin/code")
public class AdminCodeController {

  @Autowired
  private CodeService codeService;

  /* [REQ_ADM_009_1] [화면 : 기준 정보 > 코드 관리] [기능 : 그뤂 코드 리스트 조회] */
  @GetMapping("/group")
  public ResponseEntity<ApiResponse<Map<String, Object>>> findAllGroupCodeForAdmin() {
    Map<String, Object> results = codeService.findAllGroupCodeForAdmin();
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_009_2] [화면 : 기준 정보 > 코드 관리] [기능 : 그뤂 코드 조회] */
  @GetMapping("/group/{grpCd}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> findByGroupCodeForAdmin(@PathVariable String grpCd) {
    Map<String, Object> results = codeService.findByGroupCodeForAdmin(grpCd);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_010] [화면 : 기준 정보 > 코드 관리] [기능 : 그뤂 코드 추가] */
  @PostMapping("/group")
  public ResponseEntity<ApiResponse<Map<String, Object>>> insertGroupCodeForAdmin(ComCodeM comCodeM) {
    Map<String, Object> results = codeService.insertGroupCodeForAdmin(comCodeM);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_011] [화면 : 기준 정보 > 코드 관리] [기능 : 그뤂 코드 수정] */
  @PutMapping("/group")
  public ResponseEntity<ApiResponse<Map<String, Object>>> updateGroupCodeForAdmin(ComCodeM comCodeM) {
    Map<String, Object> results = codeService.updateGroupCodeForAdmin(comCodeM);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_012] [화면 : 기준 정보 > 코드 관리] [기능 : 그뤂 코드 삭제] */
  @DeleteMapping("/group/{grpCd}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> deleteGroupCodeForAdmin(@PathVariable String grpCd) {
    Map<String, Object> results = codeService.deleteGroupCodeForAdmin(grpCd);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_013] [화면 : 기준 정보 > 코드 관리] [기능 : 속성 코드 리스트 조회] */
  @GetMapping("/attribute")
  public ResponseEntity<ApiResponse<Map<String, Object>>> findAllAttrCodeForAdmin() {
    Map<String, Object> results = codeService.findAllAttrCodeForAdmin();
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_013_2] [화면 : 기준 정보 > 코드 관리] [기능 : 속성 코드 조회] */
  @GetMapping("/attribute/{attrCd}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> findByAttrCodeForAdmin(@PathVariable String attrCd) {
    Map<String, Object> results = codeService.findByAttrCodeForAdmin(attrCd);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_014] [화면 : 기준 정보 > 코드 관리] [기능 : 속성 코드 추가] */
  @PostMapping("/attribute")
  public ResponseEntity<ApiResponse<Map<String, Object>>> insertAttrCodeForAdmin(ComCodeT comCodeT) {
    Map<String, Object> results = codeService.insertAttrCodeForAdmin(comCodeT);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_015] [화면 : 기준 정보 > 코드 관리] [기능 : 속성 코드 수정] */
  @PutMapping("/attribute")
  public ResponseEntity<ApiResponse<Map<String, Object>>> updateAttrCodeForAdmin(ComCodeT comCodeT) {
    Map<String, Object> results = codeService.updateAttrCodeForAdmin(comCodeT);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_016] [화면 : 기준 정보 > 코드 관리] [기능 : 속성 코드 삭제] */
  @DeleteMapping("/attribute/{attrCd}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> deleteAttrCodeForAdmin(@PathVariable String attrCd) {
    Map<String, Object> results = codeService.deleteAttrCodeForAdmin(attrCd);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_017] [화면 : 기준 정보 > 코드 관리] [기능 : 상세코드 리스트 조회] */
  @GetMapping("/detail")
  public ResponseEntity<ApiResponse<Map<String, Object>>> findAllDetailCodeForAdmin() {
    Map<String, Object> results = codeService.findAllDetailCodeForAdmin();
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_017_2] [화면 : 기준 정보 > 코드 관리] [기능 : 상세코드 조회] */
  @GetMapping("/detail/{dtlCd}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> findByDetailCodeForAdmin(@PathVariable String dtlCd) {
    Map<String, Object> results = codeService.findByDetailCodeForAdmin(dtlCd);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_018] [화면 : 기준 정보 > 코드 관리] [기능 : 상세코드 추가] */
  @PostMapping("/detail")
  public ResponseEntity<ApiResponse<Map<String, Object>>> insertDetailCodeForAdmin(ComCodeD comCodeD) {
    Map<String, Object> results = codeService.insertDetailCodeForAdmin(comCodeD);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_019] [화면 : 기준 정보 > 코드 관리] [기능 : 상세코드 수정] */
  @PutMapping("/detail")
  public ResponseEntity<ApiResponse<Map<String, Object>>> updateDetailCodeForAdmin(ComCodeD comCodeD) {
    Map<String, Object> results = codeService.updateDetailCodeForAdmin(comCodeD);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_020] [화면 : 기준 정보 > 코드 관리] [기능 : 상세코드 삭제] */
  @DeleteMapping("/detail/{dtlCd}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> deleteDetailCodeForAdmin(@PathVariable String dtlCd) {
    Map<String, Object> results = codeService.deleteDetailCodeForAdmin(dtlCd);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }
}