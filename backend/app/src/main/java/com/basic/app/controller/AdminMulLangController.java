/**
 * @파일명   : AdminMulLangController.java
 * @설명     : 다국어 메시지 관리 기능 제공 컨트롤러
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
import com.basic.app.entity.MulLang;
import com.basic.app.service.interfaces.MultiLangService;

@RestController
@RequestMapping("/admin/lang")
public class AdminMulLangController {

  @Autowired
  private MultiLangService multiLangService;

  /* [REQ_ADM_033] [화면 : 기준 정보 > 다국어 관리] [기능 : 다국어 리스트 조회] */
  @GetMapping
  public ResponseEntity<ApiResponse<Map<String, Object>>> findAllMulLangForAdmin() {
    Map<String, Object> results = multiLangService.findAllMulLangForAdmin();
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_034] [화면 : 기준 정보 > 다국어 관리] [기능 : 다국어 리스트 언어별 조회] */
  @GetMapping("/{langCd}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> findByMulLangForAdmin(@PathVariable String langCd) {
    Map<String, Object> results = multiLangService.findByMulLangForAdmin(langCd);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_035] [화면 : 기준 정보 > 다국어 관리] [기능 : 다국어 추가] */
  @PostMapping
  public ResponseEntity<ApiResponse<Map<String, Object>>> insertMulLangForAdmin(MulLang mulLang) {
    Map<String, Object> results = multiLangService.insertMulLangForAdmin(mulLang);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_036] [화면 : 기준 정보 > 다국어 관리] [기능 : 다국어 수정] */
  @PutMapping
  public ResponseEntity<ApiResponse<Map<String, Object>>> updateMulLangForAdmin(MulLang mulLang) {
    Map<String, Object> results = multiLangService.updateMulLangForAdmin(mulLang);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_037] [화면 : 기준 정보 > 다국어 관리] [기능 : 다국어 삭제] */
  @DeleteMapping("/{langCd}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> deleteMulLangForAdmin(@PathVariable String langCd) {
    Map<String, Object> results = multiLangService.deleteMulLangForAdmin(langCd);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }
}