
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
import com.basic.app.dto.requestDto.SmsMReqDto;
import com.basic.app.dto.responseDto.SmsHResDto;
import com.basic.app.dto.responseDto.SmsMResDto;
import com.basic.app.dto.responseDto.UserResDto;
import com.basic.app.service.interfaces.SmsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @파일명 : AdminSmsController.java
 * @설명 : SMS 템플릿 및 발송 관리 기능 제공 컨트롤러
 * @작성자 : 김승연
 * @작성일 : 2025.07.23
 * @변경이력 :
 *       2025.07.23 김승연 최초 생성
 */
@Tag(name = "AdminSmsController", description = "SMS API")
@RestController
@RequestMapping("/admin/sms")
public class AdminSmsController {

  @Autowired
  private SmsService smsService;

  /**
   * @REQ_ID : REQ_ADM_067
   * @화면 : 시스템 관리 > SMS 발송 로그
   * @기능 : SMS 리스트 조회
   * @param smsMReqDto SMS 검색 조건 DTO
   * @param pageable   페이징 정보
   * @return SMS 리스트 조회 결과
   */
  @Operation(summary = "[REQ_ADM_067] [화면 : 시스템 관리 > SMS 발송 로그] [기능 : SMS 리스트 조회]", description = "SMS 리스트 조회 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = SmsMResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/search")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findAllSmsForAdmin(SmsMReqDto smsMReqDto,
      @PageableDefault(page = 0, size = 2000, sort = "smsId", direction = Sort.Direction.ASC) Pageable pageable) {
    Map<String, Object> data = smsService.findAllSmsForAdmin(smsMReqDto, pageable);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /**
   * @REQ_ID : REQ_ADM_067_2
   * @화면 : 시스템 관리 > SMS 발송 로그
   * @기능 : SMS 조회
   * @param smsId SMS 아이디
   * @return SMS 상세 조회 결과
   */
  @Operation(summary = "[REQ_ADM_067_2] [화면 : 시스템 관리 > SMS 발송 로그] [기능 : SMS 조회]", description = "SMS 조회 기능 제공")
  @Parameter(name = "smsId", description = "SMS 아이디", example = "SMS001")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = SmsMResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/{smsId}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findBySmsForAdmin(@PathVariable String smsId) {
    Map<String, Object> data = smsService.findBySmsForAdmin(smsId);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /**
   * @REQ_ID : REQ_ADM_071
   * @화면 : 시스템 관리 > SMS 발송 로그
   * @기능 : SMS 추가
   * @param smsM SMS 정보 DTO
   * @return SMS 추가 결과
   */
  @Operation(summary = "[REQ_ADM_071] [화면 : 시스템 관리 > SMS 발송 로그] [기능 : SMS 추가]", description = "SMS 추가 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = SmsMResDto.class)))
  @SwaggerCommonResponseApi
  @PostMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> insertSmsForAdmin(
      @RequestBody @Validated(CreateGroup.class) SmsMReqDto smsM) {
    Map<String, Object> data = smsService.insertSmsForAdmin(smsM);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /**
   * @REQ_ID : REQ_ADM_072
   * @화면 : 시스템 관리 > SMS 발송 로그
   * @기능 : SMS 수정
   * @param smsM SMS 정보 DTO
   * @return SMS 수정 결과
   */
  @Operation(summary = "[REQ_ADM_072] [화면 : 시스템 관리 > SMS 발송 로그] [기능 : SMS 수정]", description = "SMS 수정 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = SmsMResDto.class)))
  @SwaggerCommonResponseApi
  @PutMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> updateSmsForAdmin(
      @RequestBody @Validated(UpdateGroup.class) SmsMReqDto smsM) {
    Map<String, Object> data = smsService.updateSmsForAdmin(smsM);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /**
   * @REQ_ID : REQ_ADM_073
   * @화면 : 시스템 관리 > SMS 발송 로그
   * @기능 : SMS 삭제
   * @param smsId SMS 아이디
   * @return SMS 삭제 결과
   */
  @Operation(summary = "[REQ_ADM_073] [화면 : 시스템 관리 > SMS 발송 로그] [기능 : SMS 삭제]", description = "SMS 삭제 기능 제공")
  @Parameter(name = "smsId", description = "SMS 아이디", example = "SMS001")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ResponseApiSuccessForSwagger.class)))
  @SwaggerCommonResponseApi
  @DeleteMapping("/{smsId}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> deleteSmsForAdmin(@PathVariable String smsId) {
    Map<String, Object> data = smsService.deleteSmsForAdmin(smsId);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /**
   * @REQ_ID : REQ_ADM_068
   * @화면 : 시스템 관리 > SMS 발송 로그
   * @기능 : SMS 이력 조회
   * @param searchCondition 검색 조건
   * @param pageable        페이징 정보
   * @return SMS 이력 목록
   */
  @Operation(summary = "[REQ_ADM_068] [화면 : 시스템 관리 > SMS 발송 로그] [기능 : SMS 이력 조회]", description = "SMS 이력 조회 기능 제공")
  @Parameter(name = "smsId", description = "SMS 아이디", example = "SMS001")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = SmsHResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/history/{smsId}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findBySmsHistoryForAdmin(@PathVariable String smsId,
      @PageableDefault(page = 0, size = 2000, sort = "smsId", direction = Sort.Direction.ASC) Pageable pageable) {
    Map<String, Object> data = smsService.findBySmsHistoryForAdmin(smsId, pageable);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* 사용 X */
  /**
   * @REQ_ID : REQ_ADM_069
   * @화면 : 시스템 관리 > SMS 발송 로그
   * @기능 : SMS 템플릿 조회
   * @return SMS 템플릿 목록
   */
  // @GetMapping("/templates")
  // public ResponseEntity<ResponseApi<Map<String, Object>>> findSmsTemplates() {
  // Map<String, Object> data = smsService.findSmsTemplates();
  // return
  // ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  // }

  /* 사용 X */
  /**
   * @REQ_ID : REQ_ADM_070
   * @화면 : 시스템 관리 > SMS 발송 로그
   * @기능 : SMS 템플릿 조회
   * @param smsId SMS 아이디
   * @return SMS 템플릿 정보
   */
  // @GetMapping("/templates/{templateId}")
  // public ResponseEntity<ResponseApi<Map<String, Object>>> findSmsTemplate(
  // @PathVariable ResponseEntity<ResponseApi<Map<String, Object>>> templateId) {
  // Map<String, Object> data = smsService.findSmsTemplate(templateId);
  // return
  // ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  // }

}