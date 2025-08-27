/**
 * @파일명   : AdminRoleController.java
 * @설명     : 사용자 권한 및 역할 관리 기능 제공 컨트롤러
 * @작성자   : 김승연
 * @작성일   : 2025.07.23
 * @변경이력 :
 *   2025.07.23     김승연       최초 생성
 */

package com.basic.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basic.app.annotation.SwaggerCommonResponseApi;
import com.basic.app.api.ResponseApi;
import com.basic.app.api.ResponseApiSuccessForSwagger;
import com.basic.app.dto.group.CreateGroup;
import com.basic.app.dto.group.UpdateGroup;
import com.basic.app.dto.requestDto.RoleMenuReqDto;
import com.basic.app.dto.requestDto.RoleReqDto;
import com.basic.app.dto.requestDto.RoleUserReqDto;
import com.basic.app.dto.requestDto.UserReqDto;
import com.basic.app.dto.responseDto.RoleMenuResDto;
import com.basic.app.dto.responseDto.RoleResDto;
import com.basic.app.dto.responseDto.RoleUserResDto;
import com.basic.app.service.interfaces.RoleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "AdminRoleController", description = "권한 API")
@RestController
@RequestMapping("/admin/role")
public class AdminRoleController {

  @Autowired
  private RoleService roleService;

