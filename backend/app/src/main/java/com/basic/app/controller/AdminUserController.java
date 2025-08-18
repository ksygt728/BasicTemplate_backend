/**
 * @파일명   : AdminUserController.java
 * @설명     : 사용자 관리 기능 제공 컨트롤러
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basic.app.annotation.SwaggerCommonResponseApi;
import com.basic.app.api.ResponseApi;
import com.basic.app.api.ResponseApiSuccessForSwagger;
import com.basic.app.dto.group.UpdateGroup;
import com.basic.app.dto.requestDto.UserReqDto;
import com.basic.app.dto.responseDto.UserResDto;
import com.basic.app.service.interfaces.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/admin/user")
public class AdminUserController {

  @Autowired
  private UserService userService;

  /* [REQ_ADM_001] [화면 : 조직 관리 > 사용자 관리] [기능 : 사용자 정보 조회] */
  @Operation(summary = "[REQ_ADM_001] [화면 : 조직 관리 > 사용자 관리] [기능 : 사용자 정보 조회]", description = "사용자 정보 전체/조건부 조회 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = UserResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping // 전체 사용자 조회 또는 쿼리 파라미터로 조건부 조회
  public ResponseEntity<ResponseApi<Map<String, Object>>> findAllUserForAdmin() {
    Map<String, Object> data = userService.findAllUserForAdmin();
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_002] [화면 : 조직 관리 > 사용자 관리] [기능 : 사용자 정보 상세조회] */
  @Operation(summary = "[REQ_ADM_002] [화면 : 조직 관리 > 사용자 관리] [기능 : 사용자 정보 상세조회]", description = "사용자 정보 상세조회 기능 제공")
  @Parameter(name = "userId", description = "사용자 아이디", example = "user1234")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = UserResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/{userId}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findByUserForAdmin(@PathVariable String userId) {
    Map<String, Object> data = userService.findByUserForAdmin(userId);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_003] [화면 : 조직 관리 > 사용자 관리] [기능 : 사용자 정보 수정] */
  @Operation(summary = "[REQ_ADM_003] [화면 : 조직 관리 > 사용자 관리] [기능 : 사용자 정보 수정]", description = "사용자 정보 수정 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = UserResDto.class)))
  @SwaggerCommonResponseApi
  @PutMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> updateUserForAdmin(
      @Validated(UpdateGroup.class) UserReqDto user) {
    Map<String, Object> data = userService.updateUserForAdmin(user);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_004] [화면 : 조직 관리 > 사용자 관리] [기능 : 사용자 정보 삭제] */
  @Operation(summary = "[REQ_ADM_004] [화면 : 조직 관리 > 사용자 관리] [기능 : 사용자 정보 삭제]", description = "사용자 정보 삭제 기능 제공")
  @Parameter(name = "userId", description = "사용자 아이디", example = "user1234")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ResponseApiSuccessForSwagger.class)))
  @SwaggerCommonResponseApi
  @DeleteMapping("/{userId}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> deleteUserForAdmin(@PathVariable String userId) {
    Map<String, Object> data = userService.deleteUserForAdmin(userId);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }
}