
/**
 * @파일명   : AdminBbsController.java
 * @설명     : 게시판 관리 기능 제공 컨트롤러
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
import com.basic.app.dto.requestDto.BbsReqDto;
import com.basic.app.service.interfaces.BbsService;

@RestController
@RequestMapping("/admin/bbs")
public class AdminBbsController {

  @Autowired
  private BbsService bbsService;

  /* [REQ_ADM_081] [화면 : 시스템 관리 > 메뉴얼 관리] [기능 : 메뉴얼 리스트 조회] */
  @GetMapping
  public ResponseEntity<ApiResponse<Map<String, Object>>> findAllBbsForAdmin() {
    Map<String, Object> results = bbsService.findAllBbsForAdmin();
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_082] [화면 : 시스템 관리 > 메뉴얼 관리] [기능 : 메뉴얼 상세 조회] */
  @GetMapping("/{bbsId}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> findByBbsForAdmin(@PathVariable String bbsId) {
    Map<String, Object> results = bbsService.findByBbsForAdmin(bbsId);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_083] [화면 : 시스템 관리 > 메뉴얼 관리] [기능 : 메뉴얼 추가] */
  @PostMapping
  public ResponseEntity<ApiResponse<Map<String, Object>>> insertBbsForAdmin(
      @Validated(CreateGroup.class) BbsReqDto bbs) {
    Map<String, Object> results = bbsService.insertBbsForAdmin(bbs);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));

  }

  /* [REQ_ADM_0834] [화면 : 시스템 관리 > 메뉴얼 관리] [기능 : 메뉴얼 수정] */
  @PutMapping
  public ResponseEntity<ApiResponse<Map<String, Object>>> updateBbsForAdmin(
      @Validated(UpdateGroup.class) BbsReqDto bbs) {
    Map<String, Object> results = bbsService.updateBbsForAdmin(bbs);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));

  }

  /* [REQ_ADM_085] [화면 : 시스템 관리 > 메뉴얼 관리] [기능 : 메뉴얼 삭제] */
  @DeleteMapping("/{bbsId}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> deleteBbsForAdmin(@PathVariable String bbsId) {
    Map<String, Object> results = bbsService.deleteBbsForAdmin(bbsId);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));

  }
}