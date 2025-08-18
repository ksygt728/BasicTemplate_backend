/**
 * @파일명   : AdminInterfaceController.java
 * @설명     : 외부 시스템 인터페이스 관리 기능 제공 컨트롤러
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
import com.basic.app.dto.requestDto.InterfaceReqDto;
import com.basic.app.dto.responseDto.InterfaceResDto;
import com.basic.app.service.interfaces.InterfaceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "AdminInterfaceController", description = "인터페이스 API")
@RestController
@RequestMapping("/admin/interface")
public class AdminInterfaceController {

  @Autowired
  private InterfaceService interfaceService;

  /* [REQ_ADM_021_0] [화면 : 기준 정보 > 인터페이스 관리] [기능 : 인터페이스 조회 폼 조회] */
  @Operation(summary = "[REQ_ADM_021_0] [화면 : 기준 정보 > 인터페이스 관리] [기능 : 인터페이스 조회 폼 조회]", description = "인터페이스 조회 폼 조회 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = InterfaceResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/search")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findAllInterfaceWithConditionsForAdmin(
      InterfaceReqDto interfaceReqDto,
      @PageableDefault(page = 0, size = 5, sort = "ifId", direction = Sort.Direction.ASC) Pageable pageable) {

    Map<String, Object> data = interfaceService.findAllInterfaceWithConditionsForAdmin(interfaceReqDto, pageable);

    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_021] [화면 : 기준 정보 > 인터페이스 관리] [기능 : 인터페이스 기준정보 리스트 조회] */
  @Operation(summary = "[REQ_ADM_021] [화면 : 기준 정보 > 인터페이스 관리] [기능 : 인터페이스 기준정보 리스트 조회]", description = "인터페이스 기준정보 리스트 조회 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = InterfaceResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> findAllInterfaceForAdmin(
      @PageableDefault(page = 0, size = 10, sort = "ifId", direction = Sort.Direction.ASC) Pageable pageable) {

    Map<String, Object> data = interfaceService.findAllInterfaceForAdmin(pageable);

    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_021_2] [화면 : 기준 정보 > 인터페이스 관리] [기능 : 인터페이스 기준정보 조회] */
  @Operation(summary = "[REQ_ADM_021_2] [화면 : 기준 정보 > 인터페이스 관리] [기능 : 인터페이스 기준정보 조회]", description = "인터페이스 기준정보 조회 기능 제공")
  @Parameter(name = "ifId", description = "인터페이스 아이디", example = "IF0001")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = InterfaceResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/{ifId}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findByInterfaceForAdmin(@PathVariable String ifId) {

    Map<String, Object> data = interfaceService.findByInterfaceForAdmin(ifId);

    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_022] [화면 : 기준 정보 > 인터페이스 관리] [기능 : 인터페이스 직접 실행] */
  @Operation(summary = "[REQ_ADM_022] [화면 : 기준 정보 > 인터페이스 관리] [기능 : 인터페이스 직접 실행]", description = "인터페이스 직접 실행 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = InterfaceResDto.class)))
  @SwaggerCommonResponseApi
  @PostMapping("/execute/{ifc}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> executeInterfaceForAdmin(InterfaceReqDto ifc) {
    Map<String, Object> data = interfaceService.executeInterfaceForAdmin(ifc);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_023] [화면 : 기준 정보 > 인터페이스 관리] [기능 : 이력조회] */
  @Operation(summary = "[REQ_ADM_023] [화면 : 기준 정보 > 인터페이스 관리] [기능 : 이력조회]", description = "인터페이스 이력조회 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = InterfaceResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/history/{ifId}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findByInterfaceHistoryForAdmin(@PathVariable String ifId) {
    Map<String, Object> data = interfaceService.findByInterfaceHistoryForAdmin(ifId);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_024] [화면 : 기준 정보 > 인터페이스 관리] [기능 : 인터페이스 추가] */
  @Operation(summary = "[REQ_ADM_024] [화면 : 기준 정보 > 인터페이스 관리] [기능 : 인터페이스 추가]", description = "인터페이스 추가 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = InterfaceResDto.class)))
  @SwaggerCommonResponseApi
  @PostMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> insertInterfaceForAdmin(
      @Validated(CreateGroup.class) InterfaceReqDto ifc) {
    Map<String, Object> data = interfaceService.insertInterfaceForAdmin(ifc);

    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_025] [화면 : 기준 정보 > 인터페이스 관리] [기능 : 인터페이스 수정] */
  @Operation(summary = "[REQ_ADM_025] [화면 : 기준 정보 > 인터페이스 관리] [기능 : 인터페이스 수정]", description = "인터페이스 수정 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = InterfaceResDto.class)))
  @SwaggerCommonResponseApi
  @PutMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> updateInterfaceForAdmin(
      @Validated(UpdateGroup.class) InterfaceReqDto ifc) {

    Map<String, Object> data = interfaceService.updateInterfaceForAdmin(ifc);

    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));

  }

  /* [REQ_ADM_026] [화면 : 기준 정보 > 인터페이스 관리] [기능 : 인터페이스 삭제] */
  @Operation(summary = "[REQ_ADM_026] [화면 : 기준 정보 > 인터페이스 관리] [기능 : 인터페이스 삭제]", description = "인터페이스 삭제 기능 제공")
  @Parameter(name = "ifId", description = "인터페이스 아이디", example = "IF0001")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ResponseApiSuccessForSwagger.class)))
  @SwaggerCommonResponseApi
  @DeleteMapping("/{ifId}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> deleteInterfaceForAdmin(@PathVariable String ifId) {
    Map<String, Object> data = interfaceService.deleteInterfaceForAdmin(ifId);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }
}