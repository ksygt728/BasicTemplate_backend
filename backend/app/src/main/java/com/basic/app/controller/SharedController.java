
package com.basic.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.basic.app.annotation.SwaggerCommonResponseApi;
import com.basic.app.api.ResponseApi;
import com.basic.app.dto.requestDto.MenuReqDto;
import com.basic.app.dto.requestDto.specialDto.CodeSearchFormReqDto;
import com.basic.app.dto.responseDto.MenuResDto;
import com.basic.app.dto.responseDto.UserResDto;
import com.basic.app.dto.responseDto.specialDto.CodeSearchFormResDto;
import com.basic.app.service.interfaces.CodeService;
import com.basic.app.service.interfaces.MenuService;
import com.basic.app.service.interfaces.SharedService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;

/**
 * @파일명 : UserController.java
 * @설명 : 사용자 컨트롤러
 * @작성자 : 김승연
 * @작성일 : 2025.07.31
 * @변경이력 :
 *       2025.07.31 김승연 최초 생성
 */

@Log4j2
@Tag(name = "SharedController", description = "시스템 공유 API")
@RestController
@RequestMapping("/api/v1/shared")
public class SharedController {

  @Autowired
  private SharedService sharedService;

  @Autowired
  private MenuService menuService;

  @Autowired
  private CodeService codeService;

  /**
   * @REQ_ID : -
   * @화면 : -
   * @기능 : 다국어 목록 가져오기
   * @param localeText 언어 코드 (기본값: ko)
   * @return 다국어 목록
   */
  @Operation(summary = "[-] [화면 : -] [기능 : 다국어 목록 가져오기]", description = "다국어 목록을 가져옵니다.")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = UserResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/mulLang")
  public ResponseEntity<ResponseApi<Map<String, Object>>> getMulLangList(
      @RequestParam(defaultValue = "ko") String localeText) {
    Map<String, Object> data = sharedService.getMulLangList(localeText);

    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /**
   * @REQ_ID : -
   * @화면 : All
   * @기능 : 공통으로 사용하는 메뉴 리스트 조회
   * @param menuReqDto 메뉴 검색 조건 DTO
   * @param pageable   페이징 정보
   * @return 메뉴 리스트 조회 결과
   */
  @Operation(summary = "[-] [화면 : All] [기능 : 공통으로 사용하는 메뉴 리스트 조회]", description = "메뉴 리스트 조회 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = MenuResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/menu/search")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findAllMenuForAdmin(MenuReqDto menuReqDto,
      @PageableDefault(page = 0, size = 2000, sort = "deptCode", direction = Sort.Direction.ASC) Pageable pageable) {
    Map<String, Object> data = menuService.findAllMenuForAdmin(menuReqDto, pageable);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

  /**
   * @REQ_ID : -
   * @화면 : All
   * @기능 : 다양한 화면에서 공통으로 사용할 코드 검색
   * @param reqDto   코드 검색 조건 DTO
   * @param pageable 페이징 정보
   * @return 공통코드 검색 결과
   */
  @Operation(summary = "[-] [화면 : All] [기능 : 다양한 화면에서 공통으로 사용할 코드 검색]", description = "공통코드 검색 기능 제공")
  @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = CodeSearchFormResDto.class)))
  @SwaggerCommonResponseApi
  @GetMapping("/code/search")
  public ResponseEntity<ResponseApi<Map<String, Object>>> findAllCodeMWithConditions(CodeSearchFormReqDto reqDto,
      @PageableDefault(page = 0, size = 2000) Pageable pageable) {
    Map<String, Object> data = codeService.findAllCodeRowMWithConditions(reqDto, pageable);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success(data));
  }

}