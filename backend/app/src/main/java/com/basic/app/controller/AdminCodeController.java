
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import com.basic.app.dto.requestDto.ComCodeDReqDto;
import com.basic.app.dto.requestDto.ComCodeMReqDto;
import com.basic.app.dto.requestDto.ComCodeTReqDto;
import com.basic.app.dto.requestDto.specialDto.CodeSearchFormReqDto;
import com.basic.app.dto.responseDto.ComCodeDResDto;
import com.basic.app.dto.responseDto.ComCodeMResDto;
import com.basic.app.dto.responseDto.ComCodeTResDto;
import com.basic.app.dto.responseDto.specialDto.CodeSearchFormResDto;
import com.basic.app.service.interfaces.CodeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/admin/code")
public class AdminCodeController {

  @Autowired
  private CodeService codeService;

  /* [REQ_ADM_009_1] [화면 : 기준 정보 > 코드 관리] [기능 : 공통코드 검색] */
  @Operation(summary = "[REQ_ADM_009_1] [화면 : 기준 정보 > 코드 관리] [기능 : 공통코드 검색]", description = "공통코드 검색 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = CodeSearchFormResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/search")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findAllCodeMWithConditions(CodeSearchFormReqDto reqDto,
      @PageableDefault(page = 0, size = 2000) Pageable pageable) {
    Map<String, Object> data = codeService.findAllCodeRowMWithConditions(reqDto, pageable);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_009_2] [화면 : 기준 정보 > 코드 관리] [기능 : 그뤂 코드 조회] */
  @Operation(summary = "[REQ_ADM_009_2] [화면 : 기준 정보 > 코드 관리] [기능 : 그뤂 코드 조회]", description = "그룹 코드 조회 기능 제공")
  @Parameter(name = "grpCd", description = "그뤂코드", example = "TestGroup")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ComCodeMResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/group/{grpCd}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findByGroupCodeForAdmin(@PathVariable String grpCd) {
    Map<String, Object> data = codeService.findByGroupCodeForAdmin(grpCd);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_010] [화면 : 기준 정보 > 코드 관리] [기능 : 그뤂 코드 추가] */
  @Operation(summary = "[REQ_ADM_010] [화면 : 기준 정보 > 코드 관리] [기능 : 그뤂 코드 추가]", description = "그룹 코드 추가 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ComCodeMResDto.class)))
  @SwaggerCommonResponseApi
  @PostMapping("/group")
  public ResponseEntity<ResponseApi<Map<String, Object>>> insertGroupCodeForAdmin(
      @Validated(CreateGroup.class) ComCodeMReqDto comCodeM) {
    Map<String, Object> data = codeService.insertGroupCodeForAdmin(comCodeM);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_011] [화면 : 기준 정보 > 코드 관리] [기능 : 그뤂 코드 수정] */
  @Operation(summary = "[REQ_ADM_011] [화면 : 기준 정보 > 코드 관리] [기능 : 그뤂 코드 수정]", description = "그룹 코드 수정 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ComCodeMResDto.class)))
  @SwaggerCommonResponseApi
  @PutMapping("/group")
  public ResponseEntity<ResponseApi<Map<String, Object>>> updateGroupCodeForAdmin(
      @Validated(UpdateGroup.class) ComCodeMReqDto comCodeM) {
    Map<String, Object> data = codeService.updateGroupCodeForAdmin(comCodeM);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_012] [화면 : 기준 정보 > 코드 관리] [기능 : 그뤂 코드 삭제] */
  @Operation(summary = "[REQ_ADM_012] [화면 : 기준 정보 > 코드 관리] [기능 : 그뤂 코드 삭제]", description = "그룹 코드 삭제 기능 제공")
  @Parameter(name = "grpCd", description = "그뤂코드", example = "TestGroup")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ResponseApiSuccessForSwagger.class)))
  @SwaggerCommonResponseApi
  @DeleteMapping("/group/{grpCd}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> deleteGroupCodeForAdmin(@PathVariable String grpCd) {
    Map<String, Object> data = codeService.deleteGroupCodeForAdmin(grpCd);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_013] [화면 : 기준 정보 > 코드 관리] [기능 : 속성 코드 리스트 조회] */
  // @GetMapping("/attribute")
  // public ResponseEntity<ResponseApi<Map<String, Object>>>
  // findAllAttrCodeForAdmin() {
  // Map<String, Object> data = codeService.findAllAttrCodeForAdmin();
  // return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  // }

  // /* [REQ_ADM_013_2] [화면 : 기준 정보 > 코드 관리] [기능 : 속성 코드 조회] */
  // @GetMapping("/attribute/{attrCd}")
  // public ResponseEntity<ResponseApi<Map<String, Object>>>
  // findByAttrCodeForAdmin(@PathVariable String attrCd) {
  // Map<String, Object> data = codeService.findByAttrCodeForAdmin(attrCd);
  // return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  // }

  /* [REQ_ADM_014] [화면 : 기준 정보 > 코드 관리] [기능 : 속성 코드 추가] */
  @Operation(summary = "[REQ_ADM_014] [화면 : 기준 정보 > 코드 관리] [기능 : 속성 코드 추가]", description = "속성 코드 추가 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ComCodeTResDto.class)))
  @SwaggerCommonResponseApi
  @PostMapping("/attribute")
  public ResponseEntity<ResponseApi<Map<String, Object>>> insertAttrCodeForAdmin(
      @Validated(CreateGroup.class) ComCodeTReqDto comCodeT) {
    Map<String, Object> data = codeService.insertAttrCodeForAdmin(comCodeT);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_015] [화면 : 기준 정보 > 코드 관리] [기능 : 속성 코드 수정] */
  @Operation(summary = "[REQ_ADM_015] [화면 : 기준 정보 > 코드 관리] [기능 : 속성 코드 수정]", description = "속성 코드 수정 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ComCodeTResDto.class)))
  @SwaggerCommonResponseApi
  @PutMapping("/attribute")
  public ResponseEntity<ResponseApi<Map<String, Object>>> updateAttrCodeForAdmin(
      @Validated(UpdateGroup.class) ComCodeTReqDto comCodeT) {
    Map<String, Object> data = codeService.updateAttrCodeForAdmin(comCodeT);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_016] [화면 : 기준 정보 > 코드 관리] [기능 : 속성 코드 삭제] */
  @Operation(summary = "[REQ_ADM_016] [화면 : 기준 정보 > 코드 관리] [기능 : 속성 코드 삭제]", description = "속성 코드 삭제 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ResponseApiSuccessForSwagger.class)))
  @SwaggerCommonResponseApi
  @DeleteMapping("/attribute/{grpCd}/{attrCd}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> deleteAttrCodeForAdmin(@PathVariable String grpCd,
      @PathVariable String attrCd) {
    Map<String, Object> data = codeService.deleteAttrCodeForAdmin(grpCd, attrCd);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_017] [화면 : 기준 정보 > 코드 관리] [기능 : 상세코드 리스트 조회] */
  // @GetMapping("/detail")
  // public ResponseEntity<ResponseApi<Map<String, Object>>>
  // findAllDetailCodeForAdmin() {
  // Map<String, Object> data = codeService.findAllDetailCodeForAdmin();
  // return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  // }

  // /* [REQ_ADM_017_2] [화면 : 기준 정보 > 코드 관리] [기능 : 상세코드 조회] */
  // @GetMapping("/detail/{dtlCd}")
  // public ResponseEntity<ResponseApi<Map<String, Object>>>
  // findByDetailCodeForAdmin(@PathVariable String dtlCd) {
  // Map<String, Object> data = codeService.findByDetailCodeForAdmin(dtlCd);
  // return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  // }

  /* [REQ_ADM_018] [화면 : 기준 정보 > 코드 관리] [기능 : 상세코드 추가] */
  @Operation(summary = "[REQ_ADM_018] [화면 : 기준 정보 > 코드 관리] [기능 : 상세코드 추가]", description = "상세코드 추가 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ComCodeDResDto.class)))
  @SwaggerCommonResponseApi
  @PostMapping("/detail")
  public ResponseEntity<ResponseApi<Map<String, Object>>> insertDetailCodeForAdmin(
      @Validated(CreateGroup.class) ComCodeDReqDto comCodeD) {
    Map<String, Object> data = codeService.insertDetailCodeForAdmin(comCodeD);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_019] [화면 : 기준 정보 > 코드 관리] [기능 : 상세코드 수정] */
  @Operation(summary = "[REQ_ADM_019] [화면 : 기준 정보 > 코드 관리] [기능 : 상세코드 수정]", description = "상세코드 수정 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ComCodeDResDto.class)))
  @SwaggerCommonResponseApi
  @PutMapping("/detail")
  public ResponseEntity<ResponseApi<Map<String, Object>>> updateDetailCodeForAdmin(
      @Validated(UpdateGroup.class) ComCodeDReqDto comCodeD) {
    Map<String, Object> data = codeService.updateDetailCodeForAdmin(comCodeD);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_020] [화면 : 기준 정보 > 코드 관리] [기능 : 상세코드 삭제] */
  @Operation(summary = "[REQ_ADM_020] [화면 : 기준 정보 > 코드 관리] [기능 : 상세코드 삭제]", description = "상세코드 삭제 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ResponseApiSuccessForSwagger.class)))
  @SwaggerCommonResponseApi
  @DeleteMapping("/detail/{grpCd}/{attrCd}/{dtlCd}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> deleteDetailCodeForAdmin(@PathVariable String grpCd,
      @PathVariable String attrCd, @PathVariable String dtlCd) {
    Map<String, Object> data = codeService.deleteDetailCodeForAdmin(grpCd, attrCd, dtlCd);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }
}