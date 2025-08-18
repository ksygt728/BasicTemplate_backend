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
import com.basic.app.dto.requestDto.MulLangReqDto;
import com.basic.app.dto.responseDto.MulLangResDto;
import com.basic.app.service.interfaces.MultiLangService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/admin/lang")
public class AdminMulLangController {

  @Autowired
  private MultiLangService multiLangService;

  /* [REQ_ADM_033] [화면 : 기준 정보 > 다국어 관리] [기능 : 다국어 리스트 조회] */
  @Operation(summary = "[REQ_ADM_033] [화면 : 기준 정보 > 다국어 관리] [기능 : 다국어 리스트 조회]", description = "다국어 리스트 조회 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = MulLangResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> findAllMulLangForAdmin() {
    Map<String, Object> data = multiLangService.findAllMulLangForAdmin();
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_034] [화면 : 기준 정보 > 다국어 관리] [기능 : 다국어 리스트 언어별 조회] */
  @Operation(summary = "[REQ_ADM_034] [화면 : 기준 정보 > 다국어 관리] [기능 : 다국어 리스트 언어별 조회]", description = "다국어 리스트 언어별 조회 기능 제공")
  @Parameter(name = "langCd", description = "언어 코드", example = "LANG001")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = MulLangResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/{langCd}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findByMulLangForAdmin(@PathVariable String langCd) {
    Map<String, Object> data = multiLangService.findByMulLangForAdmin(langCd);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_035] [화면 : 기준 정보 > 다국어 관리] [기능 : 다국어 추가] */
  @Operation(summary = "[REQ_ADM_035] [화면 : 기준 정보 > 다국어 관리] [기능 : 다국어 추가]", description = "다국어 추가 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = MulLangResDto.class)))
  @SwaggerCommonResponseApi
  @PostMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> insertMulLangForAdmin(
      @Validated(CreateGroup.class) MulLangReqDto mulLang) {
    Map<String, Object> data = multiLangService.insertMulLangForAdmin(mulLang);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_036] [화면 : 기준 정보 > 다국어 관리] [기능 : 다국어 수정] */
  @Operation(summary = "[REQ_ADM_036] [화면 : 기준 정보 > 다국어 관리] [기능 : 다국어 수정]", description = "다국어 수정 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = MulLangResDto.class)))
  @SwaggerCommonResponseApi
  @PutMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> updateMulLangForAdmin(
      @Validated(UpdateGroup.class) MulLangReqDto mulLang) {
    Map<String, Object> data = multiLangService.updateMulLangForAdmin(mulLang);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_037] [화면 : 기준 정보 > 다국어 관리] [기능 : 다국어 삭제] */
  @Operation(summary = "[REQ_ADM_037] [화면 : 기준 정보 > 다국어 관리] [기능 : 다국어 삭제]", description = "다국어 삭제 기능 제공")
  @Parameter(name = "langCd", description = "언어 코드", example = "LANG001")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ResponseApiSuccessForSwagger.class)))
  @SwaggerCommonResponseApi
  @DeleteMapping("/{langCd}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> deleteMulLangForAdmin(@PathVariable String langCd) {
    Map<String, Object> data = multiLangService.deleteMulLangForAdmin(langCd);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }
}