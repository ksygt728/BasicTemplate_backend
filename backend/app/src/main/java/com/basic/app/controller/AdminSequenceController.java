/**
 * @파일명   : AdminSequenceController.java
 * @설명     : 시퀀스(일련번호) 관리 기능 제공 컨트롤러
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
import com.basic.app.dto.requestDto.ChaebunReqDto;
import com.basic.app.dto.responseDto.ChaebunResDto;
import com.basic.app.service.interfaces.SequenceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "AdminSequenceController", description = "시퀀스 API")
@RestController
@RequestMapping("/admin/sequence")
public class AdminSequenceController {

  @Autowired
  private SequenceService sequenceService;

  /* [REQ_ADM_053] [화면 : 시스템 관리 > 채번관리] [기능 : 채번 리스트 조회] */
  @Operation(summary = "[REQ_ADM_053] [화면 : 시스템 관리 > 채번관리] [기능 : 채번 리스트 조회]", description = "채번 리스트 조회 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ChaebunResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> findAllSequenceForAdmin() {
    Map<String, Object> data = sequenceService.findAllSequenceForAdmin();
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_053_2] [화면 : 시스템 관리 > 채번관리] [기능 : 채번 조회] */
  @Operation(summary = "[REQ_ADM_053_2] [화면 : 시스템 관리 > 채번관리] [기능 : 채번 조회]", description = "채번 조회 기능 제공")
  @Parameter(name = "seqId", description = "시퀀스 아이디", example = "SEQ001")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ChaebunResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/{seqId}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findBySequenceForAdmin(@PathVariable String seqId) {
    Map<String, Object> data = sequenceService.findBySequenceForAdmin(seqId);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_054] [화면 : 시스템 관리 > 채번관리] [기능 : 채번 추가] */
  @Operation(summary = "[REQ_ADM_054] [화면 : 시스템 관리 > 채번관리] [기능 : 채번 추가]", description = "채번 추가 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ChaebunResDto.class)))
  @SwaggerCommonResponseApi
  @PostMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> insertSequenceForAdmin(
      @Validated(CreateGroup.class) ChaebunReqDto chaebun) {
    Map<String, Object> data = sequenceService.insertSequenceForAdmin(chaebun);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_055] [화면 : 시스템 관리 > 채번관리] [기능 : 채번 수정] */
  @Operation(summary = "[REQ_ADM_055] [화면 : 시스템 관리 > 채번관리] [기능 : 채번 수정]", description = "채번 수정 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ChaebunResDto.class)))
  @SwaggerCommonResponseApi
  @PutMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> updateSequenceForAdmin(
      @Validated(UpdateGroup.class) ChaebunReqDto chaebun) {
    Map<String, Object> data = sequenceService.updateSequenceForAdmin(chaebun);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_056] [화면 : 시스템 관리 > 채번관리] [기능 : 채번 삭제] */
  @Operation(summary = "[REQ_ADM_056] [화면 : 시스템 관리 > 채번관리] [기능 : 채번 삭제]", description = "채번 삭제 기능 제공")
  @Parameter(name = "seqId", description = "시퀀스 아이디", example = "SEQ001")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ResponseApiSuccessForSwagger.class)))
  @SwaggerCommonResponseApi
  @DeleteMapping("/{seqId}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> deleteSequenceForAdmin(@PathVariable String seqId) {
    Map<String, Object> data = sequenceService.deleteSequenceForAdmin(seqId);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }
}