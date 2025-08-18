/**
 * @파일명   : AdminBbsController.java
 * @설명     : 게시판 관리 기능 제공 컨트롤러
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
import com.basic.app.dto.requestDto.BbsReqDto;
import com.basic.app.dto.responseDto.BbsResDto;
import com.basic.app.service.interfaces.BbsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/admin/bbs")
public class AdminBbsController {

  @Autowired
  private BbsService bbsService;

  /* [REQ_ADM_081] [화면 : 시스템 관리 > 메뉴얼 관리] [기능 : 메뉴얼 리스트 조회] */
  @Operation(summary = "[REQ_ADM_081] [화면 : 시스템 관리 > 메뉴얼 관리] [기능 : 메뉴얼 리스트 조회]", description = "시스템 관리 > 메뉴얼 관리의 메뉴얼 리스트를 조회합니다.")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = BbsResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> findAllBbsForAdmin() {
    Map<String, Object> data = bbsService.findAllBbsForAdmin();
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_082] [화면 : 시스템 관리 > 메뉴얼 관리] [기능 : 메뉴얼 상세 조회] */
  @Operation(summary = "[REQ_ADM_082] [화면 : 시스템 관리 > 메뉴얼 관리] [기능 : 메뉴얼 상세 조회]", description = "시스템 관리 > 메뉴얼 관리의 메뉴얼 상세 정보를 조회합니다.")
  @Parameter(name = "bbsId", description = "게시판 아이디", example = "bbs123")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = BbsResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/{bbsId}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findByBbsForAdmin(@PathVariable String bbsId) {
    Map<String, Object> data = bbsService.findByBbsForAdmin(bbsId);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_083] [화면 : 시스템 관리 > 메뉴얼 관리] [기능 : 메뉴얼 추가] */
  @Operation(summary = "[REQ_ADM_083] [화면 : 시스템 관리 > 메뉴얼 관리] [기능 : 메뉴얼 추가]", description = "시스템 관리 > 메뉴얼 관리에 메뉴얼을 추가합니다.")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = BbsResDto.class)))
  @SwaggerCommonResponseApi
  @PostMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> insertBbsForAdmin(
      @Validated(CreateGroup.class) BbsReqDto bbs) {
    Map<String, Object> data = bbsService.insertBbsForAdmin(bbs);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_0834] [화면 : 시스템 관리 > 메뉴얼 관리] [기능 : 메뉴얼 수정] */
  @Operation(summary = "[REQ_ADM_0834] [화면 : 시스템 관리 > 메뉴얼 관리] [기능 : 메뉴얼 수정]", description = "시스템 관리 > 메뉴얼 관리의 메뉴얼을 수정합니다.")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = BbsResDto.class)))
  @SwaggerCommonResponseApi
  @PutMapping
  public ResponseEntity<ResponseApi<Map<String, Object>>> updateBbsForAdmin(
      @Validated(UpdateGroup.class) BbsReqDto bbs) {
    Map<String, Object> data = bbsService.updateBbsForAdmin(bbs);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /* [REQ_ADM_085] [화면 : 시스템 관리 > 메뉴얼 관리] [기능 : 메뉴얼 삭제] */
  @Operation(summary = "[REQ_ADM_085] [화면 : 시스템 관리 > 메뉴얼 관리] [기능 : 메뉴얼 삭제]", description = "시스템 관리 > 메뉴얼 관리의 메뉴얼을 삭제합니다.")
  @Parameter(name = "bbsId", description = "게시판 아이디", example = "bbs123")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ResponseApiSuccessForSwagger.class)))
  @SwaggerCommonResponseApi
  @DeleteMapping("/{bbsId}")
  public ResponseEntity<ResponseApi<Map<String, Object>>> deleteBbsForAdmin(@PathVariable String bbsId) {
    Map<String, Object> data = bbsService.deleteBbsForAdmin(bbsId);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }
}