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
import com.basic.app.dto.requestDto.RoleReqDto;
import com.basic.app.dto.requestDto.RoleUserReqDto;
import com.basic.app.service.interfaces.RoleService;

@RestController
@RequestMapping("/admin/role")
public class AdminRoleController {

  @Autowired
  private RoleService roleService;

  /* [REQ_ADM_042] [화면 : 권한 관리 > 권한 관리(Role)] [기능 : 권한 리스트 조회] */
  @GetMapping
  public ResponseEntity<ApiResponse<Map<String, Object>>> findAllRoleForAdmin() {
    Map<String, Object> results = roleService.findAllRoleForAdmin();
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_042_2] [화면 : 권한 관리 > 권한 관리(Role)] [기능 : 권한 조회] */
  @GetMapping("/{roldCd}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> findByRoleForAdmin(@PathVariable String roldCd) {
    Map<String, Object> results = roleService.findByRoleForAdmin(roldCd);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_045] [화면 : 권한 관리 > 권한 관리(Role)] [기능 : 권한 추가] */
  @PostMapping
  public ResponseEntity<ApiResponse<Map<String, Object>>> insertRoleForAdmin(RoleReqDto role) {
    Map<String, Object> results = roleService.insertRoleForAdmin(role);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_046] [화면 : 권한 관리 > 권한 관리(Role)] [기능 : 권한 수정] */
  @PutMapping
  public ResponseEntity<ApiResponse<Map<String, Object>>> updateRoleForAdmin(RoleReqDto role) {
    Map<String, Object> results = roleService.updateRoleForAdmin(role);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_047] [화면 : 권한 관리 > 권한 관리(Role)] [기능 : 권한 삭제] */
  @DeleteMapping("/{roldCd}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> deleteRoleForAdmin(@PathVariable String roleCd) {
    Map<String, Object> results = roleService.deleteRoleForAdmin(roleCd);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_043] [화면 : 권한 관리 > 권한 관리(Role)] [기능 : 권한별 메뉴 리스트 조회] */
  @GetMapping("/role-menu/{roldCd}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> findByRoleMenuForAdmin(@PathVariable String roldCd) {
    Map<String, Object> results = roleService.findByRoleMenuForAdmin(roldCd);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_044] [화면 : 권한 관리 > 권한 관리(Role)] [기능 : 권한별 메뉴 리스트 수정] */
  @PutMapping("/role-menu")
  public ResponseEntity<ApiResponse<Map<String, Object>>> updateRoleMenuForAdmin(List<RoleReqDto> roleMenu) {
    Map<String, Object> results = roleService.updateRoleMenuForAdmin(roleMenu);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_048] [화면 : 권한 관리 > 사용자별 권한] [기능 : 사용자 리스트 조회] */
  @GetMapping("/role-user")
  public ResponseEntity<ApiResponse<Map<String, Object>>> findAllRoleUserForAdmin() {
    Map<String, Object> results = roleService.findAllRoleUserForAdmin();
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_049] [화면 : 권한 관리 > 사용자별 권한] [기능 : 사용자별 권한 조회] */
  @GetMapping("/role-user/{userId}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> findByRoleUserForAdmin(@PathVariable String userId) {
    Map<String, Object> results = roleService.findByRoleUserForAdmin(userId);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* [REQ_ADM_050] [화면 : 권한 관리 > 사용자별 권한] [기능 : 사용자별 권한 추가] */
  @PostMapping("/role-user")
  public ResponseEntity<ApiResponse<Map<String, Object>>> insertRoleUserForAdmin(RoleUserReqDto roleUser) {
    Map<String, Object> results = roleService.insertRoleUserForAdmin(roleUser);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }

  /* 사용 X */
  // /* [REQ_ADM_051] [화면 : 권한 관리 > 사용자별 권한] [기능 : 사용자별 권한 수정] */
  // @PutMapping("/user")
  // public ResponseEntity<ApiResponse<Map<String, Object>>>
  // updateRoleUserForAdmin(RoleUser roleUser) {
  // Map<String, Object> results = roleService.updateRoleUserForAdmin(roleUser);
  // return
  // ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  // }

  /* [REQ_ADM_052] [화면 : 권한 관리 > 사용자별 권한] [기능 : 사용자별 권한 삭제] */
  @DeleteMapping("/user") // 또는 /{userId}/roles/{roleId} 등으로 세분화 가능
  public ResponseEntity<ApiResponse<Map<String, Object>>> deleteRoleUserForAdmin(List<RoleUserReqDto> roleUser) {
    Map<String, Object> results = roleService.deleteRoleUserForAdmin(roleUser);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  }
}