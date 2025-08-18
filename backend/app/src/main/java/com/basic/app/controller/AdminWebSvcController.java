/**
 * @파일명   : AdminWebSvcController.java
 * @설명     : 웹서비스 연동 관리 기능 제공 컨트롤러
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
import com.basic.app.dto.requestDto.WebSvcReqDto;
import com.basic.app.dto.responseDto.WebSvcResDto;
import com.basic.app.service.interfaces.WebSvcService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/admin/webservice")
public class AdminWebSvcController {

  @Autowired
  private WebSvcService webSvcService;

  /* [REQ_ADM_027] [화면 : 기준 정보 > 웹서비스 관리] [기능 : 웹서비스 기준정보 리스트 조회] */
  @Operation(summary = "[REQ_ADM_027] [화면 : 기준 정보 > 웹서비스 관리] [기능 : 웹서비스 기준정보 리스트 조회]", description = "웹서비스 기준정보 리스트 조회 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = WebSvcResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> findAllWebserviceForAdmin() {
    Map<String, Object> data = webSvcService.findAllWebserviceForAdmin();
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_027_2] [화면 : 기준 정보 > 웹서비스 관리] [기능 : 웹서비스 기준정보 조회] */
  @Operation(summary = "[REQ_ADM_027_2] [화면 : 기준 정보 > 웹서비스 관리] [기능 : 웹서비스 기준정보 조회]", description = "웹서비스 기준정보 조회 기능 제공")
  @Parameter(name = "svcId", description = "웹서비스 아이디", example = "WEB001")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = WebSvcResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/{svcId}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findByWebserviceForAdmin(@PathVariable String svcId) {
    Map<String, Object> data = webSvcService.findByWebserviceForAdmin(svcId);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_030] [화면 : 기준 정보 > 웹서비스 관리] [기능 : 웹서비스 추가] */
  @Operation(summary = "[REQ_ADM_030] [화면 : 기준 정보 > 웹서비스 관리] [기능 : 웹서비스 추가]", description = "웹서비스 추가 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = WebSvcResDto.class)))
  @SwaggerCommonResponseApi
  @PostMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> inesrtWebserviceForAdmin(
      @Validated(CreateGroup.class) WebSvcReqDto webSvc) {
    Map<String, Object> data = webSvcService.inesrtWebserviceForAdmin(webSvc);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_031] [화면 : 기준 정보 > 웹서비스 관리] [기능 : 웹서비스 수정] */
  @Operation(summary = "[REQ_ADM_031] [화면 : 기준 정보 > 웹서비스 관리] [기능 : 웹서비스 수정]", description = "웹서비스 수정 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = WebSvcResDto.class)))
  @SwaggerCommonResponseApi
  @PutMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> updateWebserviceForAdmin(
      @Validated(UpdateGroup.class) WebSvcReqDto webSvc) {
    Map<String, Object> data = webSvcService.updateWebserviceForAdmin(webSvc);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_032] [화면 : 기준 정보 > 웹서비스 관리] [기능 : 웹서비스 삭제] */
  @Operation(summary = "[REQ_ADM_032] [화면 : 기준 정보 > 웹서비스 관리] [기능 : 웹서비스 삭제]", description = "웹서비스 삭제 기능 제공")
  @Parameter(name = "svcId", description = "웹서비스 아이디", example = "WEB001")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ResponseApiSuccessForSwagger.class)))
  @SwaggerCommonResponseApi
  @DeleteMapping("/{svcId}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> deleteWebserviceForAdmin(@PathVariable String svcId) {
    Map<String, Object> data = webSvcService.deleteWebserviceForAdmin(svcId);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_028] [화면 : 기준 정보 > 웹서비스 관리] [기능 : 웹서비스 직접 실행] */
  @Operation(summary = "[REQ_ADM_028] [화면 : 기준 정보 > 웹서비스 관리] [기능 : 웹서비스 직접 실행]", description = "웹서비스 직접 실행 기능 제공")
  @Parameter(name = "svcId", description = "웹서비스 아이디", example = "WEB001")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = WebSvcResDto.class)))
  @SwaggerCommonResponseApi
  @PostMapping("/execute/{svcId}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> executeWebserviceForAdmin(@PathVariable String svcId) {
    Map<String, Object> data = webSvcService.executeWebserviceForAdmin(svcId);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_029] [화면 : 기준 정보 > 웹서비스 관리] [기능 : 웹서비스 이력조회] */
  @Operation(summary = "[REQ_ADM_029] [화면 : 기준 정보 > 웹서비스 관리] [기능 : 웹서비스 이력조회]", description = "웹서비스 이력조회 기능 제공")
  @Parameter(name = "svcId", description = "웹서비스 아이디", example = "WEB001")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = WebSvcResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/history/{svcId}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findByWebserviceHistoryForAdmin(@PathVariable String svcId) {
    Map<String, Object> data = webSvcService.findByWebserviceHistoryForAdmin(svcId);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

}