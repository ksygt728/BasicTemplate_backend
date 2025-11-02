
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
import com.basic.app.dto.requestDto.MenuReqDto;
import com.basic.app.dto.requestDto.MulLangReqDto;
import com.basic.app.dto.responseDto.MulLangResDto;
import com.basic.app.service.interfaces.MultiLangService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @파일명 : AdminMulLangController.java
 * @설명 : 다국어 메시지 관리 기능 제공 컨트롤러
 * @작성자 : 김승연
 * @작성일 : 2025.07.23
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 */
@Tag(name = "AdminMulLangController", description = "다국어 API")
@RestController
@RequestMapping("/admin/lang")
public class AdminMulLangController {

  @Autowired
  private MultiLangService multiLangService;

  /**
   * @REQ_ID : REQ_ADM_033
   * @화면 : 기준 정보 > 다국어 관리
   * @기능 : 다국어 리스트 조회
   * @param mulLangReqDto 다국어 검색 조건 DTO
   * @param pageable      페이징 정보
   * @return 다국어 리스트 조회 결과
   */
  @Operation(summary = "[REQ_ADM_033] [화면 : 기준 정보 > 다국어 관리] [기능 : 다국어 리스트 조회]", description = "다국어 리스트 조회 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = MulLangResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/search")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findAllMulLangForAdmin(MulLangReqDto mulLangReqDto,
      @PageableDefault(page = 0, size = 2000, sort = "langCd", direction = Sort.Direction.ASC) Pageable pageable) {
    Map<String, Object> data = multiLangService.findAllMulLangForAdmin(mulLangReqDto, pageable);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /**
   * @REQ_ID : REQ_ADM_034
   * @화면 : 기준 정보 > 다국어 관리
   * @기능 : 다국어 리스트 언어별 조회
   * @param langGubun 언어 구분
   * @param langCd    언어 코드
   * @return 다국어 리스트 언어별 조회 결과
   */
  @Operation(summary = "[REQ_ADM_034] [화면 : 기준 정보 > 다국어 관리] [기능 : 다국어 리스트 언어별 조회]", description = "다국어 리스트 언어별 조회 기능 제공")
  @Parameter(name = "langCd", description = "언어 코드", example = "LANG001")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = MulLangResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/{langGubun}/{langCd}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findByMulLangForAdmin(@PathVariable String langGubun,
      @PathVariable String langCd) {
    Map<String, Object> data = multiLangService.findByMulLangForAdmin(langGubun, langCd);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /**
   * @REQ_ID : REQ_ADM_035
   * @화면 : 기준 정보 > 다국어 관리
   * @기능 : 다국어 추가
   * @param mulLang 다국어 정보 DTO
   * @return 다국어 추가 결과
   */
  @Operation(summary = "[REQ_ADM_035] [화면 : 기준 정보 > 다국어 관리] [기능 : 다국어 추가]", description = "다국어 추가 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = MulLangResDto.class)))
  @SwaggerCommonResponseApi
  @PostMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> insertMulLangForAdmin(
      @RequestBody @Validated(CreateGroup.class) MulLangReqDto mulLang) {
    Map<String, Object> data = multiLangService.insertMulLangForAdmin(mulLang);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /**
   * @REQ_ID : REQ_ADM_036
   * @화면 : 기준 정보 > 다국어 관리
   * @기능 : 다국어 수정
   * @param mulLang 다국어 정보 DTO
   * @return 다국어 수정 결과
   */
  @Operation(summary = "[REQ_ADM_036] [화면 : 기준 정보 > 다국어 관리] [기능 : 다국어 수정]", description = "다국어 수정 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = MulLangResDto.class)))
  @SwaggerCommonResponseApi
  @PutMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> updateMulLangForAdmin(
      @RequestBody @Validated(UpdateGroup.class) MulLangReqDto mulLang) {
    Map<String, Object> data = multiLangService.updateMulLangForAdmin(mulLang);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /**
   * @REQ_ID : REQ_ADM_037
   * @화면 : 기준 정보 > 다국어 관리
   * @기능 : 다국어 삭제
   * @param langGubun 언어 구분
   * @param langCd    언어 코드
   * @return 다국어 삭제 결과
   */
  @Operation(summary = "[REQ_ADM_037] [화면 : 기준 정보 > 다국어 관리] [기능 : 다국어 삭제]", description = "다국어 삭제 기능 제공")
  @Parameters({
      @Parameter(name = "langGubun", description = "구분코드", example = "err"),
      @Parameter(name = "langCd", description = "언어코드", example = "1001")
  })
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ResponseApiSuccessForSwagger.class)))
  @SwaggerCommonResponseApi
  @DeleteMapping("/{langGubun}/{langCd}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> deleteMulLangForAdmin(@PathVariable String langGubun,
      @PathVariable String langCd) {
    Map<String, Object> data = multiLangService.deleteMulLangForAdmin(langGubun, langCd);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /**
   * @REQ_ID : REQ_ADM_037_2
   * @화면 : 기준 정보 > 다국어 관리
   * @기능 : 다국어 삭제 각 언어별 상세
   * @param langType  국가 코드
   * @param langGubun 언어 구분
   * @param langCd    언어 코드
   * @return 다국어 삭제 각 언어별 상세 결과
   */
  @Operation(summary = "[REQ_ADM_037] [화면 : 기준 정보 > 다국어 관리] [기능 : 다국어 삭제(각 언어별 상세)]", description = "다국어 삭제 기능 제공")
  @Parameters({
      @Parameter(name = "langType", description = "국가코드", example = "ko"),
      @Parameter(name = "langGubun", description = "구분코드", example = "err"),
      @Parameter(name = "langCd", description = "언어코드", example = "1001")
  })
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ResponseApiSuccessForSwagger.class)))
  @SwaggerCommonResponseApi
  @DeleteMapping("/{langType}/{langGubun}/{langCd}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> deleteMulLangDetailForAdmin(
      @PathVariable String langType,
      @PathVariable String langGubun,
      @PathVariable String langCd) {
    Map<String, Object> data = multiLangService.deleteMulLangDetailForAdmin(langType, langGubun, langCd);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }
}