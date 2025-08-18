/**
 * @파일명   : AdminSchedulerController.java
 * @설명     : 스케줄러 작업 관리 기능 제공 컨트롤러
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
import com.basic.app.dto.requestDto.ScheMReqDto;
import com.basic.app.dto.responseDto.ScheHResDto;
import com.basic.app.dto.responseDto.ScheMResDto;
import com.basic.app.service.interfaces.SchedulerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "AdminSchedulerController", description = "스케쥴러 API")
@RestController
@RequestMapping("/admin/scheduler")
public class AdminSchedulerController {

  @Autowired
  private SchedulerService schedulerService;

  /* [REQ_ADM_074] [화면 : 시스템 관리 > 스케줄러 관리] [기능 : 스케줄러 리스트 조회] */
  @Operation(summary = "[REQ_ADM_074] [화면 : 시스템 관리 > 스케줄러 관리] [기능 : 스케줄러 리스트 조회]", description = "스케줄러 리스트 조회 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ScheMResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/search")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findAllSchedulerForAdmin(ScheMReqDto scheMReqDto,
      @PageableDefault(page = 0, size = 200, sort = "scheId", direction = Sort.Direction.ASC) Pageable pageable) {
    Map<String, Object> data = schedulerService.findAllSchedulerForAdmin(scheMReqDto, pageable);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_074_2] [화면 : 시스템 관리 > 스케줄러 관리] [기능 : 스케줄러 조회] */
  @Operation(summary = "[REQ_ADM_074_2] [화면 : 시스템 관리 > 스케줄러 관리] [기능 : 스케줄러 조회]", description = "스케줄러 조회 기능 제공")
  @Parameter(name = "scheId", description = "스케줄러 ID", example = "SCHED001")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ScheMResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/{scheId}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findBySchedulerForAdmin(@PathVariable String scheId) {
    Map<String, Object> data = schedulerService.findBySchedulerForAdmin(scheId);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_075] [화면 : 시스템 관리 > 스케줄러 관리] [기능 : 스케줄러 이력 조회] */
  @Operation(summary = "[REQ_ADM_075] [화면 : 시스템 관리 > 스케줄러 관리] [기능 : 스케줄러 이력 조회]", description = "스케줄러 이력 조회 기능 제공")
  @Parameter(name = "scheId", description = "스케줄러 ID", example = "SCHED001")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ScheHResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/history/{scheId}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findBySchedulerHistoryForAdmin(@PathVariable String scheId,
      @PageableDefault(page = 0, size = 200, sort = "startDate", direction = Sort.Direction.DESC) Pageable pageable) {
    Map<String, Object> data = schedulerService.findBySchedulerHistoryForAdmin(scheId, pageable);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_077] [화면 : 시스템 관리 > 스케줄러 관리] [기능 : 스케줄러 직접실행] */
  @Operation(summary = "[REQ_ADM_077] [화면 : 시스템 관리 > 스케줄러 관리] [기능 : 스케줄러 직접실행]", description = "스케줄러 직접실행 기능 제공")
  @Parameter(name = "scheId", description = "스케줄러 ID", example = "SCHED001")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ScheMResDto.class)))
  @SwaggerCommonResponseApi
  @PostMapping("/execute/{scheId}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> executeSchedulerForAdmin(@PathVariable String scheId) {
    Map<String, Object> data = schedulerService.executeSchedulerForAdmin(scheId);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_078] [화면 : 시스템 관리 > 스케줄러 관리] [기능 : 스케줄러 추가] */
  @Operation(summary = "[REQ_ADM_078] [화면 : 시스템 관리 > 스케줄러 관리] [기능 : 스케줄러 추가]", description = "스케줄러 추가 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ScheMResDto.class)))
  @SwaggerCommonResponseApi
  @PostMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> insertSchedulerForAdmin(
      @Validated(CreateGroup.class) ScheMReqDto scheM) {
    Map<String, Object> data = schedulerService.insertSchedulerForAdmin(scheM);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_079] [화면 : 시스템 관리 > 스케줄러 관리] [기능 : 스케줄러 수정] */
  @Operation(summary = "[REQ_ADM_079] [화면 : 시스템 관리 > 스케줄러 관리] [기능 : 스케줄러 수정]", description = "스케줄러 수정 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ScheMResDto.class)))
  @SwaggerCommonResponseApi
  @PutMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> updateSchedulerForAdmin(
      @Validated(UpdateGroup.class) ScheMReqDto scheM) {
    Map<String, Object> data = schedulerService.updateSchedulerForAdmin(scheM);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_080] [화면 : 시스템 관리 > 스케줄러 관리] [기능 : 스케줄러 삭제] */
  @Operation(summary = "[REQ_ADM_080] [화면 : 시스템 관리 > 스케줄러 관리] [기능 : 스케줄러 삭제]", description = "스케줄러 삭제 기능 제공")
  @Parameter(name = "scheId", description = "스케줄러 ID", example = "SCHED001")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ResponseApiSuccessForSwagger.class)))
  @SwaggerCommonResponseApi
  @DeleteMapping("/{scheId}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> deleteSchedulerForAdmin(@PathVariable String scheId) {
    Map<String, Object> data = schedulerService.deleteSchedulerForAdmin(scheId);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }
}