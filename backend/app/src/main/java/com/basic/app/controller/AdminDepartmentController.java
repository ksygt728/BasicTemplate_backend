
/**
 * @파일명   : AdminDepartmentController.jav  @GetMapping("/{deptCode}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> findByDepartmentForAdmin(@PathVariable String deptCode) {
    Map<String, Object> data = departmentService.findByDepartmentForAdmin(deptCode);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(data)); @설명     : 부서 관리 기능 제공 컨트롤러
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
import com.basic.app.dto.requestDto.DepartmentReqDto;
import com.basic.app.service.interfaces.DepartmentService;

@RestController
@RequestMapping("/admin/department")
public class AdminDepartmentController {

  @Autowired
  private DepartmentService departmentService;

  /* [REQ_ADM_005] [화면 : 조직 관리 > 부서 관리] [기능 : 부서 정보 리스트 조회] */
  @GetMapping
  public ResponseEntity<ApiResponse<Map<String, Object>>> findAllDepartmentForAdmin() {
    Map<String, Object> data = departmentService.findAllDepartmentForAdmin();
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(data));
  }

  /* [REQ_ADM_005_2] [화면 : 조직 관리 > 부서 관리] [기능 : 부서 정보 조회] */
  @GetMapping("/{deptCode}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> findByDepartmentForAdmin(@PathVariable String deptCode) {
    Map<String, Object> data = departmentService.findByDepartmentForAdmin(deptCode);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(data));
  }

  /* [REQ_ADM_006] [화면 : 조직 관리 > 부서 관리] [기능 : 부서 정보 추가] */
  @PostMapping
  public ResponseEntity<ApiResponse<Map<String, Object>>> insertDepartmentForAdmin(
      @Validated(CreateGroup.class) DepartmentReqDto department) {
    Map<String, Object> data = departmentService.insertDepartmentForAdmin(department);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(data));
  }

  /* [REQ_ADM_007] [화면 : 조직 관리 > 부서 관리] [기능 : 부서 정보 수정] */
  @PutMapping
  public ResponseEntity<ApiResponse<Map<String, Object>>> updateDepartmentForAdmin(
      @Validated(UpdateGroup.class) DepartmentReqDto department) {
    Map<String, Object> data = departmentService.updateDepartmentForAdmin(department);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(data));
  }

  /* [REQ_ADM_008] [화면 : 조직 관리 > 부서 관리] [기능 : 부서 정보 삭제] */
  @DeleteMapping("/{deptCode}")
  public ResponseEntity<ApiResponse<Map<String, Object>>> deleteDepartmentForAdmin(@PathVariable String deptCode) {
    Map<String, Object> data = departmentService.deleteDepartmentForAdmin(deptCode);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(data));
  }
}