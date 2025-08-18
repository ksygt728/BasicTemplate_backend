/**
 * @파일명   : AdminMailController.java
 * @설명     : 메일 템플릿 및 발송 관리 기능 제공 컨트롤러
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
import com.basic.app.dto.requestDto.MailMReqDto;
import com.basic.app.dto.responseDto.MailHResDto;
import com.basic.app.dto.responseDto.MailMResDto;
import com.basic.app.service.interfaces.MailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/admin/mail")
public class AdminMailController {

  @Autowired
  private MailService mailService;

  /* [REQ_ADM_057] [화면 : 시스템 관리 > 메일 관리] [기능 : 메일 리스트 조회] */
  @Operation(summary = "[REQ_ADM_057] [화면 : 시스템 관리 > 메일 관리] [기능 : 메일 리스트 조회]", description = "메일 리스트 조회 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = MailMResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> findAllMailForAdmin() {
    Map<String, Object> data = mailService.findAllMailForAdmin();
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_057_2] [화면 : 시스템 관리 > 메일 관리] [기능 : 메일 조회] */
  @Operation(summary = "[REQ_ADM_057_2] [화면 : 시스템 관리 > 메일 관리] [기능 : 메일 조회]", description = "메일 조회 기능 제공")
  @Parameter(name = "mailId", description = "메일 아이디", example = "mail123")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = MailMResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/{mailId}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findByMailForAdmin(@PathVariable String mailId) {
    Map<String, Object> data = mailService.findByMailForAdmin(mailId);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_058] [화면 : 시스템 관리 > 메일 관리] [기능 : 메일 이력 조회] */
  @Operation(summary = "[REQ_ADM_058] [화면 : 시스템 관리 > 메일 관리] [기능 : 메일 이력 조회]", description = "메일 이력 조회 기능 제공")
  @Parameter(name = "mailId", description = "메일 아이디", example = "mail123")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = MailHResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/history/{mailId}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findByMailHistoryForAdmin(@PathVariable String mailId) {
    Map<String, Object> data = mailService.findByMailHistoryForAdmin(mailId);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_061] [화면 : 시스템 관리 > 메일 관리] [기능 : 메일 추가] */
  @Operation(summary = "[REQ_ADM_061] [화면 : 시스템 관리 > 메일 관리] [기능 : 메일 추가]", description = "메일 추가 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = MailMResDto.class)))
  @SwaggerCommonResponseApi
  @PostMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> insertMailForAdmin(
      @Validated(CreateGroup.class) MailMReqDto mailM) {
    Map<String, Object> data = mailService.insertMailForAdmin(mailM);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_062] [화면 : 시스템 관리 > 메일 관리] [기능 : 메일 수정] */
  @Operation(summary = "[REQ_ADM_062] [화면 : 시스템 관리 > 메일 관리] [기능 : 메일 수정]", description = "메일 수정 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = MailMResDto.class)))
  @SwaggerCommonResponseApi
  @PutMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> updateMailForAdmin(
      @Validated(UpdateGroup.class) MailMReqDto mailM) {
    Map<String, Object> data = mailService.updateMailForAdmin(mailM);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_063] [화면 : 시스템 관리 > 메일 관리] [기능 : 메일 삭제] */
  @Operation(summary = "[REQ_ADM_063] [화면 : 시스템 관리 > 메일 관리] [기능 : 메일 삭제]", description = "메일 삭제 기능 제공")
  @Parameter(name = "mailId", description = "메일 아이디", example = "mail123")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ResponseApiSuccessForSwagger.class)))
  @SwaggerCommonResponseApi
  @DeleteMapping("/{mailId}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> deleteMailForAdmin(@PathVariable String mailId) {
    Map<String, Object> data = mailService.deleteMailForAdmin(mailId);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }
}