  /* [REQ_ADM_042] [화면 : 권한 관리 > 권한 관리(Role)] [기능 : 권한 리스트 조회] */
  @Operation(summary = "[REQ_ADM_042] [화면 : 권한 관리 > 권한 관리(Role)] [기능 : 권한 리스트 조회]", description = "권한 리스트 조회 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = RoleResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/search")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findAllRoleForAdmin(
      RoleReqDto roleReqDto,
      @PageableDefault(page = 0, size = 2000, sort = "roleCd", direction = Sort.Direction.ASC) Pageable pageable) {

    Map<String, Object> data = roleService.findAllRoleForAdmin(roleReqDto, pageable);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_042_2] [화면 : 권한 관리 > 권한 관리(Role)] [기능 : 권한 조회] */
  @Operation(summary = "[REQ_ADM_042_2] [화면 : 권한 관리 > 권한 관리(Role)] [기능 : 권한 조회]", description = "권한 조회 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = RoleResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/{roleCd}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findByRoleForAdmin(@PathVariable String roleCd) {
    Map<String, Object> data = roleService.findByRoleForAdmin(roleCd);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_045] [화면 : 권한 관리 > 권한 관리(Role)] [기능 : 권한 추가] */
  @Operation(summary = "[REQ_ADM_045] [화면 : 권한 관리 > 권한 관리(Role)] [기능 : 권한 추가]", description = "권한 추가 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = RoleResDto.class)))
  @SwaggerCommonResponseApi
  @PostMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> insertRoleForAdmin(
      @Validated(CreateGroup.class) RoleReqDto roleReqDto) {
    Map<String, Object> data = roleService.insertRoleForAdmin(roleReqDto);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_046] [화면 : 권한 관리 > 권한 관리(Role)] [기능 : 권한 수정] */
  @Operation(summary = "[REQ_ADM_046] [화면 : 권한 관리 > 권한 관리(Role)] [기능 : 권한 수정]", description = "권한 수정 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = RoleResDto.class)))
  @SwaggerCommonResponseApi
  @PutMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> updateRoleForAdmin(
      @Validated(UpdateGroup.class) RoleReqDto roleReqDto) {
    Map<String, Object> data = roleService.updateRoleForAdmin(roleReqDto);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_047] [화면 : 권한 관리 > 권한 관리(Role)] [기능 : 권한 삭제] */
  @Operation(summary = "[REQ_ADM_047] [화면 : 권한 관리 > 권한 관리(Role)] [기능 : 권한 삭제]", description = "권한 삭제 기능 제공")
  @Parameter(name = "roleCd", description = "권한 코드", example = "ROLE_ADMIN")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ResponseApiSuccessForSwagger.class)))
  @SwaggerCommonResponseApi
  @DeleteMapping("/{roleCd}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> deleteRoleForAdmin(@PathVariable String roleCd) {
    Map<String, Object> data = roleService.deleteRoleForAdmin(roleCd);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_043] [화면 : 권한 관리 > 권한 관리(Role)] [기능 : 권한별 메뉴 리스트 조회] */
  @Operation(summary = "[REQ_ADM_043] [화면 : 권한 관리 > 권한 관리(Role)] [기능 : 권한별 메뉴 리스트 조회]", description = "권한별 메뉴 리스트 조회 기능 제공")
  @Parameter(name = "roleCd", description = "권한 코드", example = "ROLE_ADMIN")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = RoleMenuResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/role-menu/{roleCd}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findByRoleMenuForAdmin(@PathVariable String roleCd) {
    Map<String, Object> data = roleService.findByRoleMenuForAdmin(roleCd);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_044] [화면 : 권한 관리 > 권한 관리(Role)] [기능 : 권한별 메뉴 리스트 수정] */
  @Operation(summary = "[REQ_ADM_044] [화면 : 권한 관리 > 권한 관리(Role)] [기능 : 권한별 메뉴 리스트 수정]", description = "권한별 메뉴 리스트 수정 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = RoleMenuResDto.class)))
  @SwaggerCommonResponseApi
  @PutMapping("/role-menu")
  public ResponseEntity<ResponseApi<Map<String, Object>>> updateRoleMenuForAdmin(
      @RequestBody @Validated(UpdateGroup.class) List<RoleMenuReqDto> roleMenu) {
    Map<String, Object> data = roleService.updateRoleMenuForAdmin(roleMenu);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_048] [화면 : 권한 관리 > 사용자별 권한] [기능 : 사용자 리스트 조회] */
  @Operation(summary = "[REQ_ADM_048] [화면 : 권한 관리 > 사용자별 권한] [기능 : 사용자 리스트 조회]", description = "사용자별 권한 사용자 리스트 조회 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = RoleUserResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/user")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findAllRoleUserForAdmin(UserReqDto userReqDto,
      @PageableDefault(page = 0, size = 2000, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {
    Map<String, Object> data = roleService.findAllRoleUserForAdmin(userReqDto, pageable);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_049] [화면 : 권한 관리 > 사용자별 권한] [기능 : 사용자별 권한 조회] */
  @Operation(summary = "[REQ_ADM_049] [화면 : 권한 관리 > 사용자별 권한] [기능 : 사용자별 권한 조회]", description = "사용자별 권한 조회 기능 제공")
  @Parameter(name = "userId", description = "사용자 아이디", example = "user123")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = RoleUserResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/role-user/{userId}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findByRoleUserForAdmin(@PathVariable String userId,
      @PageableDefault(page = 0, size = 2000, sort = "roleUserId.roleCd", direction = Sort.Direction.ASC) Pageable pageable) {
    Map<String, Object> data = roleService.findByRoleUserForAdmin(userId, pageable);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_050] [화면 : 권한 관리 > 사용자별 권한] [기능 : 사용자별 권한 추가] */
  @Operation(summary = "[REQ_ADM_050] [화면 : 권한 관리 > 사용자별 권한] [기능 : 사용자별 권한 추가]", description = "사용자별 권한 추가 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = RoleUserResDto.class)))
  @SwaggerCommonResponseApi
  @PostMapping("/role-user")
  public ResponseEntity<ResponseApi<Map<String, Object>>> insertRoleUserForAdmin(
      @RequestBody @Validated(CreateGroup.class) List<RoleUserReqDto> roleUser) {
    Map<String, Object> data = roleService.insertRoleUserForAdmin(roleUser);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_052] [화면 : 권한 관리 > 사용자별 권한] [기능 : 사용자별 권한 삭제] */
  @Operation(summary = "[REQ_ADM_052] [화면 : 권한 관리 > 사용자별 권한] [기능 : 사용자별 권한 삭제]", description = "사용자별 권한 삭제 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ResponseApiSuccessForSwagger.class)))
  @SwaggerCommonResponseApi
  @DeleteMapping("/role-user")
  public ResponseEntity<ResponseApi<Map<String, Object>>> deleteRoleUserForAdmin(
      @RequestBody List<RoleUserReqDto> roleUser) {
    Map<String, Object> data = roleService.deleteRoleUserForAdmin(roleUser);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }
}