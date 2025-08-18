/**
 * @파일명   : AdminMenuController.java
 * @설명     :  @PostMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> insertMenuForAdmin(
      @Validated(CreateGroup.class) MenuReqDto menu) {
    Map<String, Object> data = menuService.insertMenuForAdmin(menu);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data)); 메뉴 권한 관리 기능 제공 컨트롤러
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

import com.basic.app.annotation.SwaggerCommonResponseApi;
import com.basic.app.api.ResponseApi;
import com.basic.app.api.ResponseApiSuccessForSwagger;
import com.basic.app.dto.group.CreateGroup;
import com.basic.app.dto.group.UpdateGroup;
import com.basic.app.dto.requestDto.MenuReqDto;
import com.basic.app.dto.responseDto.MenuResDto;
import com.basic.app.service.interfaces.MenuService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/admin/menu")
public class AdminMenuController {

  @Autowired
  private MenuService menuService;

  /* [REQ_ADM_038] [화면 : 권한 관리 > 메뉴 관리] [기능 : 메뉴 리스트 조회] */
  @Operation(summary = "[REQ_ADM_038] [화면 : 권한 관리 > 메뉴 관리] [기능 : 메뉴 리스트 조회]", description = "메뉴 리스트 조회 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = MenuResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> findAllMenuForAdmin() {
    Map<String, Object> data = menuService.findAllMenuForAdmin();
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_038_2] [화면 : 권한 관리 > 메뉴 관리] [기능 : 메뉴 조회] */
  @Operation(summary = "[REQ_ADM_038_2] [화면 : 권한 관리 > 메뉴 관리] [기능 : 메뉴 조회]", description = "메뉴 조회 기능 제공")
  @Parameter(name = "menuCd", description = "메뉴 코드", example = "menu123")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = MenuResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/{menuCd}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findByMenuForAdmin(@PathVariable String menuCd) {
    Map<String, Object> data = menuService.findByMenuForAdmin(menuCd);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_039] [화면 : 권한 관리 > 메뉴 관리] [기능 : 메뉴 추가] */
  @Operation(summary = "[REQ_ADM_039] [화면 : 권한 관리 > 메뉴 관리] [기능 : 메뉴 추가]", description = "메뉴 추가 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = MenuResDto.class)))
  @SwaggerCommonResponseApi
  @PostMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> insertMenuForAdmin(
      @Validated(CreateGroup.class) MenuReqDto menu) {
    Map<String, Object> data = menuService.insertMenuForAdmin(menu);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_040] [화면 : 권한 관리 > 메뉴 관리] [기능 : 메뉴 수정] */
  @Operation(summary = "[REQ_ADM_040] [화면 : 권한 관리 > 메뉴 관리] [기능 : 메뉴 수정]", description = "메뉴 수정 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = MenuResDto.class)))
  @SwaggerCommonResponseApi
  @PutMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> updateMenuForAdmin(
      @Validated(UpdateGroup.class) MenuReqDto menu) {
    Map<String, Object> data = menuService.updateMenuForAdmin(menu);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_041] [화면 : 권한 관리 > 메뉴 관리] [기능 : 메뉴 삭제] */
  @Operation(summary = "[REQ_ADM_041] [화면 : 권한 관리 > 메뉴 관리] [기능 : 메뉴 삭제]", description = "메뉴 삭제 기능 제공")
  @Parameter(name = "menuCd", description = "메뉴 코드", example = "menu123")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ResponseApiSuccessForSwagger.class)))
  @SwaggerCommonResponseApi
  @DeleteMapping("/{menuCd}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> deleteMenuForAdmin(@PathVariable String menuCd) {
    Map<String, Object> data = menuService.deleteMenuForAdmin(menuCd);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }
}