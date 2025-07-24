/**
 * @파일명   : AdminMenuController.java
 * @설명     : 메뉴 및 메뉴 권한 관리 기능 제공 컨트롤러
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
import com.basic.app.dto.requestDto.MenuReqDto;
import com.basic.app.service.interfaces.MenuService;

@RestController
@RequestMapping("/admin/menu")
public class AdminMenuController {

  @Autowired
  private MenuService menuService;

  /* [REQ_ADM_038] [화면 : 권한 관리 > 메뉴 관리] [기능 : 메뉴 리스트 조회] */
  @GetMapping
  public ResponseEntity<ApiResponse<Map<String, Object>>> findAllMenuForAdmin() {
    Map<String, Object> results = menuService.findAllMenuForAdmin();
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_038_2] [화면 : 권한 관리 > 메뉴 관리] [기능 : 메뉴 조회] */
  @GetMapping("/{menuCd}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> findByMenuForAdmin(@PathVariable String menuCd) {
    Map<String, Object> results = menuService.findByMenuForAdmin(menuCd);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_039] [화면 : 권한 관리 > 메뉴 관리] [기능 : 메뉴 추가] */
  @PostMapping
  public ResponseEntity<ApiResponse<Map<String, Object>>> insertMenuForAdmin(
      @Validated(CreateGroup.class) MenuReqDto menu) {
    Map<String, Object> results = menuService.insertMenuForAdmin(menu);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_040] [화면 : 권한 관리 > 메뉴 관리] [기능 : 메뉴 수정] */
  @PutMapping
  public ResponseEntity<ApiResponse<Map<String, Object>>> updateMenuForAdmin(
      @Validated(UpdateGroup.class) MenuReqDto menu) {
    Map<String, Object> results = menuService.updateMenuForAdmin(menu);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_041] [화면 : 권한 관리 > 메뉴 관리] [기능 : 메뉴 삭제] */
  @DeleteMapping("/{menuCd}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> deleteMenuForAdmin(@PathVariable String menuCd) {
    Map<String, Object> results = menuService.deleteMenuForAdmin(menuCd);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }
}