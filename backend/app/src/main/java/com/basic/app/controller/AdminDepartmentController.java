
package com.basic.app.controller;

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
import com.basic.app.dto.requestDto.DepartmentReqDto;
import com.basic.app.dto.responseDto.DepartmentResDto;
import com.basic.app.service.interfaces.DepartmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @파일명 : AdminDepartmentController.java
 * @설명 : 부서 관리 기능 제공 컨트롤러
 * @작성자 : 김승연
 * @작성일 : 2025.07.23
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 */
@Tag(name = "AdminDepartmentController", description = "부서 API")
@RestController
@RequestMapping("/admin/department")
public class AdminDepartmentController {

  @Autowired
  private DepartmentService departmentService;

  /**
   * @REQ_ID : REQ_ADM_005
   * @화면 : 조직 관리 > 부서 관리
   * @기능 : 부서 정보 리스트 조회
   * @param departmentReqDto 부서 검색 조건 DTO
   * @param pageable         페이징 정보
   * @return 부서 정보 리스트 조회 결과
   */
  @Operation(summary = "[REQ_ADM_005] [화면 : 조직 관리 > 부서 관리] [기능 : 부서 정보 리스트 조회]", description = "부서 정보 리스트 조회 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = DepartmentResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/search")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findAllDepartmentForAdmin(
      DepartmentReqDto departmentReqDto,
      @PageableDefault(page = 0, size = 2000, sort = "deptCode", direction = Sort.Direction.ASC) Pageable pageable) {

    Map<String, Object> data = departmentService.findAllDepartmentForAdmin(departmentReqDto, pageable);

    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /**
   * @REQ_ID : REQ_ADM_005_2
   * @화면 : 조직 관리 > 부서 관리
   * @기능 : 부서 정보 조회
   * @param deptCode 부서 코드
   * @return 부서 정보 상세 조회 결과
   */
  @Operation(summary = "[REQ_ADM_005_2] [화면 : 조직 관리 > 부서 관리] [기능 : 부서 정보 조회]", description = "부서 정보 조회 기능 제공")
  @Parameter(name = "deptCode", description = "부서코드", example = "20000000")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = DepartmentResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/{deptCode}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findByDepartmentForAdmin(@PathVariable String deptCode) {

    Map<String, Object> data = departmentService.findByDepartmentForAdmin(deptCode);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /**
   * @REQ_ID : REQ_ADM_006
   * @화면 : 조직 관리 > 부서 관리
   * @기능 : 부서 정보 추가
   * @param department 부서 정보 DTO
   * @return 부서 정보 추가 결과
   */
  @Operation(summary = "[REQ_ADM_006] [화면 : 조직 관리 > 부서 관리] [기능 : 부서 정보 추가]", description = "부서 정보 추가 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = DepartmentResDto.class)))
  @SwaggerCommonResponseApi
  @PostMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> insertDepartmentForAdmin(
      @RequestBody @Validated(CreateGroup.class) DepartmentReqDto department) {
    Map<String, Object> data = departmentService.insertDepartmentForAdmin(department);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /**
   * @REQ_ID : REQ_ADM_007
   * @화면 : 조직 관리 > 부서 관리
   * @기능 : 부서 정보 수정
   * @param department 부서 정보 DTO
   * @return 부서 정보 수정 결과
   */
  @Operation(summary = "[REQ_ADM_007] [화면 : 조직 관리 > 부서 관리] [기능 : 부서 정보 수정]", description = "부서 정보 수정 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = DepartmentResDto.class)))
  @SwaggerCommonResponseApi
  @PutMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> updateDepartmentForAdmin(
      @RequestBody @Validated(UpdateGroup.class) DepartmentReqDto department) {
    Map<String, Object> data = departmentService.updateDepartmentForAdmin(department);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /**
   * @REQ_ID : REQ_ADM_008
   * @화면 : 조직 관리 > 부서 관리
   * @기능 : 부서 정보 삭제
   * @param deptCode 부서 코드
   * @return 부서 정보 삭제 결과
   */
  @Operation(summary = "[REQ_ADM_008] [화면 : 조직 관리 > 부서 관리] [기능 : 부서 정보 삭제]", description = "부서 정보 삭제 기능 제공")
  @Parameter(name = "deptCode", description = "부서코드", example = "20000000")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ResponseApiSuccessForSwagger.class)))
  @SwaggerCommonResponseApi
  @DeleteMapping("/{deptCode}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> deleteDepartmentForAdmin(@PathVariable String deptCode) {
    Map<String, Object> data = departmentService.deleteDepartmentForAdmin(deptCode);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }
}