
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basic.app.annotation.SwaggerCommonResponseApi;
import com.basic.app.api.ResponseApi;
import com.basic.app.api.ResponseApiSuccessForSwagger;
import com.basic.app.dto.group.CreateGroup;
import com.basic.app.dto.group.UpdateGroup;
import com.basic.app.dto.requestDto.ChaebunReqDto;
import com.basic.app.dto.responseDto.ChaebunResDto;
import com.basic.app.service.interfaces.ChaebunService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @파일명 : AdminChaebunController.java
 * @설명 : 채번 관리 기능 제공 컨트롤러
 * @작성자 : 김승연
 * @작성일 : 2025.08.23
 * @변경이력 :
 *       2025.08.23 김승연 최초 생성
 */
@Tag(name = "AdminChaebunController", description = "채번 API")
@RestController
@RequestMapping("/admin/chaebun")
public class AdminChaebunController {

  @Autowired
  private ChaebunService chaebunService;

  /**
   * @REQ_ID : REQ_ADM_053
   * @화면 : 시스템 관리 > 채번관리
   * @기능 : 채번 리스트 조회
   * @param ChaebunReqDto 채번 검색 조건 DTO
   * @param pageable      페이징 정보
   * @return 채번 리스트 조회 결과
   */
  @Operation(summary = "[REQ_ADM_053] [화면 : 시스템 관리 > 채번관리] [기능 : 채번 리스트 조회]", description = "채번 리스트 조회 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ChaebunResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/search")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findAllChaebunForAdmin(ChaebunReqDto ChaebunReqDto,
      @PageableDefault(page = 0, size = 2000, sort = "seqId", direction = Sort.Direction.ASC) Pageable pageable) {
    Map<String, Object> data = chaebunService.findAllChaebunForAdmin(ChaebunReqDto, pageable);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /**
   * @REQ_ID : REQ_ADM_053_2
   * @화면 : 시스템 관리 > 채번관리
   * @기능 : 채번 조회
   * @param seqId 채번 아이디
   * @return 채번 상세 정보
   */
  @Operation(summary = "[REQ_ADM_053_2] [화면 : 시스템 관리 > 채번관리] [기능 : 채번 조회]", description = "채번 조회 기능 제공")
  @Parameter(name = "seqId", description = "채번 아이디", example = "SMS001")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ChaebunResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/{seqId}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findByChaebunForAdmin(@PathVariable String seqId) {
    Map<String, Object> data = chaebunService.findByChaebunForAdmin(seqId);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /**
   * @REQ_ID : REQ_ADM_054
   * @화면 : 시스템 관리 > 채번관리
   * @기능 : 채번 추가
   * @param chaebunReqDto 채번 정보 DTO
   * @return 채번 추가 결과
   */
  @Operation(summary = "[REQ_ADM_054] [화면 : 시스템 관리 > 채번관리] [기능 : 채번 추가]", description = "채번 추가 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ChaebunResDto.class)))
  @SwaggerCommonResponseApi
  @PostMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> insertChaebunForAdmin(
      @Validated(CreateGroup.class) ChaebunReqDto chaebunReqDto) {
    Map<String, Object> data = chaebunService.insertChaebunForAdmin(chaebunReqDto);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /**
   * @REQ_ID : REQ_ADM_055
   * @화면 : 시스템 관리 > 채번관리
   * @기능 : 채번 수정
   * @param smsM 채번 정보 DTO
   * @return 채번 수정 결과
   */
  @Operation(summary = "[REQ_ADM_055] [화면 : 시스템 관리 > 채번관리] [기능 : 채번 수정]", description = "채번 수정 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ChaebunResDto.class)))
  @SwaggerCommonResponseApi
  @PutMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> updateChaebunForAdmin(
      @Validated(UpdateGroup.class) ChaebunReqDto smsM) {
    Map<String, Object> data = chaebunService.updateChaebunForAdmin(smsM);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /**
   * @REQ_ID : REQ_ADM_056
   * @화면 : 시스템 관리 > 채번관리
   * @기능 : 채번 삭제
   * @param seqId 채번 아이디
   * @return 채번 삭제 결과
   */
  @Operation(summary = "[REQ_ADM_056] [화면 : 시스템 관리 > 채번관리] [기능 : 채번 삭제]", description = "채번 삭제 기능 제공")
  @Parameter(name = "seqId", description = "채번 아이디", example = "CHAEBUN001")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ResponseApiSuccessForSwagger.class)))
  @SwaggerCommonResponseApi
  @DeleteMapping("/{seqId}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> deleteChaebunForAdmin(@PathVariable String seqId) {
    Map<String, Object> data = chaebunService.deleteChaebunForAdmin(seqId);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /**
   * @REQ_ID : REQ_ADM_056
   * @화면 : 시스템 관리 > 채번관리
   * @기능 : 채번 생성
   * @param seqId 채번 아이디
   * @return 채번 생성 결과
   */
  @Operation(summary = "[REQ_ADM_056] [화면 : 시스템 관리 > 채번관리] [기능 : 채번 생성]", description = "채번 삭제 기능 제공")
  @Parameter(name = "seqId", description = "채번 아이디", example = "CHAEBUN001")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ResponseApiSuccessForSwagger.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/generateSeq")
  public ResponseEntity<ResponseApi<Map<String, Object>>> generateSeq(String seqId) {
    String pattern = chaebunService.generateSeq(seqId);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(Map.of("data", pattern)));
  }

}