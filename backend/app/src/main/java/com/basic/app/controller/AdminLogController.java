/**
 * @파일명   : AdminLogController.java
 * @설명     : 시스템 로그(접속, 오류 등) 관리 기능 제공 컨트롤러
 * @작성자   : 김승연
 * @작성일   : 2025.07.23
 * @변경이력 :
 *   2025.07.23     김승연       최초 생성
 */
package com.basic.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basic.app.annotation.SwaggerCommonResponseApi;
import com.basic.app.api.ResponseApi;
import com.basic.app.dto.requestDto.LogApiReqDto;
import com.basic.app.dto.requestDto.LogErrorReqDto;
import com.basic.app.dto.responseDto.LogApiResDto;
import com.basic.app.dto.responseDto.LogErrorResDto;
import com.basic.app.service.interfaces.LogService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "AdminLogController", description = "로그 API")
@RestController
@RequestMapping("/admin/log") // Logs 및 Errors는 admin 하위에 직접 배치
public class AdminLogController {

  @Autowired
  private LogService logService;

  /* [REQ_ADM_064] [화면 : 시스템 관리 > 사용자 접속 로그] [기능 : 사용자 접속로그 리스트 조회] */
  @Operation(summary = "[REQ_ADM_064] [화면 : 시스템 관리 > 사용자 접속 로그] [기능 : 사용자 접속로그 리스트 조회]", description = "사용자 접속로그 리스트 조회 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = LogApiResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/api-log/search")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findAllAccessLogForAdmin(
      LogApiReqDto logApiReqDto,
      @PageableDefault(page = 0, size = 100, sort = "endDate", direction = Sort.Direction.DESC) Pageable pageable) {

    Map<String, Object> data = logService.findAllApiLogForAdmin(logApiReqDto, pageable);

    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_064_2] [화면 : 시스템 관리 > 사용자 접속 로그] [기능 : 사용자 접속로그 조회] */
  @Operation(summary = "[REQ_ADM_064_2] [화면 : 시스템 관리 > 사용자 접속 로그] [기능 : 사용자 접속로그 조회]", description = "사용자 접속로그 조회 기능 제공")
  @Parameter(name = "logId", description = "접속 로그 아이디", example = "LOG0001")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = LogApiResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/api-log/{logId}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findByAccessLogForAdmin(@PathVariable String logId) {
    Map<String, Object> data = logService.findByAccessLogForAdmin(logId);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_065] [화면 : 시스템 관리 > Error 관리] [기능 : 에러 리스트 조회] */
  @Operation(summary = "[REQ_ADM_065] [화면 : 시스템 관리 > Error 관리] [기능 : 에러 리스트 조회]", description = "에러 리스트 조회 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = LogErrorResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/error-log/search")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findAllErrorLogForAdmin(LogErrorReqDto logErrorReqDto,
      @PageableDefault(page = 0, size = 100, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable) {

    Map<String, Object> data = logService.findAllErrorLogForAdmin(logErrorReqDto, pageable);

    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_066] [화면 : 시스템 관리 > Error 관리] [기능 : 에러 상세정보 조회] */
  @Operation(summary = "[REQ_ADM_066] [화면 : 시스템 관리 > Error 관리] [기능 : 에러 상세정보 조회]", description = "에러 상세정보 조회 기능 제공")
  @Parameter(name = "errId", description = "에러 아이디", example = "ERR0001")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = LogErrorResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/error-log/{errId}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findByErrorLogForAdmin(@PathVariable String errId) {
    Map<String, Object> data = logService.findByErrorLogForAdmin(errId);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }
}